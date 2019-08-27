package com.fx.machinelearning;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



import java.nio.charset.Charset;

import java.nio.file.*;


import java.util.ArrayList;
import java.util.List;


import com.fx.model.AutoClassificationLabel;
import com.fx.model.AutoDetectionLabel;
import com.fx.model.AutoPercentDot;
import com.fx.model.ClassificationLabel;
import org.tensorflow.*;

import org.tensorflow.types.UInt8;

import javax.imageio.ImageIO;


/** Sample use of the TensorFlow Java API to label images using a pre-trained model. */

public class PBServer {






    public AutoClassificationLabel predictClassificationLabel(String filename,String imageFile, String modelDir, String[] labels, String PBName){


        //String imageFile =  "E:\\\\GitHub\\\\ObjectDetection\\\\models-master\\\\raccoon_dataset-master\\\\images\\\\mytest\\\\0ShAyPFRqBeO60QkYN7w_Mac Cheese 2.jpg";
        //String modelDir = "E:\\GitHub\\ObjectDetection\\models-master\\raccoon_dataset-master\\mytrain";

        byte[] data = readAllBytesOrExit(Paths.get(imageFile));
        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, PBName));
        Graph g = new Graph();
        g.importGraphDef(graphDef);
        Session s = new Session(g);

        Tensor input = Tensor.create(data);
        Tensor<Float> result = s.runner().feed("DecodeJpeg/contents", input).fetch("final_result").run().get(0).expect(Float.class);
        long[] rshape = result.shape();
        //System.out.println(rshape.length);

        int nlabels = (int) rshape[1];
        int batchSize = (int) rshape[0];


        float max = 0;
        int index = 0;
        float[][] logits = result.copyTo(new float[batchSize][nlabels]);
        for(int i=0;i<=logits.length-1;i++){
            for(int j=0;j<=logits[0].length-1;j++){
                if(logits[i][j]>max){
                    max = logits[i][j];
                    index = j+1;
                }
                System.out.println(labels[j]+" "+logits[i][j]);
            }
        }
        AutoClassificationLabel autoClassificationLabel = new AutoClassificationLabel();
        autoClassificationLabel.setFileName(filename);
        autoClassificationLabel.setSelect(index);

        return autoClassificationLabel;
    }

    public AutoDetectionLabel predicObjectDetectionLabel(String filename,String imageFile, String modelDir){

        int width=0,height=0;
        try {
            File picture = new File(imageFile);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));

            width=sourceImg.getWidth();

            height=sourceImg.getHeight();
        }catch (Exception  e){
            e.printStackTrace();
        }
        byte[] data = readAllBytesOrExit(Paths.get(imageFile));

        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "frozen_inference_graph.pb"));
        Graph g = new Graph();

        g.importGraphDef(graphDef);
        Session s = new Session(g);
        Operation operation = g.operation("detection_boxes");

        Output output = new Output(operation,0);

        //Tensor input = Tensor.create(reshapedata);
        Tensor<UInt8> input =constructAndExecuteGraphToNormalizeImage(data,width,height);
        //Tensor<Float> result = s.runner().feed("DecodeJpeg/contents", input).fetch("final_result").run().get(0).expect(Float.class);
        Tensor<Float> boxes = s.runner().feed("image_tensor",input).fetch(output).run().get(0).expect(Float.class);
        Tensor<Float> scores = s.runner().feed("image_tensor",input).fetch("detection_scores").run().get(0).expect(Float.class);

        long[] reshape1 = boxes.shape();
        long[] reshape2 = scores.shape();

        float[][] floatscores = scores.copyTo(new float[(int)reshape2[0]][(int)reshape2[1]]);
        float[][][] floatboxed = boxes.copyTo(new float[(int)reshape1[0]][(int)reshape1[1]][(int)reshape1[2]]);
        System.out.println(floatscores.length);

        List<AutoPercentDot> dots = new ArrayList<>();


        for(int i=0;i<=floatscores[0].length-1;i++){
            System.out.println(floatscores[0][i]);
            if(floatscores[0][i]>=0.5){
                AutoPercentDot mid1 = new AutoPercentDot();
                AutoPercentDot mid2 = new AutoPercentDot();

                mid1.setX(floatboxed[0][i][0]);
                mid1.setY(floatboxed[0][i][1]);
                mid2.setX(floatboxed[0][i][2]);
                mid2.setY(floatboxed[0][i][3]);
                dots.add(mid1);
                dots.add(mid2);
            }
        }
        /*
        for(int i=0;i<=floatboxed[0].length-1;i++){
            for(int j=0;j<=floatboxed[0][0].length-1;j++){
                System.out.println(floatboxed[0][i][j]);
            }
        }*/

        AutoDetectionLabel autoDetectionLabel = new AutoDetectionLabel();
        autoDetectionLabel.setDots(dots);
        autoDetectionLabel.setFileName(filename);
        return autoDetectionLabel;
    }


    /**
     *下面是tensorflow的私有方法
     *
     */


    private static Tensor<UInt8> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes,int width,int heigth) {

        try (Graph g = new Graph()) {

            GraphBuilder b = new GraphBuilder(g);

            // Some constants specific to the pre-trained model at:

            // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip

            //

            // - The model was trained with images scaled to 224x224 pixels.

            // - The colors, represented as R, G, B in 1-byte each were converted to

            //   float using (value - Mean)/Scale.

            final int H = heigth;

            final int W = width;

            /*
            final float mean = 117f;

            final float scale = 1f;
            */


            // Since the graph is being constructed once per execution here, we can use a constant for the

            // input image. If the graph were to be re-used for multiple input images, a placeholder would

            // have been more appropriate.

            final Output<String> input = b.constant("image_tensor", imageBytes);

            final Output<Float> output =
                    b.cast(
                            b.resizeBilinear(

                                    b.expandDims(

                                            b.decodeJpeg(input, 3),

                                            b.constant("make_batch", 0)),

                                    b.constant("size", new int[] {H, W})),DataType.UINT8);




            try (Session s = new Session(g)) {

                return s.runner().fetch(output.op().name()).run().get(0).expect(UInt8.class);

            }

        }

    }







    public static void main(String[] args) {



//
//        System.out.println("start");
//
//        String modelDir = "E:\\tmp";
//
//        String imageFile =  "E:\\GitHub\\Multi-label-Inception-net-master\\imgae\\images\\14.jpg";
//
//
//
//      byte[] graphDef = readAllBytesOrExit(java.nio.file.Paths.get(modelDir, "output_graph.pb"));
//        //byte[] graphDef=null;
//
//        List<String> labels =
//
//                readAllLinesOrExit(Paths.get(modelDir, "output_labels.txt"));
//
//        byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));
//
//        System.out.println("start");
//
//        try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
//
//            float[] labelProbabilities = executeInceptionGraph(graphDef, image);
//
//            int bestLabelIdx = maxIndex(labelProbabilities);
///*
//            System.out.println(
//
//
//                    String.format("BEST MATCH: %s (%.2f%% likely)",
//
//                            labels.get(bestLabelIdx),
//
//                            labelProbabilities[bestLabelIdx] * 100f));
//
//        }*/
//        for(int i=0;i<=labelProbabilities.length-1;i++){
//            System.out.println(
//
//
//                    String.format("BEST MATCH: %s (%.2f%% likely)",
//
//                            labels.get(i),
//
//                            labelProbabilities[i] * 100f));
//
//        }}

        String imageFile =  "E:\\GitHub\\Multi-label-Inception-net-master\\imgae\\images\\14.jpg";
        String modelDir = "E:\\tmp";

        byte[] data = readAllBytesOrExit(Paths.get(imageFile));
        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "output_graph.pb"));
        Graph g = new Graph();
        g.importGraphDef(graphDef);
        Session s = new Session(g);

        Tensor input = Tensor.create(data);
        Tensor<Float> result = s.runner().feed("DecodeJpeg/contents", input).fetch("final_result").run().get(0).expect(Float.class);
        long[] rshape = result.shape();
        System.out.println(rshape.length);

        int nlabels = (int) rshape[1];
        int batchSize = (int) rshape[0];

        String[] labels = {"desert"
                ,"mountains"
                ,"sea"
                ,"sunset",
                "trees"};
        float[][] logits = result.copyTo(new float[batchSize][nlabels]);
        for(int i=0;i<=logits.length-1;i++){
            for(int j=0;j<=logits[0].length-1;j++){
                System.out.println(labels[j]+" "+logits[i][j]);
            }
        }

    }


/*




    private static float[] executeInceptionGraph(byte[] graphDef, Tensor<Float> image) {

        try (Graph g = new Graph()) {

            g.importGraphDef(graphDef);

            try (Session s = new Session(g);

                 Tensor<Float> result =

                         s.runner().feed("input", image).fetch("output").run().get(0).expect(Float.class)) {

                final long[] rshape = result.shape();

                if (result.numDimensions() != 2 || rshape[0] != 1) {

                    throw new RuntimeException(

                            String.format(

                                    "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",

                                    Arrays.toString(rshape)));

                }

                int nlabels = (int) rshape[1];


                return result.copyTo(new float[1][nlabels])[0];

            }

        }

    }



    private static int maxIndex(float[] probabilities) {

        int best = 0;

        for (int i = 1; i < probabilities.length; ++i) {

            if (probabilities[i] > probabilities[best]) {

                best = i;

            }

        }

        return best;

    }

*/

    private static byte[] readAllBytesOrExit(Path path) {

        try {

            return Files.readAllBytes(path);

        } catch (IOException e) {

            System.err.println("Failed to read [" + path + "]: " + e.getMessage());

            System.exit(1);

        }

        return null;

    }



    private static List<String> readAllLinesOrExit(Path path) {

        try {

            return Files.readAllLines(path, Charset.forName("UTF-8"));

        } catch (IOException e) {

            System.err.println("Failed to read [" + path + "]: " + e.getMessage());

            System.exit(0);

        }

        return null;

    }



    // In the fullness of time, equivalents of the methods of this class should be auto-generated from

    // the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages

    // like Python, C++ and Go.

    static class GraphBuilder {

        GraphBuilder(Graph g) {

            this.g = g;

        }



        Output<Float> div(Output<Float> x, Output<Float> y) {

            return binaryOp("Div", x, y);

        }



        <T> Output<T> sub(Output<T> x, Output<T> y) {

            return binaryOp("Sub", x, y);

        }



        <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {

            return binaryOp3("ResizeBilinear", images, size);

        }



        <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {

            return binaryOp3("ExpandDims", input, dim);

        }



        <T, U> Output<U> cast(Output<T> value, DataType dtype) {

            //DataType dtype = DataType.fromClass(type);

            return g.opBuilder("Cast", "Cast")

                    .addInput(value)

                    .setAttr("DstT", dtype)

                    .build()

                    .<U>output(0);

        }



        Output<UInt8> decodeJpeg(Output<String> contents, long channels) {

            return g.opBuilder("DecodeJpeg", "DecodeJpeg")

                    .addInput(contents)

                    .setAttr("channels", channels)

                    .build()

                    .<UInt8>output(0);

        }



        <T> Output<T> constant(String name, Object value, Class<T> type) {

            try (Tensor<T> t = Tensor.<T>create(value, type)) {

                return g.opBuilder("Const", name)

                        .setAttr("dtype", DataType.fromClass(type))

                        .setAttr("value", t)

                        .build()

                        .<T>output(0);

            }

        }

        Output<String> constant(String name, byte[] value) {

            return this.constant(name, value, String.class);

        }



        Output<Integer> constant(String name, int value) {

            return this.constant(name, value, Integer.class);

        }



        Output<Integer> constant(String name, int[] value) {

            return this.constant(name, value, Integer.class);

        }



        Output<Float> constant(String name, float value) {

            return this.constant(name, value, Float.class);

        }



        private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {

            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);

        }



        private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {

            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);

        }

        private Graph g;

    }

}

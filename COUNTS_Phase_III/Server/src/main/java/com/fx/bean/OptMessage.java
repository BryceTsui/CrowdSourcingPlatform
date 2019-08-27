package com.fx.bean;

/**
 * Created by thinkpad on 2018/3/17.
 */
public class OptMessage {
    /*
       操作成功与否
     */
    private boolean result;
    /*
       message可能值：
       // 成功
       // 失败
       // 错误
       // 已存在
       // 不存在
       // 输入过长
       // 输入过短
       // 含有非法字符
       // 输入为空
       //判断为真
       //判断为假
     */
    private String message;//登陆结果

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OptMessage() {
    }

    public OptMessage(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}

import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Worker from '@/components/Worker'
import CountsMarket from '@/components/CountsMarket'
import Requester from '@/components/Requestor'
import NewProject from '@/components/NewProject'
import Admin from '@/components/Admin'
import MyProject from '@/components/MyProject'
import Test from '@/components/Test'
import Test2 from '@/components/Test2'
import Home from '@/components/Home'
import ProjectDetails from '@/components/ProjectDetails'
import CountsDetails from '@/components/CountsDetails'
import Result from '@/components/Result'
import Register from '@/components/Register'
import Message from '@/components/Message'
import AutoLabel from '@/components/AutoLabel'
import AutoJudge from '@/components/AutoJudge'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/:username/worker',
      name: 'Worker',
      component: Worker
    },
    {
      path: '/:username/autoLabel',
      name: 'AutoLabel',
      component: AutoLabel
    },
    {
      path: '/:username/autoJudge',
      name: 'AutoJudge',
      component: AutoJudge
    },
    {
      path: '/:username/message',
      name: 'Message',
      component: Message
    },
    {
      path: '/:username/requester',
      name: 'Requester',
      component: Requester
    },
    {
      path: '/:username/requester/newProject',
      name: 'NewProject',
      component: NewProject
    },
    {
      path: '/:username/myProject',
      name: 'MyProject',
      component: MyProject
    },
    {
      path: '/:username/countsMarket',
      name: 'CountsMarket',
      component: CountsMarket
    },
    {
      path: '/:username/countsMarket/:id/projectDetails',
      name: 'CountsDetails',
      component: CountsDetails
    },
    {
      path: '/:username/:id/result',
      name: 'Result',
      component: Result
    },
    {
      path: '/:username/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/:username/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/:username/requester/:id/projectDetails',
      name: 'ProjectDetails',
      component: ProjectDetails
    },
    {
      path: '/:username/worker/:id/projectDetails',
      name: 'ProjectDetails',
      component: ProjectDetails
    },
    {
      path: '/:username/test',
      name: 'Test',
      component: Test
    },
    {
      path: '/:username/test2',
      name: 'Test2',
      component: Test2
    }
  ]
})

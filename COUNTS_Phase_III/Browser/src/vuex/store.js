import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import getters from './getter'
Vue.use(Vuex)

const state = {
  user_name: localStorage.getItem('username'),
  user_identify: localStorage.getItem('identify'),
  mission_name: localStorage.getItem('mission_name')
}

const mutations = {
  INIT_STORE (state, data) {
    state.user_identify = data.user_identify
    state.user_name = data.user_name
    state.mission_name = data.mission_name
  },
  HANDLE_USER_NAME (stage, date) {
    state.user_name = date
  },
  HANDLE_USER_IDENTIFY (state, date) {
    state.user_identify = date
  },
  HANDLE_MISSION_NAME (state, date) {
    state.mission_name = date
  }
}

export default new Vuex.Store({
  state,
  mutations
})

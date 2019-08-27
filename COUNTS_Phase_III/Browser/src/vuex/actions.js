function makeAction (type) {
  return ({ dispatch }, ...args) => dispatch(type, ...args)
};

const initData = {
  user_name: 'visitor',
  user_identify: 'logout',
  mission_name: 'mission'
}

export const initStore = ({dispatch}) => {
  dispatch('INIT_STORE', initData)
}

export const handle_user_name = makeAction('HANDLE_USER_NAME')

export const handle_user_identify = makeAction('HANDLE_USER_IDENTIFY')

export const handle_mission_name = makeAction('HANDLE_MISSION_NAME')

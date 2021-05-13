// app.js
// import 'lib-flexible'
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    this.globalData.clazz = wx.getStorageSync('clazz')
    this.globalData.token = wx.getStorageSync('token')

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    userInfo: null,
    clazz:{},
    token:'',
  }
})

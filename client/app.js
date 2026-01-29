// app.js
App({
  onLaunch() {
    // 初始化登录状态
    this.checkLoginStatus()
    
    // 初始化云服务（如果使用云开发）
    // if (!wx.cloud) {
    //   console.error('请使用 2.2.3 或以上的基础库以使用云能力')
    // } else {
    //   wx.cloud.init({
    //     env: 'your-cloud-env-id',
    //     traceUser: true,
    //   })
    // }
  },
  
  onShow() {
    // 应用启动或从后台进入前台时触发
  },
  
  onHide() {
    // 应用从前台进入后台时触发
  },
  
  globalData: {
    userInfo: null,
    token: null,
    baseUrl: 'http://localhost:8080/api'
  },
  
  // 检查登录状态
  checkLoginStatus() {
    const token = wx.getStorageSync('token')
    const userInfo = wx.getStorageSync('userInfo')
    
    if (token && userInfo) {
      this.globalData.token = token
      this.globalData.userInfo = userInfo
    }
  },
  
  // 登录
  login(userInfo, token) {
    this.globalData.userInfo = userInfo
    this.globalData.token = token
    
    // 存储到本地
    wx.setStorageSync('userInfo', userInfo)
    wx.setStorageSync('token', token)
  },
  
  // 登出
  logout() {
    this.globalData.userInfo = null
    this.globalData.token = null
    
    // 清除本地存储
    wx.removeStorageSync('userInfo')
    wx.removeStorageSync('token')
  },
  
  // 网络请求封装
  request(options) {
    const token = this.globalData.token
    
    return new Promise((resolve, reject) => {
      wx.request({
        url: this.globalData.baseUrl + options.url,
        method: options.method || 'GET',
        data: options.data || {},
        header: {
          'Content-Type': 'application/json',
          'Authorization': token ? 'Bearer ' + token : ''
        },
        success: (res) => {
          if (res.statusCode === 200) {
            if (res.data.code === 200) {
              resolve(res.data.data)
            } else {
              reject(res.data.msg)
            }
          } else {
            reject('网络请求失败')
          }
        },
        fail: (err) => {
          reject(err)
        }
      })
    })
  }
})

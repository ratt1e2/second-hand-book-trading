// login.js
const app = getApp()

Page({
  data: {
    phone: '',
    password: ''
  },
  
  onLoad() {
    // 页面加载时检查登录状态
    if (app.globalData.token) {
      wx.switchTab({
        url: '/pages/index/index'
      })
    }
  },
  
  // 手机号输入
  onPhoneInput(e) {
    this.setData({
      phone: e.detail.value
    })
  },
  
  // 密码输入
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    })
  },
  
  // 登录
  login() {
    const { phone, password } = this.data
    
    if (!phone) {
      wx.showToast({
        title: '请输入手机号',
        icon: 'none'
      })
      return
    }
    
    if (!password) {
      wx.showToast({
        title: '请输入密码',
        icon: 'none'
      })
      return
    }
    
    // 这里应该调用后端API进行登录
    // 暂时使用模拟数据
    wx.showLoading({
      title: '登录中...'
    })
    
    setTimeout(() => {
      wx.hideLoading()
      
      // 模拟登录成功
      const userInfo = {
        id: 1,
        nickname: '测试用户',
        phone: phone,
        avatar: ''
      }
      const token = 'mock-token-' + Date.now()
      
      // 登录成功
      app.login(userInfo, token)
      
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
      
      // 跳转到首页
      wx.switchTab({
        url: '/pages/index/index'
      })
    }, 1000)
  },
  
  // 跳转到注册页面
  navigateToRegister() {
    wx.navigateTo({
      url: '/pages/login/register'
    })
  },
  
  // 微信登录
  wechatLogin() {
    wx.showLoading({
      title: '登录中...'
    })
    
    // 调用微信登录API
    wx.login({
      success: (res) => {
        if (res.code) {
          // 这里应该调用后端API进行微信登录
          // 暂时使用模拟数据
          setTimeout(() => {
            wx.hideLoading()
            
            // 模拟登录成功
            const userInfo = {
              id: 2,
              nickname: '微信用户',
              avatar: ''
            }
            const token = 'wechat-token-' + Date.now()
            
            // 登录成功
            app.login(userInfo, token)
            
            wx.showToast({
              title: '登录成功',
              icon: 'success'
            })
            
            // 跳转到首页
            wx.switchTab({
              url: '/pages/index/index'
            })
          }, 1000)
        } else {
          wx.hideLoading()
          wx.showToast({
            title: '登录失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        wx.hideLoading()
        wx.showToast({
          title: '登录失败',
          icon: 'none'
        })
      }
    })
  }
})

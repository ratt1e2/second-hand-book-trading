// index.js
Page({
  data: {
    searchValue: '',
    banners: [
      { id: 1, imageUrl: 'https://via.placeholder.com/750x300?text=开学季特惠' },
      { id: 2, imageUrl: 'https://via.placeholder.com/750x300?text=新书推荐' },
      { id: 3, imageUrl: 'https://via.placeholder.com/750x300?text=热门小说' }
    ],
    categories: [
      { id: 1, name: '教材' },
      { id: 2, name: '小说' },
      { id: 3, name: '社科' },
      { id: 4, name: '计算机' },
      { id: 5, name: '文学' },
      { id: 6, name: '历史' },
      { id: 7, name: '艺术' },
      { id: 8, name: '经管' }
    ],
    newBooks: [
      {
        id: 1,
        title: 'Java核心技术',
        author: 'Cay S. Horstmann',
        price: 89.9,
        condition: 1,
        coverImage: ''
      },
      {
        id: 2,
        title: 'JavaScript高级程序设计',
        author: 'Matt Frisbie',
        price: 79.9,
        condition: 2,
        coverImage: ''
      }
    ],
    hotBooks: [
      {
        id: 3,
        title: '深入理解计算机系统',
        author: 'Randal E. Bryant',
        price: 129.9,
        condition: 1,
        coverImage: ''
      },
      {
        id: 4,
        title: '算法导论',
        author: 'Thomas H. Cormen',
        price: 99.9,
        condition: 2,
        coverImage: ''
      }
    ]
  },
  
  onLoad() {
    // 页面加载时初始化数据
    this.loadData()
  },
  
  onShow() {
    // 页面显示时刷新数据
  },
  
  // 加载数据
  loadData() {
    // 这里应该调用后端API获取数据
    // 暂时使用模拟数据
    console.log('加载首页数据')
  },
  
  // 搜索输入
  onSearchInput(e) {
    this.setData({
      searchValue: e.detail.value
    })
  },
  
  // 搜索
  onSearch() {
    const { searchValue } = this.data
    if (searchValue) {
      wx.navigateTo({
        url: `/pages/category/category?keyword=${encodeURIComponent(searchValue)}`
      })
    }
  },
  
  // 跳转到分类页
  navigateToCategory(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/category/category?id=${id}`
    })
  },
  
  // 跳转到书籍详情页
  navigateToBookDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/bookDetail/bookDetail?id=${id}`
    })
  },
  
  // 跳转到更多页面
  navigateToMore(e) {
    const { type } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/category/category?type=${type}`
    })
  },
  
  // 获取书籍新旧程度文本
  getConditionText(condition) {
    const conditionMap = {
      1: '九成新',
      2: '八成新',
      3: '七成新',
      4: '六成新',
      5: '五成新及以下'
    }
    return conditionMap[condition] || '未知'
  }
})

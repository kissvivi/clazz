// pages/statistics/statistics.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[
      {
        title: "试卷21",
        code: 12

      }
    ],
    toTitle: '查看情况'
  },

  toTestsUrl(e){

		let code = e.currentTarget.dataset.code

		//let testsCode = this.data.testsCode

    wx.navigateTo({
			url: "/pages/statistics/statistics?code="+code
		  })
  },
  onLoad: function(options) {

		let that = this

  var app = getApp();
			that.data.token = wx.getStorageSync("token");
			// let clazz = wx.getStorageSync("clazz");
			app.globalData.clazz = wx.getStorageSync("clazz");
			let id = JSON.parse(app.globalData.clazz);
			console.log(id.id,":globalData")
			wx.request({
				url: 'http://127.0.0.1:8080/testStudent/selfTestList',
				method:'GET',
				header: {
					'Authorization': that.data.token
				},
				success(res) {
					if (res.data.code == 200) {
						// that.data.list = res.data.data;

						that.setData({
							list: res.data.data
						})
						 console.log(that.data.list)
					} else {
						wx.showToast({
							title: '获取失败',
							icon: 'error',
							duration: 2000
						})
					}
				},
				fail() {
					wx.showToast({
						title: '网络错误',
						icon: 'error',
						duration: 2000
					})
				}
			})
    }
})
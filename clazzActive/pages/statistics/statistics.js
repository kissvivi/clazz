// pages/statistics/statistics.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    countStu:0,
    scoreRank:{
      s59:1,
      s6069:0,
      s7079:0,
      s8089:0,
      s90100:0,
      countStu:0,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    let testsCode = options.code

    let that = this

  var app = getApp();
			that.data.token = wx.getStorageSync("token");
			// let clazz = wx.getStorageSync("clazz");
			app.globalData.clazz = wx.getStorageSync("clazz");
			let id = JSON.parse(app.globalData.clazz);
			console.log(id.id,":globalData")
			wx.request({
				url: 'http://127.0.0.1:8080/testStudent/'+testsCode+'/scoreRank',
				method:'GET',
				header: {
					'Authorization': that.data.token
				},
				success(res) {
					if (res.data.code == 200) {
						// that.data.list = res.data.data;

            let json = res.data.data
            
            that.data.countStu = json.countStu

            for(var  key in json){
              if(key != 'countStu'){
                  json[key]= (json[key]/that.data.countStu) * 100;
                }
               }

						that.setData({
							scoreRank: json
						})
						 console.log(json)
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


  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
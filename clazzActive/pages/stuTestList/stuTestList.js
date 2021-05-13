
var app = getApp();
Page({

    data: {
			token: '',
			list: [],
    },

		onLoad: function() {
			let that = this;
			wx.getStorage({
				key: 'clazz',
				success (res) {
					that.setData({
						clazzId: res.data.id
					})
					console.log(res.data)
				}
			})
			var app = getApp();
			that.data.token = wx.getStorageSync("token");
			// let clazz = wx.getStorageSync("clazz");
			app.globalData.clazz = wx.getStorageSync("clazz");
			let id = JSON.parse(app.globalData.clazz);
			console.log(id.id,":globalData")
			wx.request({
				url: 'http://127.0.0.1:8080/tests/'+id.id+'/findListByClazz',
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
							title: '获取题目失败',
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

		toTestsUrl: function(event){
			console.log(event.target.dataset.item);
			const item = event.target.dataset.item;
			// wx.navigateTo({
			// 	url: '/pages/test/test/',
			// })
			wx.navigateTo({
				url: '/pages/test/test?id=1',
				events: {
					// 为指定事件添加一个监听器，获取被打开页面传送到当前页面的数据
					acceptDataFromOpenedPage: function(data) {
						console.log(data,123,item)
					},
					// someEvent: function(data) {
					// 	console.log(data,456,item)
					// }
				},
				success: function(res) {
					res.eventChannel.emit('acceptDataFromOpenerPage', { data: item })
				}
			})
		}

})
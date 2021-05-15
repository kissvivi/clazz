
var app = getApp();
Page({

    data: {
			token: '',
			list: [],
			type:0,
			toTitle:"开始答题",
    },

		onLoad: function(options) {

			console.log("options"+options.type)
			this.setData({
				type:options.type
			})

			if(options.type == 102){
				this.setData({
					toTitle:"开始评价"
				})
			}

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
			let code = wx.getStorageSync("stuCode");
			wx.request({
				url: 'http://127.0.0.1:8080/testStudent/'+code+'/selfTestList',
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
		},

		toTestsUrl: function(event){
			
		},

	 format(ts) {
			var d = getDate(ts)
			return [d.getFullYear(), d.getMonth()+1, d.getDate()].join('-')
				+ ' '
				+ [d.getHours(), d.getMinutes(), d.getSeconds()].join(':')
		}

})
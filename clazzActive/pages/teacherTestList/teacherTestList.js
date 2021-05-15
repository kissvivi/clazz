
var app = getApp();
Page({

    data: {
			token: '',
			list: [],
			calzzList:[],
			clazzId:0,
			type:0,
			_options:{},
			testsId:0,
			toTitle:"开始答题",
			statusFrom:{
				id:0,
				clazzId:0
			},
			show: false,
				actions: [
					{
						name: '选项',
					},
					{
						name: '选项',
					},
					{
						name: '选项',
					},
				],
    },

		onLoad: function(options) {

			this.setData({
				type:options.type,
				_options:options
			})
			console.log("options.type:"+options.type)

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
			wx.request({
				url: 'http://localhost:8080/tests?page=1&size=9999',
				method:'GET',
				header: {
					'Authorization': that.data.token
				},
				success(res) {
					if (res.data.code == 200) {
						// that.data.list = res.data.data;

						let typeList = res.data.data.list;

						typeList = typeList.filter(item =>item.type == that.data.type)

						that.setData({
							list: typeList
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
		},
		updateStatus: function (e){
			this.setData({ show: true });
			
			//this.statusFrom.id = event.detail
			this.getClazzList()
			let id = e.currentTarget.dataset.id
			//this.statusFrom.id = id
			this.setData({
				testsId:id
			})
			console.log("点击发布:"+e.currentTarget.dataset.id)
		},
		onClose() {
			this.setData({ show: false });
		},
	
		onSelect(event) {
			console.log(event.detail);
			let testsId =	this.data.testsId
			this.setData({
				statusFrom:{
					clazzId:event.detail.id,
					id:testsId
				}
			})
			

			let that = this
			var app = getApp();
			that.data.token = wx.getStorageSync("token");
			wx.request({
				url: 'http://localhost:8080/tests/updateStatus',
				method:'PUT',
				data: this.data.statusFrom,
				header: {
					'Authorization': that.data.token
				},
				success(res) {
					if (res.data.code == 200) {
						// that.data.list = res.data.data;
						wx.showToast({
							title: '发布成功',
							icon: 'success',
							duration: 2000
						})
						that.onLoad(that.data._options)
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
		cancelAction(){
			this.setData({ show: false });
		},
		getClazzList(){
			let that = this
			var app = getApp();
			that.data.token = wx.getStorageSync("token");
			wx.request({
				url: 'http://localhost:8080/clazz?page=1&size=999',
				method:'GET',
				header: {
					'Authorization': that.data.token
				},
				success(res) {
					if (res.data.code == 200) {
						// that.data.list = res.data.data;

						that.setData({
							calzzList: res.data.data.list,
							actions:res.data.data.list
						})
						 console.log(that.data.calzzList)
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
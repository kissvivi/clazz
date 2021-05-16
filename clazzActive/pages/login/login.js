// index.js
// 获取应用实例
const app = getApp()

Page({
	data: {
		username: '',
		password: '',
		active: 0,
		userType: 1, // 1学生，2老师
		radio: '1',
	},
	onClick(event) {
		wx.showToast({
			title: `点击标签 ${event.detail + 1}`,
			icon: 'none',
		});
	},
	onChange(event) {
		this.data.username = event.detail;
	},
	onChangePaw(event) {
		this.data.password = event.detail;
	},
	onChangeRole(event) {
		this.setData({
			radio: event.detail,
			userType: Number(event.detail)
		});
	},

	toUrl() {

		let that = this

		if (this.data.userType == 1) {
			wx.request({
				url: 'http://127.0.0.1:8080/student/login',
				method:'POST',
				data: {
					username: that.data.username,
					password: that.data.password
				},
				success(res) {
					if (res.data.code == 200) {

						wx.setStorage({
							key:"token",
							data:res.data.data
						})
						wx.setStorage({
							key:"stuCode",
							data:that.data.username
						})

						wx.navigateTo({
							url: "/pages/scan/scan",
						})
					} else {
						wx.showToast({
							title: '用户名密码错误',
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
		} else {
			wx.request({
				url: 'http://127.0.0.1:8080/account/login',
				method:'POST',
				data: {
					username: that.data.username,
					password: that.data.password
				},
				success(res) {
					if (res.data.code == 200) {

						wx.setStorage({
							key:"token",
							data:res.data.data
						})

						wx.navigateTo({
							url: "/pages/teacher/teacher",
						})
					} else {
						wx.showToast({
							title: '用户名密码错误',
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

	}

})
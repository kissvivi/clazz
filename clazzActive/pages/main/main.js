// index.js
// 获取应用实例
const app = getApp()

Page({
	data: {
		active: 0,
		clazzId:0,
	},

	toTestsUrl(){
		wx.navigateTo({
			url: "/pages/test/test",
			})
		

	},

	toUrl(){
		wx.navigateTo({
			url: "/pages/test/test",
		  })
	}
	
	
})
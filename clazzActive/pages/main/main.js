// index.js
// 获取应用实例
const app = getApp()

Page({
	data: {
		active: 0,
		clazzId:0,
	},

	toTestsUrl(){
		let type = 101
		wx.navigateTo({
			url: "/pages/stuTestList/stuTestList?type="+type,
			})
	},

	toUrl(){
		let type = 102
		wx.navigateTo({
			url: "/pages/stuTestList/stuTestList?type="+type,
		  })
	}
	
	
})
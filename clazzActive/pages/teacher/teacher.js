// index.js
// 获取应用实例
const app = getApp()

Page({
	data: {
		active: 0,
		bannerlist: [],
		imgList:[],
		result: '原址',
	},

	toUrl(){
		wx.navigateTo({
			url: "/pages/test/test",
		  })
	}
	
	
})
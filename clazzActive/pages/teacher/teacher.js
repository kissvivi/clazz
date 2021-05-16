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
	toTestsUrl(){
		let type =101
		wx.navigateTo({
			url: "/pages/teacherTestList/teacherTestList?type="+type,
		  })
	},
	toUrl(){
		let type =102
		wx.navigateTo({
			url: "/pages/teacherTestList/teacherTestList?type="+type,
		  })
	},
	toStatisticsUrl(){
		wx.navigateTo({
			url: "/pages/statisticsList/statisticsList"
		  })
	},
	
	
})
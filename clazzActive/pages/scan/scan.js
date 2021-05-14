// index.js
// 获取应用实例
const app = getApp()

Page({
	data: {
		result: '',
		clazzName: '',
		course:'',
	},
	
	// 扫码
	getCode(){
		var that = this;
		wx.scanCode({
			success (res) {
				that.setData({ result: res.result});
				that.data.result = JSON.parse(res.result);
				that.setData({ 
					clazzName: that.data.result.name,
					course: that.data.result.course
				});
				
				//存储班级id->缓存

				wx.setStorage({
					key:"clazz",
					data:res.result
				})

				let name = res.result.name
				let course = res.result.course
				console.log(decodeURI(escape(name)))
				console.log(decodeURI(escape(course)))
				console.log(res.result)
				console.log(res.charSet)
				
			}
		})
	},
	
	// 加入班级
	jionClass(){
		console.log(this.data.className);
		// 进入首页
		wx.switchTab({
			url: "/pages/main/main",
		  })
	}
})

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    btnText: '下一题',
    nowIndex: 0,
    token: '',
    questionList:[],
    question:{},
    length: 0,
    time: 30 * 60 * 1000,
    radioValue:'',
    studentTestsFrom:{
      stuCode:0,
      testsCode:2021,
      score:30,
      selfChecks:[],//自己选项集合
      ok_checks:[],//正确选项集合
      ok_num:0,
      clazz_id:0,
      tb_ids:[],//题目集合
    },
  },

  
  onLoad: function(option){
    let that = this;
    console.log(option.query,789)
    const eventChannel = that.getOpenerEventChannel()
    eventChannel.emit('acceptDataFromOpenedPage', {data: 'data'});
    // eventChannel.emit('someEvent', {data: 'data'});
    // 监听acceptDataFromOpenerPage事件，获取上一页面通过eventChannel传送到当前页面的数据
    eventChannel.on('acceptDataFromOpenerPage', function(data) {
      console.log(data,"data")
      that.data.questionList = data.data.testBankList;
      console.log( that.data.questionList,": that.data.questionList")
    })
    that.setData({
      length: this.data.questionList.length
    })
    that.nowQuestion();
  },
  nowQuestion(){
    this.setData({
      question: this.data.questionList[this.data.nowIndex]
    })
  },

  doNext(){

    if (this.data.nowIndex == this.data.questionList.length - 1) {
      this.setData({
        btnText: "交卷"
      })
      var app = getApp();
      let clazz = JSON.parse(app.globalData.clazz)

      this.data.studentTestsFrom.stuCode = wx.getStorageSync("stuCode")
      this.data.studentTestsFrom.testsCode = this.data.questionList[0].code
      this.data.studentTestsFrom.score = this.data.studentTestsFrom.ok_num*10
      this.data.studentTestsFrom.clazz_id = clazz.id
      //wx.request
			this.data.token = wx.getStorageSync("token");

      wx.request({
				url: 'http://127.0.0.1:8080/testStudent/',
				method:'POST',
        data: this.data.studentTestsFrom,
        header: {
					'Authorization': this.data.token
				},
				success(res) {
					if (res.data.code == 200) {

            wx.showToast({
							title: '提交成功',
							icon: 'success',
							duration: 2000
						})

						wx.navigateTo({
							url: "/pages/main/main",
						})
					} else {
						wx.showToast({
							title: '提交失败',
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

      console.log("this.data.studentTestsFrom" + this.data.studentTestsFrom.ok_num)
      console.log("this.data.studentTestsFrom" + this.data.studentTestsFrom.ok_num)
    } else {

      let question = this.data.questionList[this.data.nowIndex]

      this.data.studentTestsFrom.selfChecks.push(this.data.radioValue)//选择答案
      this.data.studentTestsFrom.ok_checks.push(question.selectOk)//正确答案
      this.data.studentTestsFrom.tb_ids.push(question.id)//ids

      if (this.data.radioValue == question.selectOk) {
        this.data.studentTestsFrom.ok_num += 1
      }


      this.setData({
        nowIndex: this.data.nowIndex+1,
        radioValue:''//重置选择
      })
      this.nowQuestion();
    }

  },
  finished() {
    Toast('倒计时结束');
  },
  onChangeSelect(event){
    this.setData({
			radioValue: event.detail,
		});
    console.log(this.data.radioValue)
  }
})
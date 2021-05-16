
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title:"倒计时",
    isType:true,
    testsCode:0,
    type:0,//考试类型 101：试题 102 问卷
    btnText: '下一题',
    nowIndex: 0,
    token: '',
    questionList:[],
    question:{},
    length: 0,
    time: 30 * 60 * 1000,
    //time: 3* 1000,
    radioValue:'',
    studentTestsFrom:{
      stuCode:0,
      testsCode:2021,
      type:0,
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
      that.data.testsCode = data.data.code
      that.data.type = data.data.type
      that.data.questionList = data.data.testBankList;
      console.log( that.data.questionList,": that.data.questionList")
    })
    that.setData({
      length: this.data.questionList.length
    })

    if(that.data.type == 102){
      that.setData({
        title: "问卷调查",
        isType:false
      })
    }

    that.nowQuestion();
  },
  nowQuestion(){
    this.setData({
      question: this.data.questionList[this.data.nowIndex]
    })
    if(this.data.nowIndex == this.data.questionList.length-1){
    if(this.data.type == 101){
      this.setData({
        btnText: "提交考试"
      })
    }else{
      this.setData({
        btnText: "提交问卷"
      })
    }
  }

    console.log(this.data.nowIndex,":当前题目数")
  },

  doNext(){
    
    console.log(this.data.nowIndex,":加1后",this.data.questionList.length,"zongti")
    if (this.data.nowIndex == this.data.questionList.length-1) {
     
      

      let question = this.data.questionList[this.data.nowIndex]

      this.data.studentTestsFrom.selfChecks.push(this.data.radioValue)//选择答案
      this.data.studentTestsFrom.ok_checks.push(question.selectOk)//正确答案
      this.data.studentTestsFrom.tb_ids.push(question.id)//ids

      if (this.data.radioValue == question.selectOk) {
        this.data.studentTestsFrom.ok_num += 1
      }

      var app = getApp();
      let clazz = JSON.parse(app.globalData.clazz)

      this.data.studentTestsFrom.stuCode = wx.getStorageSync("stuCode")
      this.data.studentTestsFrom.testsCode = this.data.testsCode 
      this.data.studentTestsFrom.type = this.data.type 
      this.data.studentTestsFrom.score = parseInt(this.data.studentTestsFrom.ok_num*(100/this.data.questionList.length))
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

            setTimeout(function () {
              wx.navigateBack({
                delta: 2
              })
             }, 2000) //延迟时间 这里是2秒
						

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
      console.log(this.data.nowIndex,":else", this.data.questionList.length, "总提数")

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

    if(this.data.type == 101){
      this.setData({
        btnText: "提交考试"
      })
    }else{
      this.setData({
        btnText: "提交问卷"
      })
    }


    wx.showToast({
      title: '倒计时结束,3s后自动提交',
      icon: 'none',
      duration: 2000
    })

    let that = this
    setTimeout(function () {
      that.submitTests()
     }, 3000) //延迟时间 这里是2秒


    
    
  },
  onChangeSelect(event){
    this.setData({
			radioValue: event.detail,
		});
    console.log(this.data.radioValue)
  },
  submitTests(){
    var app = getApp();
    let clazz = JSON.parse(app.globalData.clazz)

    this.data.studentTestsFrom.stuCode = wx.getStorageSync("stuCode")
    this.data.studentTestsFrom.testsCode = this.data.testsCode 
    this.data.studentTestsFrom.type = this.data.type 
    this.data.studentTestsFrom.score = parseInt(this.data.studentTestsFrom.ok_num*(100/this.data.questionList.length))
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

          setTimeout(function () {
            wx.navigateBack({
              delta: 2
            })
           }, 2000) //延迟时间 这里是2秒

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
  }
})

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

    if(this.data.nowIndex == this.data.questionList.length-1){
      this.setData({
        btnText: "交卷"
        //wx.request
      })
    }else{
      this.setData({
        nowIndex: this.data.nowIndex+1
      })
      this.nowQuestion();
    }

  },
  finished() {
    Toast('倒计时结束');
  },
})
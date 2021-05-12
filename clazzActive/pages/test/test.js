
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    percent: 0,
    btnText: '下一题',
    rightNum: 0,
    idx: 0,
    length: 0,
    question: {

    },
    selectedOption: {
      code: '',
      content: '',
      value: -1
    },
    errNum: 0,
    rightNum: 0,
    score_arr: [],
    options_arr: [],
    options: [
      {
        title: 'A',
        select: '100'
      },
      {
        title: 'B',
        select: '300'
      },
      {
        title: 'C',
        select: '50'
      },
      {
        title: 'D',
        select: '20'
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  // onLoad: function (options) {
  //   console.log(options);
  //   let id = options.id;
  //   this.setData({
  //     dayid: id
  //   })
  //   this.onQuery(id);
  //   this.queryQues('01');
  //   this.onGetOpenid();
  //   let ordernum = this.generate();
  //   this.setData({
  //     ordernum
  //   })
  // },
  // onQuery: function(id){
  //   let items = ['01','02','03','04','05','06','07','08','09','10'];
  //   this.setData({
  //     items: items,
  //     length: items.length
  //   })
  // },
  // queryQues: function(id){
  //   let that = this;
  //   const db = wx.cloud.database();

  //   db.collection('questions').doc(id)
  //   .get()
  //   .then(res => {
  //     console.log('[数据库] [查询记录] 成功: ', res)
  //     let question = res.data;
  //     let options = question.options;
  //     options.map((option)=>{
  //       option.selected = false;
  //     })
  //     that.setData({
  //       question,
  //       options
  //     })
  //   })
  // },

  // generate: function(){
  //   return util.formatTime(new Date());
  // },

  // 选项
  selectOption: function(e){
    console.log(e.currentTarget.dataset);
    // let selectedOption = JSON.parse(e.currentTarget.dataset.value);
    // let options = this.data.options;
    // options.map((option)=>{
    //   if(option.code == selectedOption.code){
    //     option.selected = !option.selected;
    //   }
    // });
    // this.setData({
    //   options,
    //   selectedOption
    // })
  },
  
  // goResult: function(){
  //     let url = '/pages/examresult/examresult?length='+this.data.length+'&errNum='+this.data.errNum+'&rightNum='+this.data.rightNum+'&ordernum='+this.data.ordernum;
  //     wx.navigateTo({
  //       url: url
  //     })
  // },
  // generate: function(){
  //   return util.formatTime(new Date());
  // },
  

  // 下一题
  doNext: function(){
    console.log('doNext')
    
    
    
    let idx = this.data.idx;
    let length = this.data.length;
    idx++;

    let options = this.data.options;
    let isRight = true;
    for (const option of options) {
      console.log(option);
      if(option.selected == true && option.value == 0){
        isRight = false;
        break;
      }
    }
    let rightNum = this.data.rightNum;
    let errNum = this.data.errNum;
    if(isRight){
      rightNum++;
    }else{
      errNum++;
      this.addNote(options);
    }
    let score_arr = this.data.score_arr;
    let options_arr = this.data.options_arr;
    score_arr[this.data.idx] = isRight;
    options_arr[this.data.idx] = options;

    let items = this.data.items;
    
    let percent = ((idx+1)/length)*100;
    if(idx == length){
      this.setData({
        rightNum,
        errNum,
        score_arr,
        options_arr
      },()=>{
        this.addHistory();
        this.goResult();
      })
      return;

    }

    if(length-idx == 1){
      this.setData({
        btnText: '完成'
      })
      wx.showToast({
        icon: 'none',
        title: '已经是最后一题了'
      })
    }

    let id = items[idx];
    this.queryQues(id);

    this.setData({
      rightNum,
      errNum,
      score_arr,
      options_arr,
      idx,
      percent,
      selectedOption: {
        code: '',
        content: '',
        value: -1
      }
    },()=>{
      
    })
  },

  // onGetOpenid: function() {
  //   let that = this;
  //   // 调用云函数
  //   wx.cloud.callFunction({
  //     name: 'login',
  //     data: {},
  //     success: res => {
  //       console.log('[云函数] [login]: ', res)
  //       app.globalData.openid = res.result.openid
  //       that.setData({
  //         openid: res.result.openid
  //       })
  //       // wx.navigateTo({
  //       //   url: '../userConsole/userConsole',
  //       // })
  //     },
  //     fail: err => {
  //       console.error('[云函数] [login] 调用失败', err)
  //       wx.navigateTo({
  //         url: '../deployFunctions/deployFunctions',
  //       })
  //     }
  //   })
  // }
  onLoad: function() {
    wx.getStorage({
      key: 'clazz',
      success (res) {
        this.setData({
          clazzId: res.data.id
        })
        console.log(res.data)
      }
    })

    wx.request({
      url: 'http://127.0.0.1:8080/tests/'+this.data.clazzId+'/findListByClazz',
      method:'GET',
      success(res) {
        if (res.data.code == 200) {
           console.log(res.data)
        } else {
          wx.showToast({
            title: '获取题目失败',
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
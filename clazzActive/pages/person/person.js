const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    openid: '',
    nickName: '',
    avatarUrl: '',
    alterShow: false,
    userInfo: {
      nickName: '',
      avatarUrl: '',
    },
    password:'',
    studentFrom:{
      code:'',
      password:''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  // onLoad: function (options) {
  //   console.log(app.globalData.userInfo);
  //   this.setData({
  //     userInfo: app.globalData.userInfo
  //   })
  //   let _this = this;
  //   wx.login({
  //     success (res) {
  //       console.log(res);
  //       if (res.code) {
  //         //发起网络请求
  //         wx.request({
  //           url: 'https://www.xiaomutong.com.cn/web/index.php?r=wechat/getinfo4',
  //           method: 'post',
  //           data: {
  //             code: res.code
  //           },
  //           success (res) {
  //             console.log(res.data);
  //             if(res.data && res.data.code == 0){
  //               let result = JSON.parse(res.data.result);
  //               console.log(result);
  //               _this.Getuserinfobyopenid(result.openid);
  //               _this.setData({
  //                 openid: result.openid
  //               })
  //             }
  //           },
  //           fail (err){
  //             console.log(err);
  //           }
  //         })
  //       } else {
  //         console.log('登录失败！' + res.errMsg)
  //       }
  //     }
  //   })  
  // },
  Getuserinfobyopenid: function(openid){
    let _this = this;
    wx.request({
      url: 'https://www.xiaomutong.com.cn/web/index.php?r=userinfo/getuserinfobyopenid',
      method: 'post',
      data: {
        openid: openid
      },
      success (res) {
        console.log(res.data);
        if(res.data && res.data.code == 0 && res.data.result){
          console.log(res.data);
          let userInfo = JSON.parse(decodeURIComponent(res.data.result.userinfo));
          let avatarUrl = userInfo.avatarUrl;
          let nickName = userInfo.nickName;
          _this.setData({
            userInfo: userInfo,
            avatarUrl: avatarUrl,
            nickName: nickName
          })
        }
      },
      fail (err){
        console.log(err);
      }
    })
  },
  // bindMyHuodong: function(){
  //   let url = '/pages/activity/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindMyHistory: function(){
  //   let url = '/pages/history/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },  
  // bindMyStudy: function(){
  //   let url = '/pages/study/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },    
  // bindgoname: function(){
  //   let url = '/pages/name/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindmyinfo: function(){
  //   let url = '/pages/notice/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindgopay: function(){
  //   let url = '/pages/pay/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindgosend: function(){
  //   let url = '/pages/send/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindgoabout: function(){
  //   let url = '/pages/about/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },
  // bindgorule: function(){
  //   let url = '/pages/rule/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // }, 
  // bindgomode: function(){
  //   let url = '/pages/mode/index';
  //   wx.navigateTo({
  //     url: url
  //   })
  // },   
  
  alterPassword(){
      this.setData({
        alterShow:true
      })
      this.data.studentFrom.code = wx.getStorageSync("stuCode")
      
  },
  setPassword(event){

    this.setData({
      password:event.detail
    })
    this.data.studentFrom.password = this.data.password
   
  },

  alterPasswordSubmit(){
    console.log("修改密码提交")
    
    wx.request({
      url: 'http://127.0.0.1:8080/student/alterPassword',
      method:'PUT',
      data: this.data.studentFrom,
      header: {
        'Authorization': wx.getStorageSync("token")
      },
      success(res) {
        if (res.data.code == 200) {

          wx.showToast({
            title: '修改成功',
            icon: 'success',
            duration: 2000
          })
          setTimeout(function () {
            wx.navigateTo({
              url: "/pages/login/login",
            })
           }, 2000) //延迟时间 这里是2秒
          

        } else {
          wx.showToast({
            title: '修改失败',
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

  },
  onClose() {
    this.setData({ alterShow: false });
  },

  bindSelfTestsList(){
    wx.navigateTo({
      url: "/pages/selfTestList/selfTestList",
    })
  }

})
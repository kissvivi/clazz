module.exports = {
  plugins: {
    autoprefixer: {},
    'postcss-px2rem-exclude': {
    //37.5是根据Vant组件的设计稿是375得来的,lib-flexible插件默认是将375分为10等分,如果UI组件的设计稿是750,那么这里的设置就是填750/10=75
      remUnit: 37.5,
      exclude: /folder_name/i
    }
  }
}
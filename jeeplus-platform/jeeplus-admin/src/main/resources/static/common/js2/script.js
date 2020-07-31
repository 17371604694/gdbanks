Vue.component("carousel", {
  template: "#v-carousel",
  data() {
    return {
      currentOffset: 0,
      windowSize: 3,
      paginationFactor: 408,
      items: [
        {title:'荣誉展示',name: '光大银行  |  打造温暖的数字金融服务', tag: "光大银行已形成“科技驱动、数据引领、开放服务、生态协同”的数字化发展策略，重点推进“一体、两翼、三动力、四技术”布局。科技驱动、数据引领、开放服务、生态协同”的数字化发展策略",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
         {title:'阳光服务',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
         {title:'光大家园',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
         {title:'阳光青年',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
         {title:'公示公告',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
         {title:'其它',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
       {title:'荣誉展示',name: '光大银行  |  打造温暖的数字金融服务', tag: "企业管理我是标题是很:智能机器我是标题是很:智能机器人我是标题是很:智能机器人我是标题是很:智能机器人人是我：",linkpath:"www.baidu.com",img:"../../../static/common/image2/img_fw_01.png"},
      ]
    }
  },
  computed: {
    atEndOfList() {
      return this.currentOffset <= (this.paginationFactor * -1) * (this.items.length - this.windowSize);
    },
    atHeadOfList() {
      return this.currentOffset === 0;
    },
  },
  methods: {
    moveCarousel(direction) {
      // Find a more elegant way to express the :style. consider using props to make it truly generic
      if (direction === 1 && !this.atEndOfList) {
        this.currentOffset -= this.paginationFactor;
      } else if (direction === -1 && !this.atHeadOfList) {
        this.currentOffset += this.paginationFactor;
      }
    },
  }
});

new Vue({
  el:"#app"
});
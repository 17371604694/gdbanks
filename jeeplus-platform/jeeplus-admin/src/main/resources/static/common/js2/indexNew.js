$(function () {
    var _this;
    var vm = new Vue({
        el: '#indexNew',
        data:function() {
            return{
                userInfo:"000",
                ctx:"/a",
                currentOffset: 0,
                windowSize: 3,
                paginationFactor: 408,
                itemsHonor: [],
                gdjy:[],
                ygqn:[],
                ryzs:[],
                gsgg:[],
                sjyh:[],
                sjyhjx:[],
                dgbkjx:[],
                ygfwjx:[],
                ygqnjx:[],
                gdjyjx:[],
                ryzsjx:[],
                gsggjx:[],
                headOffices: [],//总行要闻集合
                headOffice:{},  //切换的每个
                headIndex:0,
                nowtime:[],

                tabType:[
                    {
                        type:true,
                        class1:"text24_b_y",
                        class2:"qh_news"
                    },
                    {
                        type:false,
                        class1:"text24_b_b",
                        class2:"qh_news_line"
                    },
                    {
                        type:true,
                        class1:"text24_b_b",
                        class2:"qh_news_line"
                    }
                ],

                tab2Type:[
                    {
                        class1:"zx_select"
                    },
                    {
                        class1:"zx_select_no"
                    },
                    {
                        class1:"zx_select_no"
                    },
                    {
                        class1:"zx_select_no"
                    }
                ],

                byTheNews:[], //图文新闻
                fenHang:[],//分行动态/工作通知/业务通知
                fenHangNameTs:{
                    "id":"",
                    "name": encodeURI("分行动态")
                },//当前是分行还是业务或者工作,默认是分行
                yxTs:{
                    "id":"97e59921be1e4e178eb4dc8b48cc91ee",
                    "name": encodeURI("营销动态")
                },//默认是营销动态
                meetingInfos:[], //会议通知
                threeEliteInfos:[], //三名四精
                tellingInfos:[],  //营销动态
                tellingInfosImg:[], //营销动态--图片
                tellingInfosImgJx:[], //营销动态精选--图片
                mienwallInfos:[], //风采墙
                statistics1:[], //部门排行
                statistics2:[], //活跃度
                statistics3:[], //热度排行
                djyl:[],
                djyljx:[],
                selectParim:"欢迎访问光大内网！", //头部搜索
                navMenu:[], //分行部室
                navMenuInstitutions:[], //经营机构
                yglb:[],//阳光、家园、荣誉轮播的标题
                byTheNewsIndex:0 //当前图文新闻的,
            }
        },
        computed: {
            atEndOfList:function () {
                return this.currentOffset <= (this.paginationFactor * -1) * (7 - this.windowSize);

            },
            atHeadOfList:function () {
                return this.currentOffset === 0;
            },
        },
        mounted :function(){
            _this=this;
            this.ctx=$("#userctx").val();
            this.now();
            this.redirectInit();  //分行部室----经营机构
            this.getGaoZong(encodeURI("总行要闻"),encodeURI("首页新闻"));
            this.getTuWei(encodeURI("图文新闻"),encodeURI("首页新闻"));
            this.getFenHang(encodeURI("分行动态"),encodeURI("首页新闻"));
            this.meetingInfo(encodeURI("会议通知"),encodeURI("首页新闻"));//会议通知
            this.ThreeEliteInfo(encodeURI("三名四精"),encodeURI("首页新闻"));//三名四精
            this.tellingInfo(encodeURI("营销动态"),encodeURI("首页新闻"));//营销动态
            // this.tellingInfoJx(encodeURI("营销动态精选"),encodeURI("首页新闻"));//营销动态精选（需求改变为不展示）
            this.honor(); //荣誉、光大家园、阳光轮播板块数据加载方法
            this.honorJx(); //荣誉、光大家园、阳光轮播精选板块数据加载方法
            this.mienwallInfo(); //风采墙
            // this.statisticsInit(); //排行榜数据加载(需求改变为不展示)
            //定时切换总行要闻
            setInterval(function () {
                if (_this.headIndex>=_this.headOffices.length-1){
                    _this.headIndex=0;
                } else {
                    _this.headIndex= _this.headIndex+1;
                }
                _this.headOffice=_this.headOffices[_this.headIndex];
            },3000); //3秒
            //将Vue方法传到全局对象window中
            window.moveCarousel = this.moveCarousel;
        },
        methods: {
            selectParimfocus:function (){
                if (_this.selectParim=="欢迎访问光大内网！" || _this.selectParim=="请输入要搜索的标题内容！"){
                    _this.selectParim="";
                }
            },
            selectParimBlur:function (){
                if (_this.selectParim==""){
                    _this.selectParim="欢迎访问光大内网！";
                }
            },
            /**
             * 搜索转跳
             */
            selectParimClick:function (){
                if (_this.selectParim=="欢迎访问光大内网！" || _this.selectParim==""){
                    _this.selectParim="请输入要搜索的标题内容";
                } else {
                    location.href="/a/logins/list?title="+encodeURI(this.selectParim.trim());
                }
            },
            //修改用户信息
            updateUserInfo:function (e){
                if (e.target.innerHTML!="登录") {
                    layer.open({
                        type: 2,
                        shadeClose:true,
                        id: 'update_user',
                        title: ['修改用户信息', 'font-size:18px;'],
                        area: ['1000px', '650px'],
                        content: '/a/sys/user/info'
                    });
                }
            },
            now:function(){
                _this.nowtime = [];
                var day2 = new Date();
                var nian = day2 .getFullYear();
                var yue = day2 .getMonth()+1;
                if(yue<10){
                    yue = '0'+yue;
                }
                var tian = day2 .getDate();
                if(tian<10){
                    tian = '0'+tian;
                }
                var now = nian+'-'+yue+'-'+tian;
                _this.nowtime = now;
            },
            //轮播点击事件
            moveCarousel:function(direction) {
                if (direction === 1 && !this.atEndOfList) {
                    this.currentOffset -= this.paginationFactor;
                } else if (direction === -1 && !this.atHeadOfList) {
                    this.currentOffset += this.paginationFactor;
                }
                window.currentOffset = this.currentOffset;
            },
            //获取稿件(名称和父名称)总行要闻
            getGaoZong:function(name,nameparent){
                _this.headOffices=[];
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
                    for (var i = 0; i <(data.length>=4?4:data.length ); i++) {
                        var myDate = new Date();
                        var time=myDate.toLocaleDateString().replace('/','-').replace('/','-').substring(0,11);
                        var week = "星期"+weekArray[myDate.getDay()];
                        _this.headOffices.push({
                            id:data[i].id,
                            programatId:data[i].programatId,
                            programatIdName:data[i].programatIdName,
                            title:_this.htmlEncode(data[i].title.substring(0,50)),
                            time:time,
                            week:week,
                            day:myDate.getDate()
                        });
                    }
                    _this.headOffice=_this.headOffices[_this.headIndex];
                });
            },
            //获取稿件(名称和父名称)----//图文新闻
            getTuWei:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    //   console.log(data)
                    for (var i = 0; i <(data.length>=3?3:data.length) ; i++) {
                        var title= _this.htmlEncode(data[i].title.replace( /^\s/, '')) ;
                        //不展示正文（提升查询速度）
                        // var content=data[i].content.replace( /^\s/, '');
                        // content=_this.htmlEncode(content);
                        // var da =content;
                        // da = da.replace(/(\n)/g, "");
                        // da = da.replace(/(\t)/g, "");
                        // da = da.replace(/(\r)/g, "");
                        // da = da.replace(/<\/?[^>]*>/g, "");
                        // da = da.replace(/\s*/g, "");
                        // content=da;
                        var accessorys=data[i].accessorys;
                        var time=data[i].createDate.substring(0,10);
                        _this.byTheNews.push({
                            accessorysImg:data[i].accessorysImg, //图片地址
                            title:title,
                            // content:content,
                            accessorys:data[i].accessorys, //视频地址
                            time:time,
                            id:data[i].id
                        })
                    }
                });
            },
            //分行动态/工作通知/业务通报
            getFenHang:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data);
                    // alert(_this.nowtime);
                    for (var i = 0; i <(data.length>=8?8:data.length ); i++) {
                        data[i].title=_this.htmlEncode(data[i].title);
                        data[i].createDate = data[i].createDate.substring(0,10);
                    }
                    if (data==null || data==""){
                        _this.fenHang=[];
                        _this.fenHang.push({
                            title:"栏目暂无发稿",
                            programatIdName:name
                        })
                    } else {
                        _this.fenHang=data;
                        _this.fenHangNameTs.id=data[0].programatParentid;
                    }
                });
            },
            //会议通知
            meetingInfo:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data)
                    for (var i = 0; i < (data.length >= 6 ? 6 : data.length); i++) {
                        var title=_this.htmlEncode(data[i].title.replace( /^\s/, ''));
                        _this.meetingInfos.push({
                            id:data[i].id,
                            title:title,
                            createDate:data[i].createDate.substring(0,10),
                            sorts:data[i].sorts,
                            meetingTime:data[i].meetingTime
                        })
                    }
                });
            },
            /*三名四精*/
            ThreeEliteInfo:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data)
                    for (var i = 0; i < (data.length >= 4 ? 4 : data.length); i++) {
                        var content=_this.htmlEncode(data[i].title.replace( /^\s/, ''));
                        _this.threeEliteInfos.push({
                            id:data[i].id,
                            title:content,
                            createDate:data[i].createDate.substring(0,10),
                            sorts:data[i].sorts
                        })
                    }
                });
            },
            //营销动态精选（点击切换栏目模块）
            tellingInfoJx:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data)
                    _this.tellingInfosImgJx=[];
                    if (data.length==0){
                        layer.msg("精选栏目暂无发稿");
                        return;
                    }
                    for (var i = 0; i < (data.length >= 3 ? 3 : data.length); i++) {
                        var title=_this.htmlEncode(data[i].title.replace( /^\s/, ''));
                        var imgst=data[i].accessorys.split('|');//只展示第一条数据的图片
                        _this.tellingInfosImgJx.push({
                            id:data[i].id,
                            title:title,
                            img:data[i].accessorys
                        })
                    }
                });
            },

            //营销动态（点击切换栏目模块）
            tellingInfo:function(name,nameparent){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data)
                    _this.tellingInfos=[];
                    _this.tellingInfosImg=[];
                    if (data.length==0){
                        layer.msg("栏目暂无发稿");
                        return;
                    }
                    for (var i = 0; i < (data.length >= 10 ? 10 : data.length); i++) {
                        var title=_this.htmlEncode(data[i].title.replace( /^\s/, '')).substring(0,26);
                        _this.tellingInfos.push({
                            id:data[i].id,
                            title:title,
                            time:data[i].createDate,
                            createDate:data[i].createDate.substring(0,10),
                            sorts:data[i].sorts
                        })
                    }
                });
            },
            //荣誉板块
            honorAdd:function(name,nameparent,datalist){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data);
                    if (data.length>0) {
                        for (var i = 0; i < (data.length >= 6 ? 6 : data.length); i++) {
                            // console.log(data)
                            var title=_this.htmlEncode(data[i].title.replace( /^\s/, ''));
                            datalist.push({
                                id:data[i].id,
                                name:title,
                                tag:title,
                                img:data[i].accessorys,
                                createDate:data[i].createDate.substring(0,10),
                                sorts:data[i].sorts
                            });
                        }
                    }
                });
            },
            //荣誉板块精选
            honorJxAdd:function(name,nameparent,datalist){
                $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
                    // console.log(data);
                    if (data.length>0) {
                        for (var i = 0; i < (data.length >= 3 ? 3 : data.length); i++) {
                            var title=_this.htmlEncode(data[0].title.replace( /^\s/, ''));
                            datalist.push({
                                id:data[0].id,
                                name:title,
                                tag:title,
                                linkpath:"/a/logins/programadatalist?id="+data[0].programatId+'&name='+data[0].programatIdName,
                                img:data[0].accessorys,
                                createDate:data[0].createDate.substring(0,10),
                                sorts:data[0].sorts
                            });
                        }
                    }
                });
            },
            //荣誉板块精选加载方法
            honorJx:function(){
                _this.honorAdd(encodeURI("党建引领精选"),encodeURI("首页新闻"),this.djyljx);
                _this.honorAdd(encodeURI("阳光服务精选"),encodeURI("首页新闻"),this.ygfwjx);
                _this.honorAdd(encodeURI("阳光青年精选"),encodeURI("首页新闻"),this.ygqnjx);
                _this.honorAdd(encodeURI("光大家园精选"),encodeURI("首页新闻"),this.gdjyjx);
                _this.honorAdd(encodeURI("荣誉展示精选"),encodeURI("首页新闻"),this.ryzsjx);
                _this.honorAdd(encodeURI("手机银行精选"),encodeURI("首页新闻"),this.sjyhjx);
                _this.honorAdd(encodeURI("公示公告精选"),encodeURI("首页新闻"),this.gsggjx);
            },
            //荣誉板块加载方法
            honor:function(){
                _this.honorAdd(encodeURI("党建引领"),encodeURI("首页新闻"),this.djyl);
                _this.honorAdd(encodeURI("阳光服务"),encodeURI("首页新闻"),this.itemsHonor);
                _this.honorAdd(encodeURI("阳光青年"),encodeURI("首页新闻"),this.ygqn);
                _this.honorAdd(encodeURI("光大家园"),encodeURI("首页新闻"),this.gdjy);
                _this.honorAdd(encodeURI("荣誉展示"),encodeURI("首页新闻"),this.ryzs);
                _this.honorAdd(encodeURI("手机银行"),encodeURI("首页新闻"),this.sjyh);
                _this.honorAdd(encodeURI("公示公告"),encodeURI("首页新闻"),this.gsgg);
            },
            //优秀党员工作者--风采墙
            mienwallInfo:function(){
                $.getJSON("/a/public/userInfo",function (data) {
                    //console.log(data)
                    var size = data.length;
                    var needsize = 6-size;
                    for (var i = 0; i < data.length; i++) {
                        _this.mienwallInfos.push({
                            photo:data[i].photo
                        })
                    }
                    if(needsize>0){//如果图片不够则补足
                        for(var i = 0;i<needsize;i++){
                            // alert(needsize)
                            if(i%2==0){
                                _this.mienwallInfos.push({
                                    photo:"/static/common/image2/ygfc_01.png"
                                })
                            }else {
                                _this.mienwallInfos.push({
                                    photo:"/static/common/image2/ygfc_03.png"
                                })
                            }
                        }
                    }
                });
            },
            /*
            * /**
             * 排行榜 typesection     typeactive   typeheat
             *初始查询本月
            * */
            statistics:function(type,month,icot){
                $.getJSON("/a/public/statistics?type="+type+"&month="+month,function (data) {
                    //  console.log(data);
                    if (data.length>0){
                        if (icot==1){
                            _this.statistics1=data;
                        }
                        if (icot==2){
                            _this.statistics2=data;
                        }
                        if (icot==3){
                            for (var i = 0; i < (data.length >= 5 ? 5 : data.length); i++) {
                                data[i].officename =_this.htmlEncode(data[i].officename);
                            }
                            _this.statistics3=data;
                        }
                    }
                });
            },
            statisticsInit:function(){
                _this.statistics(1,2,1); //部门
                _this.statistics(2,2,2);  //活跃
                _this.statistics(3,2,3);  //热度
            },
            //排行榜切换---部门
            statistics1Tab:function(e){
                // console.log(e)
                // console.log(e.target.value)
                _this.statistics(1,e.target.value,1); //部门
            },
            //排行榜切换---活跃
            statistics2Tab:function(e){
                _this.statistics(2,e.target.value,2); //活跃
            },
            //排行榜切换---热度
            statistics3Tab:function(e){
                _this.statistics(3,e.target.value,3); //热度
            },
            //营销动态,指引,综合,内控   点击切换
            dlzn:function(ind){
                for (var i = 0; i <_this.tab2Type.length ; i++) {
                    if (i==ind){
                        _this.tab2Type[i].class1='zx_select';
                    } else{
                        _this.tab2Type[i].class1='zx_select_no';
                    }
                }
                if (ind==0){
                    _this.tellingInfo(encodeURI("营销动态"),encodeURI("首页新闻"));
                    // _this.tellingInfoJx(encodeURI("营销动态精选"),encodeURI("首页新闻"));（需求改变为不展示）
                    _this.yxTs.name=encodeURI("营销动态");
                    _this.yxTs.id=encodeURI("97e59921be1e4e178eb4dc8b48cc91ee");
                }
                if (ind==1){
                    _this.tellingInfo(encodeURI("营销指引"),encodeURI("首页新闻"));
                    // _this.tellingInfoJx(encodeURI("营销指引精选"),encodeURI("首页新闻"));需求改变为不展示）
                    _this.yxTs.name=encodeURI("营销指引");
                    _this.yxTs.id=encodeURI("bbc61b7435e44de488c230b3c296df54");
                }
                if (ind==2){
                    _this.tellingInfo(encodeURI("综合支持"),encodeURI("首页新闻"));
                    // _this.tellingInfoJx(encodeURI("综合支持精选"),encodeURI("首页新闻"));需求改变为不展示）
                    _this.yxTs.name=encodeURI("综合支持");
                    _this.yxTs.id=encodeURI("8a17d9cc3f0941b3985a053cdc75b891");
                }
                if (ind==3){
                    _this.tellingInfo(encodeURI("内控合规"),encodeURI("首页新闻"));
                    // _this.tellingInfoJx(encodeURI("内控合规精选"),encodeURI("首页新闻"));需求改变为不展示）
                    _this.yxTs.name=encodeURI("内控合规");
                    _this.yxTs.id=encodeURI("4c1b80219888478592953f7fb0857558");
                }
            },
            //不忘初心，牢记使命
            bwcx:function(){
                location.href = encodeURI("/a/logins/programadatalist?id=b15399c084db4251bac855a56cee04a0&name=不忘初心,牢记使命")
            },
            //点击切换
            tabThree:function(inde){
                if (inde==0){
                    _this.getFenHang(encodeURI("分行动态"),encodeURI("首页新闻"));
                    _this.fenHangNameTs.name=encodeURI("分行动态");
                }
                if (inde==1){
                    _this.getFenHang(encodeURI("工作通知"),encodeURI("首页新闻"));
                    _this.fenHangNameTs.name=encodeURI("工作通知");
                }
                if (inde==2){
                    _this.getFenHang(encodeURI("业务通报"),encodeURI("首页新闻"));
                    _this.fenHangNameTs.name=encodeURI("业务通报");
                }
                _this.fenTree={};
                for (var i = 0; i < _this.tabType.length ; i++) {
                    if (inde==i) {
                        _this.tabType[i].type=true;
                        _this.tabType[i].class1="text24_b_y";
                        _this.tabType[i].class2="qh_news";

                    }else {
                        _this.tabType[i].type=false;
                        _this.tabType[i].class1="text24_b_b";
                        _this.tabType[i].class2="qh_news_line";
                    }
                }
            },
            //点击播放视频
            payVideo:function(path,title){
                layer.open({
                    type: 1,
                    shadeClose:true,
                    title: [title, 'font-size:18px;'],
                    area: ['1030px', '650px'],
                    content: '<center><video id="video" autoplay="autoplay" src="'+path+'" controls="controls" width="1000px" height="600px" ></center>'
                });
            },
            //首页导航栏转跳
            redirectsFor:function(ind){
                if (ind==0){ //首页
                    location.href="/a/logins/indexNew"
                }
                if (ind==1){ //分行动态
                    location.href="/a/logins/programadatalist?id=4f8d74dc230b49e5b9aba3d7386f85bf&name="+encodeURI("分行动态")
                }
                if (ind==4){ //规章制度
                    location.href="/a/logins/programadatalist?id=06cdcb5fdbfa4aa7beb9718f2817b9f4&name="+encodeURI("规章制度")
                }
                if (ind==5){ //下载专区
                    location.href="/a/logins/down"
                }
                if (ind==6){ //通讯录
                    location.href="/a/logins/addressbook"
                }

            },
            //分行部室----经营机构
            redirectInit:function(){
                $.getJSON("/a/public/getNavMenu",function (data) {
                    // console.log(data)
                    _this.navMenu=data;
                });
                $.getJSON("/a/public/getNavMenuInstitutions",function (data) {
                    // console.log(data);
                    _this.navMenuInstitutions=data;
                });
            },
            //点击切换滚动标题
            tabTitle:function(ind){
                _this.headIndex=ind;
                // console.log(ind);
                _this.headOffice=_this.headOffices[ind];

            },
            // //投票
            // Toupiao () {
            //     $.post("/a/public/voteinfodata",{},function (data) {
            //         console.log(data);
            //         _this.toupiao.push({
            //             id:data[0].id,
            //             themeName:data[0].themeName
            //         })
            //
            //     })
            //     // console.log();
            // },

            logout:function(){
                location.href=_this.ctx+"/logout";
            },
            //将转义字符转为普通html字符--
            htmlEncode:function (str){
                var s = "";
                if(str.length == 0) return "";
                s = str.replace(/&amp;/g,"&");
                s = s.replace(/&lt;/g,"<");
                s = s.replace(/&gt;/g,">");
                s = s.replace(/&nbsp;/g," ");
                s = s.replace(/'/g,"\'");
                s = s.replace(/"/g,"\"");
                s = s.replace(/&quot;/g,"\"");
                s = s.replace(/&rdquo;/g,"”");
                s = s.replace(/&ldquo;/g,"“");
                return _this.htmlDecode(s);
            },
            htmlDecode:function (text){
                //1.首先动态创建一个容器标签元素，如DIV
                var temp = document.createElement("div");
                //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
                temp.innerHTML = text;
                //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
                var output = temp.innerText || temp.textContent;
                temp = null;
                return output;
            }

        }
    });

})
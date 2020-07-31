$(function () {


    var mySwiper0 = new Swiper('.swiper-container0', {
        autoplay: {
            delay: 3000,
            disableOnInteraction: false,
        },
        touchRatio:0.5,
        navigation: {
            nextEl: '.swiper-button-next0',
            prevEl: '.swiper-button-prev0',
        },
       /* on: {
            touchEnd: function (event) {
                //你的事件
                alert("dfdfd");
            },
        }*/


    });
    var mySwiper1 = new Swiper('.swiper-container1', {
       // effect : 'coverflow',
        slidesPerView: 5,
        spaceBetween: 10,
        navigation: {
            nextEl: '.swiper-button-next1',
            prevEl: '.swiper-button-prev1',
        },
        pagination: {
            el: '.swiper-pagination1'
        }
    });
    var mySwiper2 = new Swiper('.swiper-container2', {
        autoplay: {
            delay: 3000,
            disableOnInteraction: false,
        },
        slidesPerView: 5,
        spaceBetween: 10,
        navigation: {
            nextEl: '.swiper-button-next2',
            prevEl: '.swiper-button-prev2',
        },
        pagination: {
            el: '.swiper-pagination2'
        }
    });

   // layer.msg("登录成功");
     var httpPath="http://localhost:8088/";


     //对于可能重复的栏目,请添加父栏目(上一级栏目名称)
    test("分行动态","首页新闻");
    meetingInfo();
    ThreeEliteInfo();
    mienwallInfo();
    synthesizeInfo("综合支持","首页新闻");
    nkhgInfo("内控合规","首页新闻");
    retailInfo("零售版块","首页新闻");
    tellingInfo("对公版块","首页新闻");
    zhyw("总行要闻","首页新闻");
    getVideo("图文新闻","首页新闻");


    /*稿件*/
    $(".box_huise").click(function () {
        test($(this).html().trim(),"首页新闻");
        var index=$(this).index();
       $(".takeType").find("span").each(function (i,e) {
           if (index==i){
               $(e).addClass("take");
           }else {
               $(e).removeClass("take");
           }

       })

    });
    function test(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $(".box1").empty();
         //   $("#newtop").empty();
            //只显示8条,如果大于8条显示更多
            for (let i = 0; i <(data.length>=10?10:data.length ); i++) {

                if (i>2){
                    var $li=`<tr align="left" valign="middle" style=""><td height="40" style="font-size: 14px;font-family: 微软雅黑;"><a href="/a/logins/content/?id=`+data[i].id+`" style="color: rgb(90, 90, 90);text-decoration:none">`+data[i].title+`</a></td></tr>`;
                }else {
                    var $li=`<tr align="left" valign="middle"><td height="40" style="font-size: 14px;font-family: 微软雅黑;font-weight: bold"><a href="/a/logins/content/?id=`+data[i].id+`" style="color: black;text-decoration:none">`+data[i].title+`</a></td></tr>`;
                }
                  $(".box1").append($li);
            }

            $(".box_more").remove();
            if (data.length>10){
                var $tr=`<span class="box_more" style="float: right;position: relative;top: 16px " ><a style="color: #9C78C3" href="/a/logins/programadatalist?id=`+data[0].programatId+`&name=`+name+`">更多+</a></span>`;
                $("#span").append($tr);
            }
            if ($(".box1").find("tr").length<=0){
                var $li=`<tr align="left" valign="middle"><td height="40" style="font-size: 14px;font-family: 微软雅黑;font-weight: bolder">该栏目下暂无数据</td></tr>`;
                $(".box1").append($li);
            }

        });
    }



    /**
     * 视频
     * 读取图文新闻
     */
    function getVideo(name,nameparent){
        // $.getJSON("/a/public/getVideo",function (data) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $(".lb-content").empty();
            for (let i = 0; i <(data.length>=4?4:data.length) ; i++) {
                /*var lenfist=data[i].videoName.length>=25?25:data[i].videoName.length;
                var title=(data[i].videoName.substring(0,lenfist))+"...";*/
                var title=data[i].title.replace( /^\s/, '');
                var content=data[i].content.replace( /^\s/, '');
                content=escape2Html(content);
                var da =content;
                da = da.replace(/(\n)/g, "");
                da = da.replace(/(\t)/g, "");
                da = da.replace(/(\r)/g, "");
                da = da.replace(/<\/?[^>]*>/g, "");
                da = da.replace(/\s*/g, "");
                content=da;
                var accessorys=data[i].accessorys;
                var svgimg="";
                if (accessorys.trim()!="" && accessorys!=null){
                    svgimg=`<svg t="1578034267811" class="icon players" viewBox="0 0 1024 1024" 
                        style="position: absolute;top: 140px;left: 160px; version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2031" 
                        width="70" height="70"><path d="M675.328 117.717333A425.429333 425.429333 0 0 0 512 
                        85.333333C276.352 85.333333 85.333333 276.352 85.333333 512s191.018667 426.666667 426.666667 
                        426.666667 426.666667-191.018667 426.666667-426.666667c0-56.746667-11.093333-112-32.384-163.328a21.333333 
                        21.333333 0 0 0-39.402667 16.341333A382.762667 382.762667 0 0 1 896 512c0 212.074667-171.925333 384-384 384S128 
                        724.074667 128 512 299.925333 128 512 128c51.114667 0 100.8 9.984 146.986667 29.12a21.333333 21.333333 0 0 0 
                        16.341333-39.402667zM456.704 305.92C432.704 289.152 405.333333 303.082667 405.333333 331.797333v360.533334c0 
                        28.586667 27.541333 42.538667 51.370667 25.856l252.352-176.768c21.76-15.253333 21.632-43.541333 0-58.709334l-252.373333-176.768z 
                        m-8.597333 366.72V351.466667l229.269333 160.597333-229.269333 160.597333z" fill="#cdcdcd" p-id="2032"></path></svg>`;
                }
                let time=data[i].createDate.substring(0,10);
                if (i==0){
                    var $div=`<div class="lb-item active">
                    <a href="/a/logins/content?id=`+data[i].id+`">
                       <img src="`+data[i].accessorysImg+`" alt="picture loss">
                        `+svgimg+`
                      <!--div 内容超出指定行数显示...-->
                       <span style="width: 97%;
                        text-overflow: -o-ellipsis-lastline;
                        overflow: hidden;text-overflow: ellipsis;
                        display: -webkit-box;-webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;">`+title+`</span>
                        <span style="color:#CCC;">`+time+`</span>
                        <span style="color:#000; font-size:14px; font-weight:normal;width: 97%;
                        text-overflow: -o-ellipsis-lastline;
                        overflow: hidden;text-overflow: ellipsis;
                        display: -webkit-box;-webkit-line-clamp: 3;
                        -webkit-box-orient: vertical;">`+content+`</span>
                    </a>
                </div>`;
                } else {
                    var $div=`<div class="lb-item">
                    <a href="/a/logins/content?id=`+data[i].id+`">
                        <img src="`+data[i].accessorysImg+`" alt="picture loss">
                        `+svgimg+`
                        <span style="width: 97%;
                        text-overflow: -o-ellipsis-lastline;
                        overflow: hidden;text-overflow: ellipsis;
                        display: -webkit-box;-webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;">`+title+`</span>
                        <span style="color:#CCC;">`+time+`</span>
                        <span style="color:#000; font-size:14px; font-weight:normal;width: 97%;  
                        text-overflow: -o-ellipsis-lastline;
                        overflow: hidden;text-overflow: ellipsis;
                        display: -webkit-box;-webkit-line-clamp: 3;padding: 0;
                        -webkit-box-orient: vertical;">`+content+`</span>
                    </a>
                </div>`;
                }
                $(".lb-content").append($div);
            }
            $(".players").on("click",function () {
                var i=$(this).parents("div").index();
                $(this).parents("a").attr("href","javascript:void()");
              //  alert("==="+data[i].videoPath);
                layer.open({
                    type: 1,
                    shadeClose:true,
                    title: [data[i].title, 'font-size:18px;'],
                    area: ['1030px', '650px'],
                    content: '<center><video id="video" autoplay="autoplay" src="'+data[i].accessorys+'" controls="controls" width="1000px" height="600px" ></center>' //这里content是一个普通的String
                });

            });

            // 轮播选项
            const options = {
                id: 'lb-1',              // 轮播盒ID
                speed: 600,              // 轮播速度(ms)
                delay: 3000,             // 轮播延迟(ms)
                direction: 'left',       // 图片滑动方向
                moniterKeyEvent: true,   // 是否监听键盘事件
                moniterTouchEvent: true  // 是否监听屏幕滑动事件
            }
            const lb = new Lb(options);
            lb.start();

        });

    }



    /*会议数据*/
    function meetingInfo() {
        $.getJSON("/a/public/dataBankConference",function (data) {
            $("#meeting").empty();
            for (let i = 0; i <(data.length>=4?4:data.length); i++) {
                //去除左空格
                var content=data[i].context.replace( /^\s/, '');
                var $tr=`<tr>
                            <td width="5%" height="45" align="center" valign="middle" class="newslist"><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                            <td width="95%" class="newslist">
                            <a style="width: 250px;color: rgb(33, 33, 33);font-family: 微软雅黑;
                            display:block; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" href="/" class="alist16hei">`+content+`</a></td>
                          </tr>`;
                $("#meeting").append($tr);
            }
        });
    }
    /*三名四精*/
    function ThreeEliteInfo(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $("#ThreeElite").empty();
            for (let i = 0; i <(data.length>=4?4:data.length); i++) {
                //  alert(data[i].context);
                var content=data[i].context.replace( /^\s/, '');
               /// content=content+" <span class='layui-badge'style='border-radius: 0px;font-size: 10px'>99</span>";
                var $tr=`<tr>
                        <td width="5%" height="45" align="center" valign="middle" class="newslist"><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                        <td width="95%" class="newslist" style="position: relative;">
                        <a style="width: 220px;color: rgb(33, 33, 33);font-family: 微软雅黑;
                        display:block; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" href="/a/logins/content?id=`+data[i].id+`" 
                        class="alist16hei">`+content+`</a><span class='layui-badge' style='border-radius: 2px;font-size: 6px;position: absolute;left: 87%;top: 14px'>99</span>
                        </td>
                      </tr>`;
                $("#ThreeElite").append($tr);
            }
        });

    }

    /*
    * 营销动态
    *   对公版块telling
    * */
    function tellingInfo(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $("#tellingfist").empty();
            $("#tellingend").empty();
            for (let i = 0; i <(data.length>=6?6:data.length ); i++) {
                //为确定,先显示前两个的详情
                var path=httpPath+data[i].accessorys;
                let time=data[i].createDate.substring(5,10);
                var len="&lt;span style=&quot;color: rgb(51, 51, 51); font-family: arial; font-size: 16px; text-align: justify;&quot;&gt;".length;
                var cont = data[i].content.substring(len,data[i].content.lastIndexOf("&lt;"));

                var lens=data[i].title.length>=38?38:data[i].title.length;
                var conts=data[i].title.length>37?"...":"";
                var content1=(data[i].title.substring(0,lens)+""+conts).replace( /^\s/, '');
                var content=data[i].title.replace( /^\s/, '');
                if (i==0){
                    cont=cont.substring(0,20)+"...";
                    var $li=`<td>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td style="padding: 2px"><a href="/a/logins/content?id=`+data[i].id+`"><img style="border-radius: 2px;position: relative;left: 17px" src="`+path+`" width="195" height="119" /></a></td>
                            </tr>
                            <tr>
                               <td style="padding-left: 18px;padding-right: 17px">
                               <a href="/a/logins/content?id=`+data[i].id+`" class="alist16hei" style="font-family: 微软雅黑;font-size: 14px">`+content1+`</a></td>
                            </tr>
                              <tr>
                                <td class="font_hui_14h inden5px" style="font-family: 微软雅黑;font-weight: normal;padding-left: 12px;">`+time+`</td>
                              </tr>
                          </table>
                      </td>`;
                    $("#tellingfist").append($li);
                }
                if (i==1){
                    cont=cont.substring(0,20)+"...";
                    var $li=`<td>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td style="padding: 2px"><a href="/a/logins/content?id=`+data[i].id+`">
                              <img style="border-radius: 2px;position: relative;right: 0" src="`+path+`" width="195" height="119" /></a></td>
                            </tr>
                            <tr>
                               <td style="position: relative;left: -13px">
                               <a href="/a/logins/content?id=`+data[i].id+`" class="" style="font-family: 微软雅黑;font-size: 14px;
                           
                               ">`+content1+`</a></td>
                            </tr>
                              <tr>
                                <td class="font_hui_14h inden5px" style="font-family: 微软雅黑;font-weight: normal;padding-left: 12px;position: relative;left: -28px">`+time+`</td>
                              </tr>
                          </table>
                      </td>`;
                    $("#tellingfist").append($li);
                }
                if (i>1){
                    var $tr=`<tr>
                        <td width="5%" height="40" align="center" valign="middle" class=""><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                        <td width="100%" class=""><a style="font-family: 微软雅黑;font-size: 14px;
                        display:block; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;
                        " href="/a/logins/content?id=`+data[i].id+`" class="alist17hei">`+content+`</a></td>
                      </tr>`;
                    $("#tellingend").append($tr);
                }


            }
        });
    }
    /*综合支持
    * */
    function synthesizeInfo(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $("#synthesizefist").empty();
            $("#synthesizeend").empty();
            for (let i = 0; i <(data.length>=6?6:data.length ); i++) {
                //为确定,先显示前两个的详情
                var path=httpPath+data[i].accessorys;
                let time=data[i].createDate.substring(5,10);
                var len="&lt;span style=&quot;color: rgb(51, 51, 51); font-family: arial; font-size: 16px; text-align: justify;&quot;&gt;".length;
                var cont = data[i].content.substring(len,data[i].content.lastIndexOf("&lt;"));
                var lens=data[i].title.length>=38?38:data[i].title.length;
                var conts=data[i].title.length>37?"...":"";
                var content1=(data[i].title.substring(0,lens)+""+conts).replace( /^\s/, '');
                var content=data[i].title.replace( /^\s/, '');
                if (i==0){
                    cont=cont.substring(0,20)+"...";
                    var $li=`<td>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td style="padding: 2px"><a href="/a/logins/content?id=`+data[i].id+`"><img style="border-radius: 2px;position: relative;left: 17px" src="`+path+`" width="195" height="119" /></a></td>
                            </tr>
                            <tr>
                               <td style="padding-left: 18px;padding-right: 17px">
                               <a href="/a/logins/content?id=`+data[i].id+`" class="alist16hei" style="font-family: 微软雅黑;font-size: 14px">`+content1+`</a></td>
                            </tr>
                              <tr>
                                <td class="font_hui_14h inden5px" style="font-family: 微软雅黑;font-weight: normal;padding-left: 12px;">`+time+`</td>
                              </tr>
                          </table>
                      </td>`;
                    $("#synthesizefist").append($li);
                }
                if (i==1){
                    cont=cont.substring(0,20)+"...";
                    var $li=`<td>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td style="padding: 2px"><a href="/a/logins/content?id=`+data[i].id+`"><img style="border-radius: 2px;position: relative;right: 0px" src="`+path+`" width="195" height="119" /></a></td>
                            </tr>
                            <tr>
                               <td style="position: relative;left: -13px">
                               <a href="/a/logins/content?id=`+data[i].id+`" class="" style="font-family: 微软雅黑;font-size: 14px">`+content1+`</a></td>
                            </tr>
                              <tr>
                                <td class="font_hui_14h inden5px" style="font-family: 微软雅黑;font-weight: normal;padding-left: 12px;position: relative;left: -28px">`+time+`</td>
                              </tr>
                          </table>
                      </td>`;
                    $("#synthesizefist").append($li);
                }
                if (i>1){
                    var $tr=`<tr>
                        <td width="5%" height="40" align="center" valign="middle" class=""><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                        <td width="95%" class=""><a style="font-family: 微软雅黑;font-size: 14px" href="/a/logins/content?id=`+data[i].id+`" class="alist17hei">`+content+`</a></td>
                      </tr>`;
                    $("#synthesizeend").append($tr);
                }


            }

        });


    }



    /*
     * 营销动态
     *   零售版块
     * */
    function retailInfo(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $("#retailfist").empty();
            $("#retailend").empty();
            for (let i = 0; i <(data.length>=8?8:data.length ); i++) {

                let time=data[i].createDate.substring(5,10);
                var content=(data[i].title).replace( /^\s/, '');
                content=content.substring(0,content.length>=27?27:content.length)+""+(content.length>=27?"...":"");
                var tcon = ((data[i].title).replace( /^\s/, '')).substring(0,data[i].title.length>=66?66:data[i].title.length)+""+(data[i].title.length>=66?"...":"");
             //   alert(tcon)
                if (i==0){
                    var $li=`<tr style="">
                        <td height="60" class="font_back_20h" style="padding-left:5px; padding-right:5px; line-height:35px;
                        font-family: 微软雅黑;font-size: 16px"><a href="/a/logins/content?id=`+data[i].id+`">`+tcon+`</a></td>
                          </tr>
                          <tr>
                            <td height="30" align="left" valign="middle" class="font_hui_14h inden5px" style="font-size: 10px;font-weight: normal;font-family: 微软雅黑;">`+time+`</td>
                      </tr>`;
                    $("#retailfist").append($li);
                }else {
                    var $tr=`<tr>
                        <td width="5%" height="40" align="center" valign="middle" class=""><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                        <td width="95%" class=""><a style="font-family: 微软雅黑;color: rgb(33, 33, 33);font-size: 14px;width: 96%;
                        display:block; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;
                        " href="/a/logins/content?id=`+data[i].id+`" class="alist17hei">`+content+`</a></td>
                      </tr>`;
                    $("#retailend").append($tr);
                }
            }
        });
    }

   /*经营管理
     内控合规*/
    function nkhgInfo(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $("#nkhgfist").empty();
            $("#nkhgend").empty();
            for (let i = 0; i <(data.length>=8?8:data.length ); i++) {
                let time=data[i].createDate.substring(5,10);
                var content=(data[i].title).replace( /^\s/, '');
                content=content.substring(0,content.length>=27?27:content.length)+""+(content.length>=27?"...":"");
                var tcon = ((data[i].title).replace( /^\s/, '')).substring(0,data[i].title.length>=66?66:data[i].title.length)+""+(data[i].title.length>=66?"...":"");
                if (i==1){
                    var $li=`<tr>
                        <td height="60" class="font_back_20h" style="padding-left:5px; padding-right:5px; 
                        line-height:35px;font-family: 微软雅黑;font-size: 16px"><a href="/a/logins/content?id=`+data[i].id+`">`+tcon+`</a></td>
                          </tr>
                          <tr>
                            <td height="30" align="left" valign="middle" class="font_hui_14h inden5px" style="font-family: 微软雅黑;font-weight: normal">`+time+`</td>
                      </tr>`;
                    $("#nkhgfist").append($li);
                }else {
                    var $tr=`<tr>
                        <td width="5%" height="40" align="center" valign="middle" class=""><img src="../../../static/common/image/ico_dian.jpg" width="4" height="4" /></td>
                        <td width="95%" class="">
                        <a style="font-family: 微软雅黑;color: rgb(33, 33, 33);font-size: 14px;width: 96%;" href="/a/logins/content?id=`+data[i].id+`" class="alist17hei">`+content+`</a></td>
                      </tr>`;
                    $("#nkhgend").append($tr);
                }
            }
        });
    }




   /*风采墙*/
    function mienwallInfo() {
        $.getJSON("/a/public/userInfo",function (data) {
            $("#mienwall").empty();
            for (let i = 0; i <data.length; i++) {
                var path=httpPath+data[i].photo;
                var $tr=`<div class="swiper-slide swiper-slide2">
                   <img src="`+path+`" width="100%" height="80%" />
                   <div class="box_fc_zs font_bai_16h" style="width: 100%">`+data[i].name+`</div>
                </div>`;
                $("#mienwall").append($tr);
            }
        });
    }


    /*总行要闻 滚动*/
    function zhyw(name,nameparent) {
        $.getJSON("/a/public/dataJson?name="+name+"&nameparent="+nameparent,function (data) {
            $(".font_inner").empty();
            for (let i = 0; i <data.length; i++) {
                var length=data[i].title.length>=25?25:data[i].title.length;
                var cont=data[i].title.substring(0,length);
                let time=data[i].createDate.substring(0,10);
                var $li=` <li style="height: 40px;">
                          <a style="font-size: 16px;font-weight: bold " href="/a/logins/content?id=`+data[i].id+` " name="`+time+`">`+cont+`</a>
                         </li>`;
                var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
                var week = "星期"+weekArray[new Date(time).getDay()];
                $(".float_leftday").html(`<span style="font-weight: bolder;font-size: 14px">`+week+`</span></br><span style="color: rgb(90, 90, 90);font-size: 13px">`+time+`</span>`);
                $(".font_inner").append($li);

            }
            //1文字轮播(2-5页中间)开始
            $(".font_inner li:eq(0)").clone(true).appendTo($(".font_inner"));//克隆第一个放到最后(实现无缝滚动)
            // var liHeight = $(".swiper_wrap").height();//一个li的高度
            var liHeight = 40;//一个li的高度
            //获取li的总高度再减去一个li的高度(再减一个Li是因为克隆了多出了一个Li的高度)
            var totalHeight = (($(".font_inner li").length *  $(".font_inner li").eq(0).height()) -liHeight);
            $(".font_inner").height(totalHeight);//给ul赋值高度
            var index = 0;
            var autoTimer = 0;//全局变量目的实现左右点击同步
            var clickEndFlag = true; //设置每张走完才能再点击

            function tab(){
                $(".font_inner").stop().animate({
                    top: -index * liHeight
                },400,function(){
                    clickEndFlag = true;//图片走完才会true
                    if(index == $(".font_inner li").length -1) {
                        $(".font_inner").css({top:0});
                        index = 0;
                    }
                })
            }

            function next() {
                index++;
                if(index > $(".font_inner li").length - 1) {//判断index为最后一个Li时index为0
                    index = 0;
                }
                var a=$(".font_inner li:eq("+index+")").html();
                var time=a.substring(a.indexOf("name=\"")+6,a.lastIndexOf("\">"));
                var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
                var week = "星期"+weekArray[new Date(time).getDay()];
                $(".float_leftday").html(`<span style="font-weight: bolder;font-size: 14px">`+week+`</span></br><span style="color: rgb(90, 90, 90);font-size: 13px">`+time+`</span>`);
                $(".font_inner").append($li);
                tab();
            }
            function prev() {
                index--;
                if(index < 0) {
                    index = $(".font_inner li").size() - 2;//因为index的0 == 第一个Li，减二是因为一开始就克隆了一个LI在尾部也就是多出了一个Li，减二也就是_index = Li的长度减二
                    $(".font_inner").css("top",- ($(".font_inner li").size() -1) * liHeight);//当_index为-1时执行这条，也就是走到li的最后一个
                }
                var a=$(".font_inner li:eq("+index+")").html();
                var time=a.substring(a.indexOf("name=\"")+6,a.lastIndexOf("\">"));
                var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
                var week = "星期"+weekArray[new Date(time).getDay()];
                // $(".float_leftday").html(week+"</br>"+time);
                $(".float_leftday").html(`<span style="font-weight: bolder;font-size: 14px">`+week+`</span></br><span style="color: rgb(90, 90, 90);font-size: 13px">`+time+`</span>`);
                $(".font_inner").append($li);
                tab();
            }
            //切换到下一张
            $(".swiper_wrap .gt").on("click",function() {
                if(clickEndFlag) {
                    next();
                    clickEndFlag = false;
                }
            });
            //切换到上一张
            $(".swiper_wrap .lt").on("click",function() {
                if(clickEndFlag) {
                    prev();
                    clickEndFlag = false;
                }
            });
            //自动轮播
            autoTimer = setInterval(next,3000);
            $(".font_inner a").hover(function(){
                clearInterval(autoTimer);
            },function() {
                autoTimer = setInterval(next,3000);
            })

            //鼠标放到左右方向时关闭定时器
            $(".swiper_wrap .lt,.swiper_wrap .gt").hover(function(){
                clearInterval(autoTimer);
            },function(){
                autoTimer = setInterval(next,3000);
            })
            //1文字轮播(2-5页中间)结束



        });
    }


    /**
     * 搜索转跳
     */
     $("#searchtitle").click(function () {
           var valu = $("#searchValue").val().trim();
           if (valu!=null&&valu!=""){
               location.href="/a/logins/list?title="+valu;
               $("#searchValue").val("");
           }else {
               layer.msg("请输入搜索内容");
           }
     });


    /**
     * 排行榜 typesection     typeactive   typeheat
     */
    //初始查询本月
    statistics(1,1,1); //部门
    statistics(2,1,2);  //活跃
    statistics(3,1,3);  //热度
    //点击切换
    $(".typesection").click(function () {
        var index=$(this).index();
        $(".typesection").each(function (i,e) {
            if (i==index){
                $(e).css({"background-color":"#9C78C3","color":"#ffffff"});
                statistics(1,(i+1),1);
            } else {
                $(this).css({"background-color":"#F4F4F4","color":"#9C78C3"});

            }
        })
    });
    $(".typeactive").click(function () {
        var index=$(this).index();
        $(".typeactive").each(function (i,e) {
            if (i==index){
                $(e).css({"background-color":"#9C78C3","color":"#ffffff"});
                statistics(2,(i+1),2);
            } else {
                $(this).css({"background-color":"#F4F4F4","color":"#9C78C3"});

            }
        })
    });
    $(".typeheat").click(function () {
        var index=$(this).index();
        $(".typeheat").each(function (i,e) {
            if (i==index){
                $(e).css({"background-color":"#9C78C3","color":"#ffffff"});
                statistics(3,(i+1),3);  //热度
            } else {
                $(this).css({"background-color":"#F4F4F4","color":"#9C78C3"});

            }
        })
    });

    function statistics(type,month,icot) {
        $.getJSON("/a/public/statistics?type="+type+"&month="+month,function (data) {

            if (icot==1) {
                $("#statistics1").empty();
            }
            if (icot==2) {
                $("#statistics2").empty();
            }
            if (icot==3) {
                $("#statistics3").empty();
            }
            for (let i = 0; i <(data.length<=5?data.length:5) ; i++) {
                var imgpath,imgtwo;
                //奖牌图标
                if (i==0){
                    imgpath="<img src=\"../../../static/common/image/ico_one.jpg\" width=\"23\" height=\"23\" />";
                } else if (i==1){
                    imgpath="<img src=\"../../../static/common/image/ico_two.jpg\" width=\"23\" height=\"23\" />";
                } else if(i==2){
                    imgpath="<img src=\"../../../static/common/image/ico_thr.jpg\" width=\"23\" height=\"23\" />";
                }else {
                    imgpath=i+1;
                }
                //图标
                if (icot==1){
                    imgtwo="<img src=\"../../../static/common/image/ico_10.jpg\" width=\"17\" height=\"22\" />";
                }
                if (icot==2){
                    imgtwo="<img src=\"../../../static/common/image/ico_09.jpg\" width=\"17\" height=\"22\" />";
                }
                if (icot==3){
                    imgtwo="<img src=\"../../../static/common/image/ico_08.jpg\" width=\"17\" height=\"22\" />";
                }

                var $tr=`<tr>
                    <td width="12%" height="40" align="center" valign="middle">`+imgpath+`</td>
                    <td width="63%" class="font_back_14h inden5px" style="font-family: 微软雅黑">`+data[i].officename+`</td>
                    <td width="10%" align="center" valign="middle">`+imgtwo+`</td>
                    <td width="15%" align="center" valign="middle">`+data[i].sum+`</td>
                  </tr>`;
                
               // alert(data[i].sum+"==="+data[i].officename);
                if (icot==1) {
                    $("#statistics1").append($tr);
                }
                if (icot==2) {
                    $("#statistics2").append($tr);
                }
                if (icot==3) {
                    $("#statistics3").append($tr);
                }


            }



        });

    }

    //将转义字符转为普通html字符
    function escape2Html(str) {
        var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
    }

});



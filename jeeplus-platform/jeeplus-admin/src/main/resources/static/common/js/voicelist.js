$(function () {

    $.getJSON("/a/public/getNavMenu",function (data) {
        for (let i = 0; i < data.length ; i++) {
            let $div='<div><a class="redirects1_div_a" style="text-decoration: none" href="/a/logins/grouplist?id='+data[i].id+'">'+data[i].name+'</a> </div>';
            $("#redirects1_div").append($div);
        }
    });
    $.getJSON("/a/public/getNavMenuInstitutions",function (data) {
        for (let i = 0; i < data.length ; i++) {
            var divs = '';
            divs += '<div>' +
                '<a class="redirects2_div_a" style="text-decoration:none" href="/a/logins/programadatalist?id='+data[i].id+'&name='+encodeURI(data[i].name)+'">'+data[i].name+'</a>' +
                '</div>';
            $("#redirects2_div").append(divs);
        }
    });

    //首页li导航栏转跳
    $(".redirects").click(function () {
        let i=$(this).index();
      //  alert(i)
        if (i==0){ //首页
            location.href="/a/logins/indexNew"
        }
        if (i==1){ //分行动态
            location.href=encodeURI("/a/logins/programadatalist?id=4f8d74dc230b49e5b9aba3d7386f85bf&name=分行动态")
        }
        if (i==4){ //规章制度
            location.href=encodeURI("/a/logins/programadatalist?id=06cdcb5fdbfa4aa7beb9718f2817b9f4&name=规章制度")
        }
        if (i==5){ //下载中心
            location.href="/a/logins/down"
        }
        if (i==6){ //通讯录/communicationbook/communicationbookmanagement/bankCommunicationBook
            location.href="/a/logins/addressbook"
        }
    });

    $(".redirects1").hover(function () {
        $("#redirects1_div").show();
    },function () {
        $("#redirects1_div").hide();

    })
    $(".redirects2").hover(function () {
        $("#redirects2_div").show();
    },function () {
        $("#redirects2_div").hide();

    })
    $(".stParim").focus(function () {
        if ($(this).val()=="欢迎访问光大内网！" || $(this).val()=="请输入要搜索的标题内容！"){
            $(this).val("")
        }
    });
    $(".stParim").blur(function () {
        if ($(this).val()==""){
            $(this).val("欢迎访问光大内网！");
        }
    });
    $(".selectParim").click(function () {
        let selectParim=$(".stParim").val();
        if (selectParim=="欢迎访问光大内网" || selectParim==""){
            $(".stParim").val("请输入要搜索的标题内容！");
        } else {
            location.href="/a/logins/list?title="+encodeURI(selectParim.trim());
        }
    });
    $("#unameuser").click(function () {
        if ($(this).html()!="登录") {
            layer.open({
                type: 2,
                shadeClose:true,
                id: 'update_user',
                title: ['修改用户信息', 'font-size:18px;'],
                area: ['1000px', '650px'],
                content: '/a/sys/user/info'
            });
        }
    });

    $(".loginOutSystem").click(function () {
        location.href="/a/logout";
    });

    var integrityurl = window.location.search;/*获取请求路径*/
    var reg = RegExp(/type/);
    if (reg.test(integrityurl)) {
        list(integrityurl.substring((integrityurl.indexOf('=')+1)));
    }else {
        list("all");
    }

    // function list(type){
    //     layui.use('laypage', function(){
    //         var laypage = layui.laypage;
    //         $.getJSON("/a/public/voicedataweb?type="+type+"&pageNo=1",function (data) {
    //             // console.log(data);
    //             $("#tableList").empty();
    //             // console.log("=============");
    //             // console.log(data);
    //             for (let i = 0; i <data.rows.length ; i++) {
    //                 let time=data.rows[i].createDate.substring(0,10);
    //                 var $tr='<tr>'
    //                     +'<td width="5%" align="center" valign="middle" class="list_td_height newslist"><span>匿名：<span/></td>'
    //                     +'<td width="80%" align="left" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+data.rows[i].leaveMessage+'</a></td>'
    //                     +'<td width="15%" align="center" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+time+'</a></td>'
    //                   +'</tr>';
    //                 $("#tableList").append($tr);
    //             }
    //
    //             //执行一个laypage实例
    //             laypage.render({
    //                 elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
    //                 limit:data.pageSize, //页大小
    //                 curr:data.pageNo, //当前页
    //                 theme:"#9932CD"
    //                 ,count: data.count //数据总数
    //                 ,jump: function(obj, first){
    //                     // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
    //                     // console.log(obj.limit); //得到每页显示的条数
    //                     $.getJSON("/a/public/voicedataweb?type="+type+"&pageNo="+obj.curr,function (data) {
    //                         $("#tableList").empty();
    //                         for (let i = 0; i <data.rows.length ; i++) {
    //                             let time=data.rows[i].createDate.substring(0,16);
    //                             //href="/a/logins/content/?id='13ccb91987c24ab5afb9a9e7df9469a3'"
    //                             if("否"==data.rows[i].isShow){
    //                                 var $tr='<tr>'
    //                                 +'<td width="5%" align="center" valign="middle" class="list_td_height newslist"><span style="color: #6A6AFF;font-style: italic">'+data.rows[i].leaveMessageName+'：<span/></td>'
    //                                 +'<td width="80%" align="left" valign="middle" class="newslist"><a href="/a/logins/voicecontent/?id='+data.rows[i].id+'" >'+data.rows[i].leaveMessage+'</a></td>'
    //                                 +'<td width="15%" align="center" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+time+'</a></td>'
    //                               +'</tr>';
    //                             }else {
    //                                 var $tr='<tr>'
    //                                 +'<td width="5%" align="center" valign="middle" class="list_td_height newslist"><span style="color: #6A6AFF;font-style: italic">匿名：<span/></td>'
    //                                 +'<td width="80%" align="left" valign="middle" class="newslist"><a href="/a/logins/voicecontent/?id='+data.rows[i].id+'" >'+data.rows[i].leaveMessage+'</a></td>'
    //                                 +'<td width="15%" align="center" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+time+'</a></td>'
    //                               +'</tr>';
    //                             }
    //
    //                             $("#tableList").append($tr);
    //                         }
    //
    //                     });
    //                 }
    //             });
    //
    //         });
    //     });
    // }

    var p = /[a-z]/i;//是否含有字母

    function list(type){
        layui.use('laypage', function(){
            var laypage = layui.laypage;
            $.getJSON("/a/public/voicedataweb?type="+type+"&pageNo=1",function (data) {
                // console.log(data);
                $("#tableList").empty();
                var divs = '';
                var b;
                for (let i = 0; i <data.rows.length ; i++) {
                    b = p.test(data.rows[i].replyNameB);
                    let time=data.rows[i].createDate.substring(0,10);
                    if(data.rows[i].isReply==='是'){//已回复
                        if(data.rows[i].isShow==='是'){
                            divs += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                '                        <!--留言-->' +
                                '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">匿名</div>' +
                                '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                '                        </div>' +
                                '                        <!--回复-->' +
                                '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                '                            <div style="margin-left: 1%">' +
                                '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />';
                                if(b){
                                    divs +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyDeptName+'</div>';
                                }else {
                                    divs +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyNameB+'</div>';
                                }
                                    divs +=
                                '                                <div style="clear: both"></div>' +
                                '                            </div>' +
                                '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">回复 :'+data.rows[i].replyB+'</span></div>' +
                                '                            <div style="margin-left: 5%;" id="hftime">'+data.rows[i].replyTimeB+'</div>' +
                                '                        </div>' +
                                '                    </div>' +
                                '                </div>'
                        }else {
                            divs += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                '                        <!--留言-->' +
                                '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">'+data.rows[i].leaveMessageName+'</div>' +
                                '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                '                        </div>' +
                                '                        <!--回复-->' +
                                '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                '                            <div style="margin-left: 1%">' +
                                '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />';
                                if(b){
                                    divs +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyDeptName+'</div>';
                                }else {
                                    divs +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyNameB+'</div>';
                                }
                                    divs +=
                                '                                <div style="clear: both"></div>' +
                                '                            </div>' +
                                '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">回复 :'+data.rows[i].replyB+'</span></div>' +
                                '                            <div style="margin-left: 5%;" id="hftime">'+data.rows[i].replyTimeB+'</div>' +
                                '                        </div>' +
                                '                    </div>' +
                                '                </div>'
                        }

                    }else {//未回复
                        if(data.rows[i].isShow==='是'){
                            divs += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                '                        <!--留言-->' +
                                '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">匿名</div>' +
                                '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                '                        </div>' +
                                '                        <!--回复-->' +
                                '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                '                            <div style="margin-left: 1%">' +
                                '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                                <div style="color: red;font-weight: bold;font-style: italic;" id="hfname"></div>' +
                                '                                <div style="clear: both"></div>' +
                                '                            </div>' +
                                '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">未回复</span></div>' +
                                '                            <div style="margin-left: 5%;" id="hftime"></div>' +
                                '                        </div>' +
                                '                    </div>' +
                                '                </div>'
                        }else {
                            divs += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                '                        <!--留言-->' +
                                '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">'+data.rows[i].leaveMessageName+'</div>' +
                                '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                '                        </div>' +
                                '                        <!--回复-->' +
                                '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                '                            <div style="margin-left: 1%">' +
                                '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                '                                <div style="color: red;font-weight: bold;font-style: italic;" id="hfname"></div>' +
                                '                                <div style="clear: both"></div>' +
                                '                            </div>' +
                                '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">未回复</span></div>' +
                                '                            <div style="margin-left: 5%;" id="hftime"></div>' +
                                '                        </div>' +
                                '                    </div>' +
                                '                </div>'
                        }

                    }

                }
                $("#voicelist").empty();
                $("#voicelist").append(divs);
                //执行一个laypage实例
                laypage.render({
                    elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
                    limit:data.pageSize, //页大小
                    curr:data.pageNo, //当前页
                    theme:"#9932CD"
                    ,count: data.total //数据总数
                    ,jump: function(obj, first){
                        // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        // console.log(obj.limit); //得到每页显示的条数
                        $.getJSON("/a/public/voicedataweb?type="+type+"&pageNo="+obj.curr,function (data) {
                            console.log(data);
                            $("#voicelist").empty();
                            var ds = '';
                            var b;
                            for (let i = 0; i <data.rows.length ; i++) {
                                let time=data.rows[i].createDate.substring(0,16);
                                b = p.test(data.rows[i].replyNameB);
                                if(data.rows[i].isReply==='是'){//已回复
                                    if(data.rows[i].isShow==='是'){//匿名
                                        ds += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                            '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                            '                        <!--留言-->' +
                                            '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                            '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">匿名</div>' +
                                            '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                            '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                            '                        </div>' +
                                            '                        <!--回复-->' +
                                            '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                            '                            <div style="margin-left: 1%">' +
                                            '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />';
                                            if(b){
                                                ds +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyDeptName+'</div>';
                                            }else {
                                                ds +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyNameB+'</div>';
                                            }
                                            ds +=
                                            '                                <div style="clear: both"></div>' +
                                            '                            </div>' +
                                            '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">回复 :'+data.rows[i].replyB+'</span></div>' +
                                            '                            <div style="margin-left: 5%;" id="hftime">'+data.rows[i].replyTimeB+'</div>' +
                                            '                        </div>' +
                                            '                    </div>' +
                                            '                </div>'
                                    }else {//没有匿名
                                        ds += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                            '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                            '                        <!--留言-->' +
                                            '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                            '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">'+data.rows[i].leaveMessageName+'</div>' +
                                            '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                            '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                            '                        </div>' +
                                            '                        <!--回复-->' +
                                            '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                            '                            <div style="margin-left: 1%">' +
                                            '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />';
                                            if(b){
                                                ds +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyDeptName+'</div>';
                                            }else {
                                                ds +='<div style="color: red;font-weight: bold;font-style: italic;" id="hfname">'+data.rows[i].replyNameB+'</div>';
                                            }
                                                ds +=
                                            '                                <div style="clear: both"></div>' +
                                            '                            </div>' +
                                            '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">回复 :'+data.rows[i].replyB+'</span></div>' +
                                            '                            <div style="margin-left: 5%;" id="hftime">'+data.rows[i].replyTimeB+'</div>' +
                                            '                        </div>' +
                                            '                    </div>' +
                                            '                </div>'
                                    }

                                }else {
                                    if(data.rows[i].isShow==='是'){
                                        ds += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                            '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                            '                        <!--留言-->' +
                                            '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                            '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">匿名</div>' +
                                            '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                            '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                            '                        </div>' +
                                            '                        <!--回复-->' +
                                            '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                            '                            <div style="margin-left: 1%">' +
                                            '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                                <div style="color: red;font-weight: bold;font-style: italic;" id="hfname"></div>' +
                                            '                                <div style="clear: both"></div>' +
                                            '                            </div>' +
                                            '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">未回复</span></div>' +
                                            '                            <div style="margin-left: 5%;" id="hftime"></div>' +
                                            '                        </div>' +
                                            '                    </div>' +
                                            '                </div>'
                                    }else {
                                        ds += ' <div class="layout_content_info1" style="padding-top: 1%;">' +
                                            '                    <div class="content_text" style="background-color: aliceblue;padding-right: 1%">　' +
                                            '                        <!--留言-->' +
                                            '                        <div class="font_back_16h_ygxs"style="margin-left: 1%;border-bottom-style: solid;border-bottom-color: #9C78C2;padding-bottom: 5%;">' +
                                            '                            <img id="img1" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                 style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                            <div style="color: red;font-weight: bold;font-style: italic;" id="lyname">'+data.rows[i].leaveMessageName+'</div>' +
                                            '                            <div style="margin-left: 4%" id="lycontent">'+data.rows[i].leaveMessage+'</div>' +
                                            '                            <div id="lytime" style="margin-left: 4%">'+time+' 回复部门：'+data.rows[i].replyDeptName+'</div>' +
                                            '                        </div>' +
                                            '                        <!--回复-->' +
                                            '                        <div id="hf" class="font_back_16h_ygxs"style="margin-top: 2%;">' +
                                            '                            <div style="margin-left: 1%">' +
                                            '                                <img id="img2" class="float_left photo" src="../../../static/common/image2/ico_loogin.png"' +
                                            '                                     style="width: 30px;height: 30px;border-radius: 50%;position: relative;margin-right: 1%;" />' +
                                            '                                <div style="color: red;font-weight: bold;font-style: italic;" id="hfname"></div>' +
                                            '                                <div style="clear: both"></div>' +
                                            '                            </div>' +
                                            '                            <div style="margin-left: 5%;" id="hfcontent"><span id="hfbm" style="color: #00BFFF"> </span><span style="color: #00BFFF">未回复</span></div>' +
                                            '                            <div style="margin-left: 5%;" id="hftime"></div>' +
                                            '                        </div>' +
                                            '                    </div>' +
                                            '                </div>'
                                    }

                                }


                            }
                            $("#voicelist").append(ds);
                        });
                    }
                });

            });
        });
    }

    /*未输入值是警告*/
    $("#search").click(function () {
        var searchValue=$(".inputtext2").val();
        if (searchValue!=null&&searchValue!=""){
            list(searchValue);
        }else {
            layer.msg("请输入搜索内容!!!!");
        }
    });

});
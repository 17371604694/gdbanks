$(function () {

    //首页li导航栏转跳
    $(".redirects").click(function () {
        var i=$(this).index();
      //  alert(i)
        if (i==0){ //首页
            location.href="/a/logins/indexNew"
        }
        if (i==1){ //分行动态
            location.href="/a/logins/programadatalist?id=4f8d74dc230b49e5b9aba3d7386f85bf&name="+encodeURI("分行动态")
        }
        if (i==4){ //
            location.href="/a/logins/programadatalist?id=06cdcb5fdbfa4aa7beb9718f2817b9f4&name="+encodeURI("规章制度")
        }
        if (i==5){ //下载中心
            location.href="/a/logins/down"
        }
        if (i==6){ //通讯录
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
        var selectParim=$(".stParim").val();
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
    var reg = RegExp(/id/);
    if (reg.test(integrityurl)) {
        list(integrityurl.substring((integrityurl.indexOf('=')+1)));
    }else {
        list("all");
    }

    function list(id){
        layui.use('laypage', function(){
            var laypage = layui.laypage;
            $.getJSON("/a/public/getDistributeListById?id="+id+"&pageNo=1",function (data) {
                $("#tableList").empty();
                // console.log("=============");
                // console.log(data);
                var trs = '';
                for (var i = 0; i <data.list.length ; i++) {
                    var time=data.list[i].createDate.substring(0,10);
                    var createtiem = data.list[i].createDate.substring(0,10);
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
                    trs += '<tr>';
                    if(now===createtiem){
                        trs += '<td width="5%" align="center" valign="middle" class="list_td_height newslist"><img src="../../../static/common/image/ico_new.jpg" width="39" height="17" /></td>';
                    }else {
                        trs += '<td width="5%" align="center" valign="middle" class="list_td_height newslist"><img src="" width="39" height="17" /></td>';
                    }
                    trs +=
                        ' <td width="80%" align="left" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+data.list[i].title.substring(0,40)+'</a></td>' +
                        ' <td width="15%" align="center" valign="middle" class="newslist"><a href="#" class="adeptlist14">'+time+'</a></td>' +
                        ' </tr>;'
                }
                $("#tableList").append(trs);

                //执行一个laypage实例
                laypage.render({
                    elem: 'test1', //注意，这里的 test1 是 ID，不用加 # 号
                    limit:data.pageSize, //页大小
                    curr:data.pageNo, //当前页
                    theme:"#9932CD"
                    ,count: data.count //数据总数
                    ,jump: function(obj, first){
                        // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        // console.log(obj.limit); //得到每页显示的条数
                        $.getJSON("/a/public/getDistributeListById?id="+id+"&pageNo="+obj.curr,function (data) {
                            $("#tableList").empty();
                            var trs = '';
                            for (var i = 0; i <data.list.length ; i++) {
                                var time=data.list[i].createDate.substring(0,16);
                                var createtiem = data.list[i].createDate.substring(0,10);
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
                                trs += '<tr>';
                                if(now===createtiem){
                                    trs += '<td width="5%" align="center" valign="middle" class="list_td_height newslist"><img src="../../../static/common/image/ico_new.jpg" width="39" height="17" /></td>';
                                }else {
                                    trs += '<td width="5%" align="center" valign="middle" class="list_td_height newslist"><img src="" width="39" height="17" /></td>';
                                }
                                trs +=
                                    ' <td width="80%" align="left" valign="middle" class="newslist"><a target="_blank" href="/a/logins/content/?id='+data.list[i].id+'" class="adeptlist14">'+data.list[i].title.substring(0,52)+'</a></td>' +
                                    ' <td width="15%" align="center" valign="middle" class="newslist"><a target="_blank" href="#" class="adeptlist14">'+time+'</a></td>' +
                                    ' </tr>;'

                            }
                            $("#tableList").append(trs);
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
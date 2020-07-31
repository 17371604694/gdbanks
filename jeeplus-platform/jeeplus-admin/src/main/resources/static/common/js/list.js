$(function () {

    $.getJSON("/a/public/getNavMenu",function (data) {
        for (var i = 0; i < data.length ; i++) {
            var $div=$("<div><a class=\"redirects1_div_a\" style=\"text-decoration: none\" href=\"/a/logins/grouplist?id="+data[i].id+"\">"+data[i].name+"</a> </div>");
            $("#redirects1_div").append($div);
        }
    });

    $.getJSON("/a/public/getNavMenuInstitutions",function (data) {
        for (var i = 0; i < data.length ; i++) {
            var divs = '';
            divs += '<div>' +
                '<a class="redirects2_div_a" style="text-decoration:none" href="/a/logins/programadatalist?id='+data[i].id+'&name='+encodeURI(data[i].name)+'">'+data[i].name+'</a>' +
                '</div>';
            $("#redirects2_div").append(divs);
        }
    });

    //首页li导航栏转跳
    $(".redirects").click(function () {
        var i=$(this).index();
        if (i==0){ //首页
            location.href="/a/logins/indexNew"
        }
        if (i==1){ //
            location.href="/a/logins/programadatalist?id=4f8d74dc230b49e5b9aba3d7386f85bf&name="+encodeURI("分行动态")
        }
        if (i==4){ //
            location.href="/a/logins/programadatalist?id=06cdcb5fdbfa4aa7beb9718f2817b9f4&name="+encodeURI("规章制度")
        }
        if (i==5){ //
            location.href="/a/logins/down"
        }
        if (i==6){ //
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
        if ($(this).val()=="欢迎访问光大内网" || $(this).val()=="请输入要搜索的标题内容"){
            $(this).val("")
        }
    });

    $(".stParim").blur(function () {
        if ($(this).val()==""){
            $(this).val("欢迎访问光大内网");
        }
    });

    $(".selectParim").click(function () {
        var selectParim=$(".stParim").val();
        if (selectParim=="欢迎访问光大内网" || selectParim==""){
            $(".stParim").val("请输入要搜索的标题内容");
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

    var integrityurl = window.location.search;
    var reg = RegExp(/title/);
    if (reg.test(integrityurl)) {
        list(integrityurl.substring((integrityurl.indexOf('=')+1)));
    }else {
        list("all");
    }

    function list(title){
       layui.use('laypage', function(){
           var laypage = layui.laypage;
           $.getJSON("/a/public/getDistributeList?title="+title+"&pageNo=1",function (data) {
                $("#tableList").empty();
                //  console.log(data);
                for (var i = 0; i <data.list.length ; i++) {
                    var time=data.list[i].createDate.substring(0,10);
                    var $tr=$("<tr>\n" +
                        "                        <td width=\"5%\" align=\"center\" valign=\"middle\" class=\"list_td_height newslist\"></td>\n" +
                        "                        <td width=\"80%\" align=\"left\" valign=\"middle\" class=\"newslist\"><a target=\"_blank\" href=\"#\" class=\"adeptlist14\">"+data.list[i].title+"</a></td>\n" +
                        "                        <td width=\"15%\" align=\"center\" valign=\"middle\" class=\"newslist\"><a target=\"_blank\" href=\"#\" class=\"adeptlist14\">"+time+"</a></td>\n" +
                        "                      </tr>");
                    $("#tableList").append($tr);
                }

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
                        $.getJSON("/a/public/getDistributeList?title="+title+"&pageNo="+obj.curr,function (data) {
                            $("#tableList").empty();
                            for (var i = 0; i <data.list.length ; i++) {
                                var time=data.list[i].createDate.substring(0,16);
                                //href="/a/logins/content/?id='13ccb91987c24ab5afb9a9e7df9469a3'"
                                var $tr=$("<tr>\n" +
                                    "<td width=\"5%\" align=\"center\" valign=\"middle\" class=\"list_td_height newslist\"></td>\n" +
                                    "<td width=\"80%\" align=\"left\" valign=\"middle\" class=\"newslist\"><a target=\"_blank\" href=\"/a/logins/content/?id="+data.list[i].id+"\" >"+data.list[i].title+"</a></td>\n" +
                                    "<td width=\"15%\" align=\"center\" valign=\"middle\" class=\"newslist\"><a target=\"_blank\" href=\"#\" class=\"adeptlist14\">"+time+"</a></td>\n" +
                                    "</tr>");
                                $("#tableList").append($tr);
                            }

                        });
                    }
                });

            });
        });
    }


    /*收索集合列表页面，查询按钮*/
    $("#search").click(function () {
        var searchValue=encodeURI($(".inputtext2").val());
        if (searchValue!=null&&searchValue!=""){
           list(searchValue);
        }else {
            layer.msg("请输入搜索内容!!!!");
        }
    });

});
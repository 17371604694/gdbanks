(function() {
    /*该js弃用*/
    gd = {
        /*
       * type:附件分类
       * pageNo：当前页
       * name:文件名
       * beginCreateDate:查询开始时间
       * endCreateDate:查询结束时间
       * */
        load:function (type, currentPage, rname,beginCreateDate,endCreateDate) {
            var totalPage = 0;
            $.post("/a/public/download",
                {useType: type, pageNo: currentPage, name: rname
                }, function (pageBean) {
                    //设置分页栏
                    if(pageBean.count>0){
                        totalPage = pageBean.count%pageBean.pageSize==0?pageBean.count/pageBean.pageSize : Math.floor(pageBean.count/pageBean.pageSize)+1;
                    }
                    $("#totalCount").html(pageBean.count);//总条数
                    $("#totalPage").html(totalPage);//总页数

                    var lis = "";
                    var firstPage = '<li><a href="javascript:load('+type+',1,\''+rname+'\')">首页</a></li>';//实现首页刷新 并且显示按钮
                    var befornum = pageBean.pageNo - 1;
                    if (befornum <= 0) {
                        befornum = 1;
                    }
                    var beforPage = '<li class="threeword"><a href="javascript:load(' + type + ',' + befornum + ',\''+rname+'\')">上一页</a></li>';//设置上一页
                    lis += firstPage;
                    lis += beforPage;
                    /*实现每个页面只显示10个页码*/
                    var begin;//开始显示的页面
                    var end;//结束显示的页码
                    if (totalPage < 10) {
                        begin = 1;
                        end = totalPage;
                    } else {
                        begin = pageBean.currentPage - 5;
                        end = pageBean.currentPage + 4;
                        if (begin < 1) {
                            begin = 1;
                            end = 10;
                        }
                        if (end > totalPage) {
                            begin = totalPage - 9;
                            end = totalPage;
                        }
                    }
                    for (var i = begin; i <= end; i++) {
                        /*当点击页码超链接的时候此处调用load函数 实现页面的刷新
                        实现分页按钮显示*/
                        var li = "";
                        if (pageBean.pageNo == i) {
                            li = '<li class="curPage" onclick="javascript:load(' + type + ',' + i + ',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';
                        } else {
                            li = '<li onclick="javascript:load(' + type + ',' + i + ',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';
                        }
                        lis += li;
                    }

                    var nextnum = pageBean.pageNo + 1;
                    if (nextnum >= totalPage) {
                        nextnum = totalPage;
                    }
                    var nextPage = '<li class="threeword"><a href="javascript:load(' + type + ',' + nextnum + ',\''+rname+'\')">下一页</a></li>';
                    var lastPage = '<li class="threeword"><a href="javascript:load(' + type + ',' + totalPage + ',\''+rname+'\');">末页</a></li>';
                    lis += nextPage;
                    lis += lastPage;
                    $("#pageNum").empty();
                    $("#pageNum").append(lis);

                    //list数据展示
                    var trs = '<tr class="font_back_16h">' +
                        '<td width="30%" height="60" align="center" bgcolor="#E0E0E0">附件名称</td>' +
                        '<td width="20%" align="center" bgcolor="#E0E0E0">部门</td>' +
                        '<td width="20%" align="center" bgcolor="#E0E0E0">附件类型 </td>' +
                        '<td width="20%" align="center" bgcolor="#E0E0E0">日期</td>' +
                        '<td width="10%" align="center" bgcolor="#E0E0E0">操作</td>' +
                        '</tr>';
                    for (var i = 0; i < pageBean.list.length; i++) {
                        if (i % 2 == 0) {
                            trs += '<tr>' +
                                '<td height="60" align="left" valign="middle" class="font_back_14h" style="text-indent:5px;">' + pageBean.list[i].name + '</td>' +
                                '<td align="center" valign="middle" class="font_back_14h">' + pageBean.list[i].officename + '</td>' +
                                '<td align="center" valign="middle" class="font_back_14h">' + pageBean.list[i].type + '</td>' +
                                '<td align="center" valign="middle" class="font_back_14h">' + pageBean.list[i].createDate + '</td>' +
                                '<td align="center" valign="middle"><a href="' + pageBean.list[i].accessorys + '"><img src="../../../static/common/imagess/ico_down01.jpg" width="30" height="30" /></a></td>' +
                                '</tr>';
                        } else {
                            trs += '<tr>' +
                                '<td height="60" align="left" valign="middle" bgcolor="#F5F5F5" class="font_back_14h" style="text-indent:5px;">' + pageBean.list[i].name + '</td>' +
                                '<td align="center" valign="middle" bgcolor="#F5F5F5" class="font_back_14h">' + pageBean.list[i].officename + '</td>' +
                                '<td align="center" valign="middle" bgcolor="#F5F5F5" class="font_back_14h">' + pageBean.list[i].type + '</td>' +
                                '<td align="center" valign="middle" bgcolor="#F5F5F5" class="font_back_14h">' + pageBean.list[i].createDate + '</td>' +
                                '<td align="center" valign="middle" bgcolor="#F5F5F5"><a href="' + pageBean.list[i].accessorys + '"><img src="../../../static/common/imagess/ico_down02.jpg" width="30" height="30" /></a></td>' +
                                '</tr>';
                        }
                    }
                    $("#downloadtable").empty();
                    $("#downloadtable").append(trs);
                    $("#usetype").val(pageBean.list[0].useType);
                    window.scrollTo(0, 0);//刷新自动跳转到顶部
                });
    }
    };
})(jQuery);
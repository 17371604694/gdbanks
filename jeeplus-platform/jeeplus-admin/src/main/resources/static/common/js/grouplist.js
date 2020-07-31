(function() {
    /*该js弃用*/
    group = {
        /**
         * 加载导航栏菜单
         */
        loadgrouplist:function (list) {
            alert(list.length);
            var divlist = "";
            for(var i = 0;i<list.length;i++){
                divlist +=
                    '<div class="layout_group_list">'+
                    '<div class="float_left layout_conent_list right_border">'+
                    '<div class="" style="height:66px;">'+
                    '<div class="font_back_24h float_left margin_top20"><span class="dept_title">部门介绍</span></div>'+
                    '<div class="folt_right margin_top20"><span class="box_more">more+</span></div>'+
                    '</div>'+
                    '<div>'+
                    '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
                    '<tr>'+
                    '<td width="10%" align="center" valign="middle" class="list_td_height"><img'+
                    'src="../../../static/common/imagess/ico_new.jpg" width="39" height="17"/></td>'+
                    '<td align="left" valign="middle"><a href="#" class="adeptlist14">光大集团与华晨集团签署战略合作协议</a></td>'+
                    '<td width="15%" align="center" valign="middle"><a href="#" class="adeptlist14">11-09</a></td>'+
                    '</tr>'+

                    '</table>'+
                    '</div>'+
                    '</div>'+
                    '<div class="float_left layout_conent_list" style="margin-left:10px;">'+
                    '<div class="" style="height:66px;">'+
                    '<div class="font_back_24h float_left margin_top20"><span class="dept_title">市场讯息</span></div>'+
                    '<div class="folt_right margin_top20"><span class="box_more">more+</span></div>'+
                    '</div>'+
                    '<div>'+
                    '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
                    '<tr>'+
                    '<td width="10%" align="center" valign="middle" class="list_td_height"><img'+
                    'src="../../../static/common/imagess/ico_new.jpg" width="39" height="17"/></td>'+
                    '<td align="left" valign="middle"><a href="#" class="adeptlist14">光大集团与华晨集团签署战略合作协议</a></td>'+
                    '<td width="15%" align="center" valign="middle"><a href="#" class="adeptlist14">11-09</a></td>'+
                    '</tr>'+
                    '</table>'+
                    '</div>'+
                    '</div>'+
                    '</div>'
            }
        }
    }

});
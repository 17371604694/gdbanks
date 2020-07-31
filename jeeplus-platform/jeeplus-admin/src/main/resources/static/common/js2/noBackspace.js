window.onload=function () {
    /* 防止backspace键后退网页 */
    document.onkeydown = function(event) {
        if (event.keyCode == 8) {// backspace的keycode=8
            var type = document.activeElement.type;// 获取焦点类型
            if (type == "text" || type == "textarea" || type == "password"
                || type == "select") {// 判断焦点类型，无法输入的类型一律屏蔽
                if (document.activeElement.readOnly == false)// 如果不是只读，则执行本次backspace按键
                    return true;
            }
            event.keyCode = 0;// 将本次按键设为0（即无按键）
            event.returnValue = false;
            return false;
        }
    };

}
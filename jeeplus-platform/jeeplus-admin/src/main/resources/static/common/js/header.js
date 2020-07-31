$(function() {
    $.get("/a/logins/header",function (data) {
        $("#header").html(data);
    });
});
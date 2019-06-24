var signed = false;

var preloaderHide = function () {
    $('.preloader-wrapper').css('display', 'none');
}

var preloaderShow = function () {
    $('.preloader-wrapper').css('display', 'block');
}

//
var onSignIn = function (accountInfo) {

    $("a[href='#!home:out']").text('Signout (' + accountInfo.name + ')');
    $("a[href='#!home:out']").css('display', 'block');
    
    $("a[href='#!feedback']").css('display', 'block');

    $("a[href='#!signin']").css('display', 'none');
    $("a[href='#!signup']").css('display', 'none');
    
    $(".cart").css('display', 'block');
    signed = true;
}

var onSignOut = function () {

    $("a[href='#!home:out']").text('');
    $("a[href='#!home:out']").css('display', 'none');
    
    $("a[href='#!feedback']").css('display', 'none');

    $("a[href='#!signin']").css('display', 'block');
    $("a[href='#!signup']").css('display', 'block');
    
    $(".cart").css('display', 'none');
    signed = false;

    $("#admin").html('');
}

$(document).ready(function () {

    $('.sidenav').sidenav();
    $('.modal').modal();
    $.get("/api/user/check")
        .done(function (resp) {

            if (resp !== undefined && resp.status === 'success') {

                if (resp.data !== undefined) {
                    onSignIn(resp.data);
                }
            }
        })
        .fail(function (xhr, status, error) {
            alert("Fatal error: " + error);
        });
});
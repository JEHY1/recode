$(function() {

    // search 열기
    $("#iconBox > ul > li:nth-child(1) > a").on("click", function() {
        $("#search").css("display","block");
        $("#search input[type=search]").focus();
    });
    // search 닫기
    $("#search > a").on("click", function() {
        $("#search").css("display","none");
    });

    // nav 열기
    $("#iconBox > ul > li:nth-child(3) > a").on("click", function() {
        $("nav").css("display","block");
    });
    // nav 닫기
    $("nav > a").on("click", function() {
        $("nav").css("display","none");
    });

    // notice 닫기
    $("#notice > div > a").on("click", function() {
        $("#notice").css("display","none");
    })

    // scroll - header 색
    $(window).on("scroll", function() {
        if($(window).scrollTop() == 0) {
            $("header").css({"background-color":"#ffffff00", "border-bottom":"none"});
        }
        else {
            $("header").css({"background-color":"#ffffffee", "border-bottom":"1px solid #ccc"});

        }
    });

});
$(function() {

    // search 열기
    $("#iconBox > ul > li:nth-child(1) > a").on("click", function() {
        $("#search").css("display","block");
    });
    $("#search > a").on("click", function() {
        $("#search").css("display","none");
    });

    // nav 열기
    $("#iconBox > ul > li:nth-child(3) > a").on("click", function() {
        $("nav").css("display","block");
    });
    $("nav > a").on("click", function() {
        $("nav").css("display","none");
    });

    // scroll - header 색
    $(window).on("scroll", function() {
        if($(window).scrollTop() == 0) {
            $("header").css("background-color","#ffffff00");
        }
        else {
            $("header").css("background-color","#ffffffee");

        }
    });

    // hover - header 색
    $("header").hover(
        function(){
            if($(window).scrollTop() == 0) {
                $("header").css("background-color","#ffffffee");
            }
        },
        function(){
            if($(window).scrollTop() == 0) {
                $("header").css("background-color","#ffffff00");
            }
        });
});
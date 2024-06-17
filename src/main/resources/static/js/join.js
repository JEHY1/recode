$(function() {
    // 전체 동의하기 눌렀을 때, 다 체크
    $("#all").on("click", function() {
        if($("#all").prop("checked") == true) {
            $(".must, .select").prop("checked", true);
        }
        else {
            $(".must, .select").prop("checked", false);
        }
    });
    // 개별 다 체크되면 전체 동의하기 체크
    $(".must, .select").on("click", function() {
        if($("#must1").prop("checked") == true && $("#must2").prop("checked") == true 
        && $("#select1").prop("checked") == true && $("#select2").prop("checked") == true) {
            $("#all").prop("checked", true);
        }
        else {
            $("#all").prop("checked", false);
        }  
    });
});
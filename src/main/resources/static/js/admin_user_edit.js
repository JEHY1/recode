$(function() {
    // 체크박스 하나만 체크되게 하기
    $("input[type=checkbox]").on("change", function() {
        $("input[type=checkbox]").not(this).prop("checked", false);
    });
});
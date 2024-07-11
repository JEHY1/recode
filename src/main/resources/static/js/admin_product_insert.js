$(function() {

    // 상품 대표사진 미리보기
    $("#mainImg").on("change", function(event) {

        var file = event.target.files[0];
        if(file != null) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#mainImgShow").attr("src", e.target.result);
            }
            reader.readAsDataURL(file);
        }
        else {
            $("#mainImgShow").attr("src", "");
        }

    });
});



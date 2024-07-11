$(function() {
    // 전체 체크 눌렀을 때, 다 체크
    $("#all").on("click", function() {
        if($("#all").prop("checked") == true) {
            $(".check").prop("checked", true);
        }
        else {
            $(".check").prop("checked", false);
        }
    });
    // 개별 다 체크되면 전체 체크
    $(".check").on("click", function() {
        if($(".check:checked").length == $(".check").length) {
            $("#all").prop("checked", true);
        }
        else {
            $("#all").prop("checked", false);
        }
    });

    // 체크박스 체크 후 삭제 버튼 눌렀을 때
    $("#selectBox a:nth-of-type(1)").on("click", function() {

        let checkArr = [];
        $(".check:checked").each(function() {
           checkArr.push($(this).val());
        });

        $.ajax({
            url:'/admin/qna/delete',
            type : "post",
            data : {
                "qnAIds" : checkArr
            },
            dataType : 'text',
            success : function(data) {
                if (data == "delete") {
                    // 현재 페이지의 "page"파라메타 변수를 가져옴
                    let page = new URLSearchParams(window.location.search).get("page");
                    // 마지막 페이지 일 때
                    if($("#pageBox > a:last-child").prop("href") == "javascript:void(0)") {
                        location.href = "/admin/qna/index?isDel=1&page=" + ($(".check:checked").length == $(".check").length ? page - 1 : page); // 싹다 체크 됐을 때 전 페이지로
                    }
                    else {
                        location.href = "/admin/qna/index?isDel=1&page=" + page;
                    }
                }
            }
        });
    });
    // 페이지 갱신없이 주소 URL 변경
    history.pushState(null, null, "/admin/qna/index?page=" +  new URLSearchParams(window.location.search).get("page"));


});



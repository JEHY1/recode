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
            url:'/admin/notice/delete',
            type : "post",
            data : {
                "noticeIds" : checkArr
            },
            dataType : 'text',
            success : function(data) {
                if (data == "delete") {
                    // 현재 페이지의 파라메타 변수를 가져옴
                    let url = new URLSearchParams(window.location.search);
                    // 마지막 페이지 일 때
                    if($("#pageBox > a:last-child").prop("href") == "javascript:void(0)") {
                        location.href = "/admin/notice/index?isDel=1&page=" + ($(".check:checked").length == $(".check").length ? url.get("page") - 1 : url.get("page")) + "&category=" + url.get("category") + "&searchKeyword=" + url.get("searchKeyword"); // 싹다 체크 됐을 때 전 페이지로
                    }
                    else {
                        location.href = "/admin/notice/index?isDel=1&page=" + url.get("page") + "&category=" + url.get("category") + "&searchKeyword=" + url.get("searchKeyword");
                    }
                }
            }
        });
    });

    let url = new URLSearchParams(window.location.search); // 현재 페이지의 파라메타 변수를 가져옴
    // 삭제창 떴을 때 페이지 갱신없이 주소 URL 변경
    if(url.get("isDel") == 1) {
        history.pushState(null, null, "/admin/notice/index?page=" + url.get("page") + "&category=" + url.get("category") + "&searchKeyword=" + url.get("searchKeyword"));
    }
    // 검색한 searchKeyword -> URL 로 보내기
    $("#selectBox > button").on("click", function() {
        location.href = "/admin/notice/index?page=0&category=" + $("#selectBox > select").val() + "&searchKeyword=" + $("#selectBox > input").val();
    });


});



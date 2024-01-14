
window.onload = function () {
    console.log(window.location.pathname)
    if (window.location.pathname == "/views/android") {
        fetchAndroidMemo()o
    } else {
        // 타이틀 엔터키 방지 로직
        document.getElementById('title').addEventListener('keydown', function (event) {
            switch (event.keyCode) {
                case 13:
                    // 엔터 키 방지.
                    event.preventDefault();
                    break;
            }
        })

        // 취소 버튼 클릭시.
        document.getElementById('btn-cancel').onclick = function () {
            // alert("취소 취소");
            history.back();
        }
    }

}

/**
 * fetch Android Memo
 */
function fetchAndroidMemo() {
    $.ajax({
        type: "GET",
        url: "/api/v1/memo/aos",
        contentType: 'application/json',
        dataType: "JSON",
        success: function (json) {
            let data = json.data;
            data.payload.forEach(element => {
                // initView
                const divRoot = $('<div class="card-normal-bg"></div>');

                let card = $('<div id="card-contents" class="card-normal-contents"></div>')
                // DataBinding
                card.html(element.title + "<p>" + element.contents + "</p>")
                divRoot.append(card);
                $('#memo_list').append(divRoot);
            })
        },
        error: function (xhr, status, error) {
            console.log(status, 'Error ' + error);
        }
    })
}

function addMemo() {
    let contents = document.getElementById('contents').value;
    contents = contents.replace(/(?:\r\n|\r|\n)/g, '<br />');
    document.getElementById('contents').value = contents;
    return true;
}

function postAndroidMemo() {
    console.log("postAndroidMemo")

}
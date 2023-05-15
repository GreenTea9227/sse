console.clear();

function Chat__submitWriteMessageForm(form) {
    // console.log("submit 실행됨");

    // 입력란에 좌우에 존재하는 공백제거
    form.authorName.value = form.authorName.value.trim();

    if (form.authorName.value.length == 0) {
        form.authorName.focus();
        alert("작성자를 입력해주세요");

        return;
    }

    form.content.value = form.content.value.trim();

    if (form.content.value.length == 0) {
        form.content.focus();

        return;
    }

    // POST http://localhost:8020/chat/writeMessage

    form.content.value = ""; // 입력한 비우기
}

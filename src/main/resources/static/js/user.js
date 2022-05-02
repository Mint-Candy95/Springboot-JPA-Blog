let index = {
    init : function(){
        $("#btn-save").on("click",()=>{ //function(){}, ()=>{} this.를 바인딩 하기위해서 사용
            this.save();
        } );
    },

    save:function(){
        //alert('user의 save함수 호출됨')
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        };

        //console.log(data); //자바 스크립트 오브젝트

        //ajax호출시 default가 비동기 호출
        //ajax통신 이용해 3개의 데이터를 json으로 변경하여 insert요청
        //ajax가 통신 성공후 서버가 json은 리턴해주면 자바 오브젝트로 변환
        $.ajax({
            // 회원가입 수행 요청
            type:"POST",
            url:"/blog/api/user",
            data: JSON.stringify(data), //json문자열, http body 데이터
            contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지
            dataType:"json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 모든것이 STring(생긴게 json이라면) =>javascript 오브젝트로 변경
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href="/blog";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();
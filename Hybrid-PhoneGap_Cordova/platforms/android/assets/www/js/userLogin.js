function verifyLogin(){

    var username = $("#username").val();
    var password = $("#password").val();
    if(username && password){
        var user = {};
        user.username = username;
        user.password = password;
        $.ajax(
            {
                url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/login",
                //url:"http://localhost:8080/springmvc/login",
                type: "POST",
                crossDomain: true,
                data: "username="+user.username+"&password="+user.password,
                error: onError,
                success: onSuccess,
                dataType: "json",
                contentType: "application/x-www-form-urlencoded"

            } );
        alert(password);
    }
}

function onError(e){
    console.log(e);
    alert("error - "+e.toLocaleString());
}
function onSuccess(data){
    if(data.status == 0){
        alert("Username/password not correct!");
    } else {
		    window.sessionStorage.accessToken = data.data.jwt;
        if(data.data.role==='caregiver')
          window.location.href = "caregiverHome.html";
        else {
          window.location.href = "boomerHome.html";
        }
    }
}

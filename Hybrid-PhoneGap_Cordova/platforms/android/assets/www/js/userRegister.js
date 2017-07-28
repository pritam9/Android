var lat;
var lang;
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    navigator.geolocation.getCurrentPosition(onSuccess, onError, { timeout: 30000,enableHighAccuracy: true });
    //alert("Loading Co ordinates");
    function onSuccess(position) {
        lat=position.coords.latitude;
        lang=position.coords.longitude;
        //alert(lat+" : "+lang);
      }
      function onError(error) {
          alert('code: ' + error.code + '\n' +
              'message: ' + error.message + '\n');
      }

    }

function verifyRegister(){

    var username = $("#email").val();
    var password = $("#password").val();
    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var age = $("#age").val();
    var sex = $("#sex").val();
    var streetname = $("#streetname").val();
    var apt = $("#apt").val();
    var city = $("#city").val();
    var state = $("#state").val();
    var role = $("#role").val();
    var zip = $("#zip").val();
    //alert(lat+" -- "+lang);
    if(username && password && firstname && sex && streetname && apt && city && role && zip){
        var user = {};
        user.username = username;
        user.password = password;
        user.firstname = firstname;
        user.lastname = lastname;
        user.age = age;
        user.sex = sex;
        user.streetname = streetname;
        user.apt = apt;
        user.city = city;
        user.state = state;
        user.role = role;
        user.zip = zip;
        alert(lat+" -- "+lang);
        $.ajax(
            {
                url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/register",
                //url:"http://localhost:8080/controller/register",
                type: "POST",
                crossDomain: true,
                data: "username="+user.username+"&password="+user.password+"&email="+user.username
                        +"&firstname="+user.firstname+"&lastname="+user.lastname+"&age="+user.age
                            +"&sex="+user.sex+"&streetname="+user.streetname+"&apartment="+user.apt
                                +"&city="+user.city+"&state="+user.state+"&role="+user.role
                                        +"&zipcode="+user.zip+
                                        "&longitude="+lang+"&latitude="+lat+"&deviceId="+localStorage.getItem('registrationId'),
                success: onSuccess,
                error: function(e){
                    console.log(e);
                    alert("error - "+e);
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded"
            } );
        alert(localStorage.getItem('registrationId'));
    } else {
        $(".error-msg").removeClass("hide");
        $(".error-msg").html("Please enter all the fields");
        alert("Username/Password cannot be blank")
    }


    return false;
}

function onSuccess(data){
    //var obj = JSON.parse(data.data);
    alert(data.status);
    console.log(data);
    if(data.status == 0){
        $(".error-msg").html(data.message);
        $(".error-msg").removeClass("hide");
        alert("Username/password not correct!");
    } else {
        alert("Login correct!");
        window.location.href = "index.html";
    }
}

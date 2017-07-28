var token=window.sessionStorage.accessToken;
var caregiverId="";
var respondToCaregiverId="";
var requestId =0;
$(document).ready(function(){

    token=window.sessionStorage.accessToken;
    //alert(token);
    $.ajax({
        url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/getuser",
        //url:"http://localhost:8080/springmvc/getuser",
        type: "POST",
        crossDomain: true,
        data: "token="+token,
        success: onSuccess,
        error: function(e){
            console.log(e);
            alert("error - "+e);
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded"
    });
});

function onSuccess(data){

    var username=data.data.username;
    var firstname=data.data.firstname;
    //alert(data.status);
    console.log(data);
    if(data.status == 0){
        $(".error-msg").html(data.message);
        $(".error-msg").removeClass("hide");
        alert("Something went wrong!!");
    } else {
        //alert("Entering into homepage!");
    }
    //document.getElementById("username").innerHTML=username;
    //document.getElementById("firstname").innerHTML=firstname;
}

function userLogout(){
    //document.getElementById("logout").style.color = "red";
    sessionStorage.clear();
    window.location.href = "index.html";
}
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    navigator.geolocation.getCurrentPosition(onSuccess, onError, { timeout: 30000,enableHighAccuracy: true });
    function onSuccess(position) {
        var lat=position.coords.latitude;
        var lang=position.coords.longitude;
        //alert(lat+"-"+lang);
        //Google Maps
        var infowindow = new google.maps.InfoWindow();
        var myLatlng = new google.maps.LatLng(lat,lang);
        var mapOptions = {zoom: 15,center: myLatlng}
        var map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
        var boomer = "img/boomer.png";
        var caregiver = "img/caregiver.png";
        $.ajax({
            url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/loc",
            //url:"http://localhost:8080/springmvc/getuser",
            type: "GET",
            crossDomain: true,
            data: "token="+token+"&latitude="+lat+"&longitude="+lang,
            success: function(data){
                //alert(data.data.length);
                for(var i=0;i<data.data.length;i++){
                    var nearUser = data.data[i];
                    //alert(nearUser.username);
                    var marker1;
                    if(nearUser.role=="caregiver"){
                        marker1 = new google.maps.Marker({
                            position: new google.maps.LatLng(nearUser.location.lat, nearUser.location.longi),
                            icon:caregiver,
                            map: map
                        });
                    }else{
                        marker1 = new google.maps.Marker({
                            position: new google.maps.LatLng(nearUser.location.lat, nearUser.location.longi),
                            icon:boomer,
                            map: map
                        });
                    }

                    google.maps.event.addListener(marker1,'click', (function (marker1,role,firstname,lastname,username) {
                        return function () {
                          caregiverId=username;
                            infowindow.setContent("<div class="+"'container-fluid col-md-12'"+"><div class="+"'example'"+">"+
                                "<div class="+"'col-md-12'"+">"+
                                "<a class="+"'navbar-brand'"+" href='#'"+">"+firstname+" "+lastname+"I am "+role+"</a>"+
                                "<div class="+"'row'"+">"+
                                "<div class="+"'col-md-12'"+">"+
                                "<button type="+"'submit'"+" class="+"'btn btn-group-justified btn-info btn-sm'"+" onclick=\"toggler('join_caregiver',\'"+firstname+"\',\'"+lastname+"\')\"\;>Join</button>"+
                            "</div> </div></div> </div>");
                            infowindow.open(map, marker1);
                        }
                    })(marker1, nearUser.role,nearUser.firstname,nearUser.lastname,nearUser.username));
                }
            },
            error: function(e){
                console.log(e);
                alert("error - "+e);
            },
            dataType: "json",
            contentType: "application/x-www-form-urlencoded"
        });

        var marker = new google.maps.Marker({position: myLatlng,map: map, title:'Hi!'});

        //var marker1, i;

    }
    function onError(error) {
        alert('code: ' + error.code + '\n' +
            'message: ' + error.message + '\n');
    }
    google.maps.event.addDomListener(window, 'load', onSuccess);
}

function toggler(divId,firstname,lastname) {

    $("#"+divId).toggle();
    document.getElementById("caregiver_details").innerHTML=firstname+" "+lastname;
}

function sendRequest(){
  var description = $("#requirements").val();

  $.ajax(
      {
          url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/req/init",
          //url:"http://localhost:8080/springmvc/login",
          type: "POST",
          crossDomain: true,
          data: "token="+token+"&caregiver="+caregiverId+"&description="+description+"&requestId=1",
          error: function(e){
              console.log(e);
              alert("error - "+e);
          },
          success: onPostSuccess,
          dataType: "json",
          contentType: "application/x-www-form-urlencoded"

      } );

alert(description+" and "+caregiverId);


      //return false;
}

function onPostSuccess(){
  navigator.notification.alert(
'You are the winner!',  // message
alertDismissed,         // callback
'Game Over',            // title
'Done'                  // buttonName
);
}

function alertResponse(msg,sender,reqId){
  requestId =reqId;
  respondToCaregiverId=sender;
  document.getElementById("caregiver_text").innerHTML=msg;
  alert(sender+ " - "+msg+" - "+reqId);
  $("#respond_caregiver").toggle();
}

function respondToRequest(status){
    var description = $("#respond_text").val();
    if(status==='ACCEPT'){
          $.ajax(
              {
                  url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/req/boomer/confirm.do",
                  //url:"http://localhost:8080/springmvc/login",
                  type: "POST",
                  crossDomain: true,
                  data: "token="+token+"&caregiver="+respondToCaregiverId+"&description="+description+"&requestId="+requestId,
                  error: function(e){
                      console.log(e);
                      alert("error - "+e);
                  },
                  success: onConfirmSuccess1,
                  dataType: "json",
                  contentType: "application/x-www-form-urlencoded"

              } );
              alert(status);
    }else if(status==='REPLY'){
        $.ajax(
          {
              url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/req/init",
              type: "POST",
              crossDomain: true,
              data: "token="+token+"&caregiver="+respondToCaregiverId+"&description="+description+"&requestId="+requestId,
              error: function(e){
                  console.log(e);
                  alert("error - "+e);
              },
              success: onConfirmSuccess,
              dataType: "json",
              contentType: "application/x-www-form-urlencoded"

          } );
          alert(status);
    }else{
        $.ajax(
          {
              url:"http://ec2-35-166-132-223.us-west-2.compute.amazonaws.com/boomer/req/boomer/cancel.do",
              type: "POST",
              crossDomain: true,
              data: "token="+token+"&caregiver="+respondToCaregiverId+"&description="+description+"&requestId="+requestId,
              error: function(e){
                  console.log(e);
                  alert("error - "+e);
              },
              success: onConfirmSuccess,
              dataType: "json",
              contentType: "application/x-www-form-urlencoded"

          } );
          alert(status);
    }
    $("#respond_caregiver").toggle();
    alert(status);
    //return false;
}
function onConfirmSuccess(data){
    alert(data.message);
}
function onConfirmSuccess1(data){
  window.location.href = "directions.html";
}

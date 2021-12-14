var id;
const callApiEditUser=async(userName,userEmailId,userPassword,userMobile,userId)=>{
    await fetch("http://localhost:8080/users",{
        method:"POST",
        headers:{
            'Content-Type': "text/json",
        },
        body: JSON.stringify({
            userName,
            userEmailId,
            userPassword,
            userMobile
        },userId)
    }
    )
.then(response => response.json())
.then(data => {
  console.log('Success:', data);
  if(data.status==200){
    console.log("Next is landing page");
    location.replace("landingpage.html");
  }
})
.catch((error) => {
  console.error('Error:', error);
});
}
const getUser = (id) => {
    return fetch("http://localhost:8080/user/"+id,{
      method : 'GET'
    }).then((response) => {
      return response.json();
    }).catch(err => {
      console.log(err);
    })
  }
window.onload=function(){
    var userid=sessionStorage.getItem("currentLoginId");
    var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    id=uid.toString(CryptoJS.enc.Utf8);
        getUser(id).then((data) => {
            let user=data.data;
            document.getElementById("name").value=user.userName;
            document.getElementById("mail").value=user.userEmailId;
            //document.getElementById("pass1").value=user.userPassword;
            //document.getElementById("pass2").value=user.userEmailId;
            document.getElementById("mobile").value=user.userMobile;
        });
    document.getElementById("clicked").onclick=function(){
        
        
        var userName=document.getElementById("name").value;
        var userEmailId=document.getElementById("mail").value;
        //var userPassword=document.getElementById("pass1").value;
        //var pass2=document.getElementById("pass2").value;
        var userMobile=document.getElementById("mobile").value;
        if(userPassword!=pass2){
            alert("Re-enter Your Password!");
        }
        else{
            callApiEditUser(userName,userEmailId,userPassword,userMobile,id);
        }
    }
}

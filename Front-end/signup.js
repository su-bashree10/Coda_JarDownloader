const callApiSignupUser=async(userName,userEmailId,userPassword,userMobile)=>{
    await fetch("http://localhost:8080/users",{
        method:"POST",
        headers:{
            'Content-Type': "application/json",
        },
        body: JSON.stringify({
            userName,
            userEmailId,
            userPassword,
            userMobile
        })
    }
    )
.then(response => response.json())
.then(data => {
  console.log('Success:', data);
  if(data.status==200){
    console.log("Next is Login page");
    document.getElementById("popup").style.display="block";
    location.replace("login.html");
  }
  else{
    document.getElementById("popup1").innerHTML=data.message;
    document.getElementById("popup1").style.display="block";
  }
})
.catch((error) => {
  console.error('Error:', error);
});
}
window.onload=function(){
    document.getElementById("clicked").onclick=function(){
        var userName=document.getElementById("name").value;
        var userEmailId=document.getElementById("mail").value;
        var userPassword=document.getElementById("pass1").value;
        var pass2=document.getElementById("pass2").value;
        var userMobile=document.getElementById("mobile").value;
        if(userPassword!=pass2){
            alert("Re-enter Your Password!");
        }
        else{
            callApiSignupUser(userName,userEmailId,userPassword,userMobile);
        }
    }
}

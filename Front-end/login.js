
window.onload=function(){
    document.getElementById("donelogin").onclick=function(){
        var userEmailId=document.getElementById("mail").value;
        var userPassword=document.getElementById("pass").value;
        console.log(`${userEmailId} ---- >    ${userPassword}`);
        sessionStorage.setItem("currentLoginmail",userEmailId);
        callApiLoginUser(userEmailId,userPassword);
    }
}
const callApiLoginUser= async(userEmailId,userPassword)=>{
    console.log(`In api call  ${userEmailId} ---- >    ${userPassword}`);
    await fetch("http://localhost:8080/loginUser",{
        method:"POST",
        headers:{
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({userEmailId, userPassword})
    }
    )
  .then(response => response.json())
  .then(data => {
  console.log('Success:', data);
  if(data.status==200){
    var userId=data.data.userId;
    var encrypted = CryptoJS.AES.encrypt(userId.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    console.log(encrypted);
    //console.log(CryptoJS.AES.decrypt(encrypted,"ABCDEFGHIJKLMNOPQRSTUVWYZ"))
    sessionStorage.setItem("currentLoginId",encrypted);
    console.log("Next is Home page");
    //$( "#donelogin" ).click(function() {
      document.getElementById("popup").style.display="block";
      
    location.replace("jarsFile.html")
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
var id;
var userid=sessionStorage.getItem("currentLoginId");
var uid=CryptoJS.AES.decrypt(userid,"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
id=uid.toString(CryptoJS.enc.Utf8); 
function addaccount(){
    var acc=document.getElementById("account").value;
    addBankAccount(id,acc);
}
window.onload=function(){
    var userid=sessionStorage.getItem("currentLoginId");
    var uid=CryptoJS.AES.decrypt(userid,"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    id=uid.toString(CryptoJS.enc.Utf8); 
    getUser(id).then((data) => {
        let user=data.data;
        document.getElementById("account").value=user.accountNumber;
    })
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
const addBankAccount=async(id,acc)=>{
    await fetch("http://localhost:8080/user/update/"+id,{
        method:"POST",
        headers:{
            'Content-Type': "application/json",
        },
        body: JSON.stringify({
            "accountNumber":acc
        })
    })
    .then(response => response.json())
.then(data => {
  console.log('Success:', data);
  if(data.status==200){
    console.log("Next is Login page");
    //alert("Bank Account Details Updated!");
    document.getElementById("popup").style.display="block";
    //location.replace("profile.html");
  }
  else{
      //alert(data.message);
      document.getElementById("popup1").innerHTML="Couldn't Update Bank Account!";
      document.getElementById("popup1").style.display="block";
  }
})
.catch((error) => {
  console.error('Error:', error);
});
}
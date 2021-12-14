window.onload=function(){
    var userid=sessionStorage.getItem("currentLoginId");
    if(userid===null||userid===""){
        alert("Login to View Profile");
        window.location.href="login.html";
    }
    //var userid=sessionStorage.getItem("currentLoginId");
    var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    var id=uid.toString(CryptoJS.enc.Utf8);
    var mail=sessionStorage.getItem("currentLoginmail");
    getUser(id).then((data)=>{
        console.log(data);
        document.getElementById("name").value=data.data.userName;
        document.getElementById("mail").value=mail;
        document.getElementById("account").value=data.data.accountNumber;
        document.getElementById("username").innerHTML="Hello "+data.data.userName+"!";
    })
}
const getUser=(id)=>{
    return fetch("http://localhost:8080/user/"+id,{
        method:'GET'
    }).then((response)=>{
        return response.json();
    }).catch(err=>{
        console.log(err);
    })

}
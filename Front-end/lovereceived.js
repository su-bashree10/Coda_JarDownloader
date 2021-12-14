var userid=sessionStorage.getItem("currentLoginId");
var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
var id=uid.toString(CryptoJS.enc.Utf8);
window.onload=function(){
    if(userid===null||userid===""){
        alert("Login to View Profile");
        window.location.href="login.html";
    }
    else{
        getAllSupports(id).then((data)=>{
            console.log(data);
            console.log(data.data);
            displaySupport(data.data);
        })

    }

}
function displaySupport(data){
    let html=``;
    data.forEach((d,i)=>{
        console.log(i+d.senderName+d.senderEmail+d.jarName+d.amount);
        html+=`<tr><th scope="row">${i+1}</th>
        <td>${d.senderName}</td>
        <td>${d.senderEmail}</td>
        <td>${d.jarName}</td>
        <td>${d.amount}</td>
        </tr>`;
    });
    console.log(html);
    $("#tabledata").append(html);
}
const getAllSupports=(id)=>{
    return fetch("http://localhost:8080//user/support/"+id,{
        method:'GET'
    }).then((response)=>{
        return response.json();
    }).catch(err=>{
        console.log(err);
    })
}
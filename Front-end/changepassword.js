window.onload=function(){
    var userid=sessionStorage.getItem("currentLoginId");
    var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    var id=uid.toString(CryptoJS.enc.Utf8);
        getUser(id).then((data) => {
            let user=data.data;
            document.getElementById("mobile").value=user.userMobile;
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
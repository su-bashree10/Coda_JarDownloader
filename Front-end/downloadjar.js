var searchbar;
var data=[];
const getAllJars = () => {
  return fetch("http://localhost:8080/getAllJars",{
    method : 'GET'
  }).then((response) => {
    return response.json();
  }).catch(err => {
    console.log(err);
  })
  }
  const incrementDownload = (id) => {
  return fetch("http://localhost:8080/jar/"+id,{
    method : 'POST'
  }).then((response) => {
    return response.json();
  }).catch(err => {
    console.log(err);
  })
  }
window.onload = function(){
  searchbar = document.getElementById("searchbar");
    getAllJars().then(data => {
        var elem = data.data;
        displayData(elem);
    })
    
}
$(document).ready(function() {
searchbar.addEventListener("keyup",(e)=>{
    // $("#jar").remove();
    var searchElement = e.target.value;
    console.log(searchElement);
    find(searchElement);
    //  display(data);
})

})

function find(element){
   
  if(element !== ""){
   var xhr = new XMLHttpRequest();
   xhr.onreadystatechange = function(){
       if (this.readyState == 4 && this.status == 200) {
           data = JSON.parse(this.responseText);
           // data.push(jsonResponse);

           console.log(data);
           console.log(typeof data);
           displayData(data)
       //    return data;
         }
   }
   xhr.open("GET", "http://localhost:8080/jar/search?q="+element, true);
   xhr.send();
  }else{
       getAllJars().then(data => {

           displayData(data.data);
       })
  }
}

function displayData(data){
  console.log(data);
      let html = ``;
          data.forEach((jar, i) => {
            
            html+=`
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                  <h5 class="card-title">${jar.jarName}</h5>
                  <h6 class="card-subtitle mb-2 text-muted">Version: ${jar.jarFileVersion}</h6>
                  
                  <p class="card-text">${jar.jarFileDescription}</p>
                  <h6 class="card-subtitle mb-2 text-muted">Author: ${data[i].user.userName}</h6>
                  <button type="button" id="download" class="btn btn-primary" style="background-color: #4B8DF0;" onclick="downloadjar('${jar.jarFileId}','${jar.jarFileDownloadUrl}')">Downloads (${jar.noOfDownloads})</button>
                  <button type="button" id="pay" class="btn btn-primary" style="background-color: #4B8DF0;" onclick="dopayment('${jar.jarFileId}','${data[i].user.userId}')">Support</button>
                </div>
              </div>`;
        });
        html+=`<script>
                function downloadjar(id,url){
                  console.log(id);
                  console.log(url);
                  location.href=url; 
                  incrementDownload(id);
                }
                function dopayment(jarid,receiverid){
                  console.log("hello");
                  var id=sessionStorage.getItem("currentLoginId");
                  if(id===null||id===""){
                    alert("Login to Support!");
                    window.location.href="login.html";
                  }
                  else{
                    var uid = CryptoJS.AES.decrypt(id.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    var senderid=uid.toString(CryptoJS.enc.Utf8);
                    //var amount = document.getElementById("amount").value;
                    var orderId =  Math.floor(Date.now() / 1000).toString(36);
                    //params = getparams();
                    //reciverId =  unescape(params["reciverId"]);
                    //jarId = unescape(params["jarId"]);
                    window.location.href="http://localhost:8080/payment/"+senderid+"/"+jarid+"/"+orderId+"/"+receiverid;
                    console.log("http://localhost:8080/payment/"+senderid+"/"+jarid+"/"+orderId+"/"+receiverid);
                    //api(amount,orderId)
                    //11 - receiver jar uploader
                    //1 - pay user id
                    //7-jar id 
                  }
                }
               </script>`;
        $("#row").empty();
        $("#row").append(html);
}

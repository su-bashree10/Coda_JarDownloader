var pdfurl;
var excelurl;
const incrementDownload = (id) => {
  return fetch("http://localhost:8080/jar/"+id,{
    method : 'POST'
  }).then((response) => {
    return response.json();
  }).catch(err => {
    console.log(err);
  })
  }
window.onload=function(){

  getAllJars().then((data) => {

      let jars = data.data;
      console.log(data.data);
      let html = ``;
          jars.forEach((jar, i) => {
            html+=`<div class="card" style="width: 18rem;">
            <div class="card-body">
              <h5 class="card-title">${jar.jarName}</h5>
              <h6 class="card-subtitle mb-2 text-muted">Version: ${jar.jarFileVersion}</h6>
              
              <p class="card-text">${jar.jarFileDescription}</p>
              
              <button type="button" id="download" style="background-color:#4B8DF0;" class="btn btn-primary" onclick="downloadjar('${jar.jarFileId}','${jar.jarFileDownloadUrl}')">Downloads (${jar.noOfDownloads})</button>
              <button type="button" id="delete" class="btn btn-primary" style="background-color: #4B8DF0;" onclick="deleteJar('${jar.jarFileId}')">Delete</button>

            </div>
          </div>`;
        });
        let html1=`<script>
                  function downloadjar(id,url){
                  console.log(id);
                  console.log(url);
                  location.href=url; 
                  incrementDownload(id);
                }
                function deleteJar(id){
                  console.log(id);
                  deleteJarById(id).then(data=>{
                    console.log(data);
                    if(data.status==200){
                      document.getElementById("popup").style.display="block";
                    }
                    else{
                      document.getElementById("popup1").innerHTML="Couldn't Delete File, Please try again!";
                      document.getElementById("popup1").style.display="block";
                    }
                  })
                }
                </script>`;
        $("#row").append(html);
  
        $('body').append(html1);
//       if((i+1)%4==0)
//         html+=`
//           </div>
//       </div>
//       <div class="container">
//           <div class="row">`;
    });
    //var id=sessionStorage.getItem("currentLoginId");
    pdfDownload().then((data)=>{
      console.log(data);
      console.log(data.url);
      pdfurl=data.url
      //document.getElementById("generatepdf").onClick="location.href='${data.url}'";
      //$('#generatepdf').click(function(){window.location.href = $(this).data(data.url);});
    });
    excelDownload().then((data)=>{
      console.log(data);
      console.log(data.url);
      excelurl=data.url;
    });
   // document.getElementById("generatepdf").onclick="location.href=${";
  
};
const deleteJarById=(id)=>{
  return fetch("http://localhost:8080/jar/delete/"+id,{
    method:'GET'
  }).then((response)=>{
    return response;
  }).catch(err=>{
    console.log(err);
  })
}
function getpdf(){
  location.href=pdfurl;
}
function getexcel(){
  location.href=excelurl;
}
const pdfDownload=()=>{
  var userid=sessionStorage.getItem("currentLoginId");
  var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    var id=uid.toString(CryptoJS.enc.Utf8);
  return fetch("http://localhost:8080/downloadpdf/user/jar/"+id,{
    method:'GET'
  }).then((response)=>{
    return response;
  }).catch(err=>{
    console.log(err);
  })
}
const excelDownload=()=>{
  var userid=sessionStorage.getItem("currentLoginId");
  var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    var id=uid.toString(CryptoJS.enc.Utf8);
  return fetch("http://localhost:8080/downloadexcel/user/jar/"+id,{
    method:'GET'
  }).then((response)=>{
    return response;
  }).catch(err=>{
    console.log(err);
  })
}
const getAllJars = () => {
  var userid=sessionStorage.getItem("currentLoginId");
  var uid = CryptoJS.AES.decrypt(userid.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    var id=uid.toString(CryptoJS.enc.Utf8);
return fetch("http://localhost:8080/user/jar/"+id,{
  method : 'GET'
}).then((response) => {
  return response.json();
}).catch(err => {
  console.log(err);
})
}

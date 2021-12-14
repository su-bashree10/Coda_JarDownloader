window.onload=function(){
    var userid=sessionStorage.getItem("currentLoginId");
    //var id=CryptoJS.AES.decrypt(userid,"ABCDEFGHIJKLMNOPQRSTUVWYZ");
    console.log(userid);
    if(userid===null||userid===""){
        alert("Login to Upload Jar Files");
        window.location.href="login.html";
    }
}
function sendjars(){
        var jarfile = document.querySelector('#jar');
        var jarname=document.getElementById("name").value;
        var jarversion=document.getElementById("version").value;
        var jardescription=document.getElementById("description").value;
        var id=sessionStorage.getItem("currentLoginId");
        var uid = CryptoJS.AES.decrypt(id.toString(),"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        var userid=uid.toString(CryptoJS.enc.Utf8); 
        console.log(jarname);
        console.log(jarversion);
        console.log(jardescription);
        console.log(userid);
        var file=jarfile.files;
        var formdata=new FormData();
        formdata.append("jarFile",file[0]);
        formdata.append("jarFileName",jarname);
        formdata.append("jarFileVersion",jarversion);
        formdata.append("jarFileDescription",jardescription);
        formdata.append("userId",userid);
        console.log(formdata);
        var xhr = new XMLHttpRequest();
        xhr.withCredentials=false;
        xhr.open("POST", "http://localhost:8080/jar/save");
        
        xhr.onload = function() {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if(xhr.status == 200) {
                console.log('Success:', response);
                var userId=response.userId;
                console.log("Next is Home page");
                //alert("File Uploaded Successfully");
                document.getElementById("popup").style.display="block";
                location.replace("jarsFile.html")
            }
            else{
                document.getElementById("popup1").innerHTML=data.message;
                document.getElementById("popup1").style.display="block";
            }
        }
        xhr.send(formdata);
}
/*const callApiUploadJar=async(formdata)=>{
    console.log("gonna call fetch");
    await fetch("http://localhost:8080/jar/save",{
        method:"POST",
       // headers:{
         //   "Content-Type": "multipart/form-data",
           // "boundary":"CUSTOM",
        //},
        body:formdata
    }
    )
    .then(response => response.json())
    .then(data => {
        console.log(data);
        if(data.status==200){
            console.log('Success:', data);
            var userId=data.data.userId;
            //sessionStorage.setItem("currentLoginId",userId);
            console.log("Next is Home page");
            alert("File Uploaded Successfully");
            //location.replace("homepage.html")
        }
    })
    .catch((error) => {
    console.error('Error:', error);
    });
}
*/
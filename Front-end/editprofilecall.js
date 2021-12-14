const callApiSendOtp=async(phone) => {
    console.log("entered");
    await fetch("http://localhost:8080/sendotp",{
        method:"POST",
        headers:{"Content-Type":"text/plain",
    },
        body:"phone:"+phone
    })
    .then(data => {
        console.log(data.status);
        if(data.status==200){
            console.log(data.message);
            console.log("otp sent!");
        }
        return JSON.stringify(data)
    })
    .catch(err => {
        console.log(err);
    });
}
const callApiVerifyOtp=async(phone,otp)=>{
    await fetch("http://localhost:8080/verifyotp",{
        method:"POST",
        
        headers:{"Content-Type":"application/json",},
        body:JSON.stringify({phone,otp})
    },)
    .then(response => response.json())
    .then(data=>{
        console.log(data.status);
        if(data.status==200){
            console.log(data.message);
            console.log("verified successfully")
        }
    })
    .catch(err => {
        console.log(err);
    });
}
function sendOTPsoon(){
    console.log("going to send otp");
    var phone="+91"+document.getElementById("mobile").value;
    console.log(phone);
    callApiSendOtp(phone);
    console.log("be happy");

}
function verifyOTPsoon(){
    console.log("goint to verify otp");
    var phone="+91"+document.getElementById("mobile").value;
    var otp=document.getElementById("otp").value;
    console.log("otp"+otp);
    callApiVerifyOtp(phone,otp);
    return "success";
}
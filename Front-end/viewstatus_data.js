const api_url = "http://localhost:8080/getAllJars";
var data=[];
async function getapi(api_url) {
        const response = await fetch(api_url);
        
        var d = await response.json();
        console.log(d);
        if (response) {
            hideloader();
         }
        show(d);
}
getapi(api_url);
function hideloader() {
    document.getElementById('loading').style.display = 'none';
}
function show(d){
    var i=0;
    for (let r of d.list) {
        data[i]={
            jar_file_id:r.jar_file_id,
            jar_file_name:r.jar_file_name,
            jar_file_description:r.jar_file_description,
            jar_file_download_url:r.jar_file_download_url,
            no_of_downloads:r.no_of_downloads,
            //excel_file_link:r.excel_file_link
        }
    }
}
/*

const data=[
    {
        jar_file_id:1,
        jar_file_name:"Sample jar 1",
        jar_file_description:"hello..i am description.you please read me",
        jar_file_download_url:"i am ur url!",
        no_of_downloads:100,
        excel_file_link:"i am download url"
    }
    ,
    {
        jar_file_id:2,
        jar_file_name:"Sample jar 2",
        jar_file_description:"hello..i am description.you please read me",
        jar_file_download_url:"i am ur url!",
        no_of_downloads:80,
        excel_file_link:"i am download url"
    }
];
*/
export default data;
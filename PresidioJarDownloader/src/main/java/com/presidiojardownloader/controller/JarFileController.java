package com.presidiojardownloader.controller;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.lowagie.text.DocumentException;
import com.presidiojardownloader.entity.JarFiles;
import com.presidiojardownloader.entity.RazorPayEntity;
import com.presidiojardownloader.service.JarFileService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
public class JarFileController {
	
	public static String value;
	@Autowired
	private JarFileService jarFileService;
	
	@PostMapping("/jar/save")
	public ResponseEntity<Object> saveJarFile(@RequestParam("jarFile")MultipartFile file,
								   @RequestParam("jarFileName")String name,
								   @RequestParam("jarFileVersion")String version,
								   @RequestParam("jarFileDescription")String description , 
								   @RequestParam("userId") Long userId){
	
		
		return jarFileService.saveJarFile(name,version,file,description , userId);		
	}
	
	@GetMapping("/getAllJars")
	public ResponseEntity<Object>getAllJars(){
		return jarFileService.getAllJars();
	}
	
	@PostMapping("/jar/{id}")
	public ResponseEntity<Object>updateDownloadCount( @PathVariable("id")  @RequestBody Long id){
		return jarFileService.updateDownloadCount(id);
	}
	
	@GetMapping("/jar/delete/{id}")
	public ResponseEntity<Object>deleteJarById(@PathVariable("id") @RequestBody Long id){
		return jarFileService.deleteJarById(id);
	}
	
	@GetMapping("/user/jar/{id}")
	
	public ResponseEntity<Object>findJarByUserId( @PathVariable("id")  @RequestBody Long id){
		return jarFileService.findJarByUserId(id);
	}
	
	
	@GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        JarFiles dbFile = jarFileService.getFile(fileId);
        //ResponseEntity<Object>res=updateDownloadCount(Long.parseLong(fileId));
        return ResponseEntity.ok()
        		.contentType(MediaType.parseMediaType(dbFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getJarFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getJarFile()));
    }
	
	@GetMapping("/downloadexcel/user/jar/{userid}" )
	public ResponseEntity<Object> downloadExcel(HttpServletResponse response , @PathVariable("userid") Long userId) throws DocumentException, IOException{
		return jarFileService.downloadExcel(response,userId);
	} 
	
	@GetMapping("/downloadpdf/user/jar/{userid}" )
	public ResponseEntity<Object> downloadpdf(HttpServletResponse response , @PathVariable("userid") Long userId) throws DocumentException, IOException{
		return jarFileService.downloadpdf(response,userId);
	}
	
	@GetMapping("/jar/search")
	public List<JarFiles>searchByName(@RequestParam("q")String q){
		return jarFileService.searchByName(q);
	}
	@RequestMapping(value = "/razorpay", method = RequestMethod.GET , produces = "application/json")
	public RazorPayEntity payWithRazorPay() throws Exception {
	
			RazorpayClient razorpay = new RazorpayClient("rzp_test_b1AS2LeRYDxSGS", "yvON0ohG0dufXItAtGeqbBY0");
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", 50000); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");
			Order order = razorpay.Orders.create(orderRequest);
			RazorPayEntity rpe = new RazorPayEntity();
			rpe.setId(order.get("id"));
			System.out.println(order.toJson());
			return rpe;
	}
}

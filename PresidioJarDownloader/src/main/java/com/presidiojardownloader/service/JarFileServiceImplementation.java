package com.presidiojardownloader.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.presidiojardownloader.controller.JarFileController;
import com.presidiojardownloader.downloader.ExcelDownload;
import com.presidiojardownloader.downloader.PdfDownload;
import com.presidiojardownloader.entity.JarFiles;
import com.presidiojardownloader.entity.User;
import com.presidiojardownloader.jar.FileStorageException;
import com.presidiojardownloader.jar.MyFileNotFoundException;
import com.presidiojardownloader.mail.EmailSenderService;
import com.presidiojardownloader.repository.JarFileRepository;
import com.presidiojardownloader.repository.UserRepository;
import com.presidiojardownloader.responseEntity.ResponseHandler;

@Service
public class JarFileServiceImplementation implements JarFileService {
	
		@Autowired
		private JarFileRepository jarFileRepository;
		@Autowired
		private UserRepository userRepository;
		@Autowired
		private EmailSenderService service;
		
		
		

	@Override
	public ResponseEntity<Object> saveJarFile(String fileName, String version, MultipartFile file, String description,Long UserId) {
		User user = userRepository.findByUserId(UserId);
		 //fileName = StringUtils.cleanPath(file.getOriginalFilename());
		 String Name = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			 if(fileName.contains("..")) {
	               throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	         }
			 JarFiles jarFile = new JarFiles(fileName, Name , version, description, file.getContentType(),file.getBytes(), "" ,user );
			 //JarFiles jarFile = new JarFiles(fileName, version, description, file.getContentType(),file.getBytes(), "" ,user );
			 jarFile = jarFileRepository.save(jarFile);
			 JarFileController.value = file.getContentType();
			 String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/downloadFile/")
		                .path(String.valueOf(jarFile.getJarFileId()))
		                .toUriString();
			 jarFile.setJarFileDownloadUrl(fileDownloadUri);
			 
			 jarFile = jarFileRepository.save(jarFile);
			 boolean verify=user.isVerified();
			 if(verify==true && jarFile != null)
			 {
				 String message = "Thanks for uploading "+fileName+" jar File. Keep Supporting Programming Community";
				 try {
				 service.sendSimpleEmail(jarFile.getUser().getUserEmailId(), message ,"From JarDownloader" );
				 } catch(Exception e)
				 {
					 return ResponseHandler.generateResponse("Something Went Wrong ", HttpStatus.BAD_REQUEST, e);
				 }
			 }
			 jarFile.setJarFile(null);
			 jarFile.setUser(null);
			 return ResponseHandler.generateResponse("SuccessFully added", HttpStatus.OK, jarFile);
			 
		}catch(Exception ex)
		{
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
//		 return ResponseHandler.generateResponse("Something Went Wrong", HttpStatus.BAD_REQUEST, null);
		
		
	}

	@Override
	public ResponseEntity<Object> getAllJars() {
		List<JarFiles> jars = jarFileRepository.findAll();
		for(JarFiles jar : jars) {
			jar.setJarFile(null);
			jar.getUser().setUserMobile(null);
			jar.getUser().setUserPassword(null);
		}
		if(jars.size() == 0)
		{
			return ResponseHandler.generateResponse("No Jars Files available", HttpStatus.EXPECTATION_FAILED, null);
		}
		return ResponseHandler.generateResponse("Jars Files available", HttpStatus.OK, jars);
	}

	@Override
	public ResponseEntity<Object> updateDownloadCount(Long id) {
		JarFiles jar = jarFileRepository.findJarFilesByJarFileId(id).get(0);
		if(jar == null) {
			return ResponseHandler.generateResponse("No jar in this id", HttpStatus.BAD_REQUEST, null);
		}
		int count = jar.getNoOfDownloads();
		count = count+1;
		jar.setNoOfDownloads(count);
		JarFiles updatedJar = jarFileRepository.save(jar);
		updatedJar.setJarFile(null);
		updatedJar.setUser(null);
		return ResponseHandler.generateResponse("Jar Download count incremented", HttpStatus.OK, updatedJar);
		
	}

	@Override
	public ResponseEntity<Object> deleteJarById(Long id) {
		try {
			int res = jarFileRepository.deleteJarById(id);
			if(res == 0)
			{
				return ResponseHandler.generateResponse("Cannot delete the Jar Try again Later", HttpStatus.BAD_REQUEST, null);
			}
			return ResponseHandler.generateResponse("Jar Deleted Sucessfully..", HttpStatus.OK, null);
		}catch(Exception e)
		{
			return ResponseHandler.generateResponse("Cannot delete the Jar Try again Later", HttpStatus.BAD_REQUEST, e);
		}
		
	}

	@Override
	public ResponseEntity<Object> findJarByUserId(Long id) {
		List<JarFiles> jars = jarFileRepository.findJarFilesByUserId(id);
		if(jars.size() == 0) {
			return ResponseHandler.generateResponse("No Jars Available", HttpStatus.OK, jars);
		}
		for(JarFiles jar : jars) {
			jar.setJarFile(null);
//			jar.setJarFileDownloadUrl(null);
			jar.setUser(null);
		}
		return  ResponseHandler.generateResponse("Jars Available", HttpStatus.OK, jars);
		
	}

	@Override
	
	
	 public JarFiles getFile(String fileId) {
			Long id = Long.parseLong(fileId);
	        JarFiles jar = jarFileRepository.findJarFilesByJarFileId(id).get(0);
	        if(jar == null) {
	        	throw new MyFileNotFoundException("File not found with id " + fileId);
	        }
	        return jar;
	    }
	
		/*@Override
		public ResponseEntity<Object>saveJarFile(String version ,MultipartFile file, String description,Long UserId){
			User user = userRepository.findByUserId(UserId);
			 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			 
			 try {
				 
				 // Check if it is a valid file
				 
				 if(fileName.contains("..")){throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);}
				 
				 JarFiles jarFile = new JarFiles(fileName, version, description, file.getContentType(),file.getBytes(),file.getContentType(),user);
				 try {
					 JarFiles jar = jarFileRepository.save(jarFile);
					 jar.setUser(null);
					 jar.setJarFile(null);
					 return ResponseHandler.generateResponse(jar.getJarFileName()+"Successfully added", HttpStatus.OK, jar);
				 }catch(Exception e) {
					 return ResponseHandler.generateResponse("File Not Saved Please try again", HttpStatus.BAD_REQUEST, e.getMessage());
				 }
			 }catch(Exception e) {
				 throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
			 }
			
		}*/
		
		@Override
		public ResponseEntity<Object> downloadpdf( HttpServletResponse response,Long userId)  {
	        
	        List<JarFiles> jars = jarFileRepository.findJarFilesByUserId(userId);
	         
	        try {
			PdfDownload pdf = new PdfDownload(jars);
			 pdf.export(response);
			 return ResponseHandler.generateResponse("Downloaded Sucessfully", HttpStatus.OK, null);
			}catch(Exception e) {
				return ResponseHandler.generateResponse("Something Went Wrong...", HttpStatus.BAD_REQUEST, e);
			}

		}
		
		@Override
		public ResponseEntity<Object> downloadExcel(HttpServletResponse response, Long userId) {
			response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	        
	        List<JarFiles>jarFiles = jarFileRepository.findJarFilesByUserId(userId);
	        ExcelDownload e1 = new ExcelDownload(jarFiles);
	        try {
				e1.export(response);
				return ResponseHandler.generateResponse("Downloaded", HttpStatus.OK, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public List<JarFiles> searchByName(String q) {
			List<JarFiles>jars = jarFileRepository.findByJarNameStartsWith(q);
			for(JarFiles jar : jars) {
				jar.setJarFile(null);
//				jar.setJarFileName(null);
				jar.setType(null);
				jar.getUser().setUserMobile(null);
				jar.getUser().setUserPassword(null);
				jar.getUser().setUserEmailId(null);
				
			}
			return jars;
		}



}

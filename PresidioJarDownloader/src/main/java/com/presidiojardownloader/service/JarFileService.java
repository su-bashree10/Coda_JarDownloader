package com.presidiojardownloader.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;
import com.presidiojardownloader.entity.JarFiles;

public interface JarFileService {


	ResponseEntity<Object> saveJarFile(String name, String version, MultipartFile file, String description , Long userId);

	ResponseEntity<Object> getAllJars();

	ResponseEntity<Object> updateDownloadCount(Long id);

	ResponseEntity<Object> deleteJarById(Long id);

	ResponseEntity<Object> findJarByUserId(Long id);

	JarFiles getFile(String fileId);
	
	//ResponseEntity<Object>saveJarFile(String version ,MultipartFile file, String description,Long UserId);

	ResponseEntity<Object> downloadpdf(HttpServletResponse response,Long userId) throws DocumentException, IOException;
	ResponseEntity<Object> downloadExcel(HttpServletResponse response, Long userId);
	
	List<JarFiles> searchByName(String q);

	

	
}
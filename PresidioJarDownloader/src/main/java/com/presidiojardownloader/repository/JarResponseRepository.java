//package com.presidiojardownloader.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//
//import com.presidiojardownloader.jar.JarResponse;
//
//@Repository
//public interface JarResponseRepository extends JpaRepository<JarResponse, Long>{
//	@Query(value = "select jar_file_id,jar_file_description, jar_file_download_url,jar_file_name,jar_file_version, no_of_downloads,user_id from jarfiles",nativeQuery = true)
//	List<JarResponse> getAllJars();
//}

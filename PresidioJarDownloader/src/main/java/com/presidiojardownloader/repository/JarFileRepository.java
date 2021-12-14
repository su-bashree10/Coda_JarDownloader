package com.presidiojardownloader.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.presidiojardownloader.entity.JarFiles;

@Repository
public interface JarFileRepository extends JpaRepository<JarFiles, Long> {
	
//	@Query(value = "select jar_file_id,jar_file_description, jar_file_download_url,jar_file_name,jar_file_version, no_of_downloads,user_id from jarfiles",nativeQuery = true)
	List<JarFiles>findAll();
	List<JarFiles>findJarFilesByJarFileId(Long id);
//	JarFiles findJarFilesByJarFileId(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from jarfiles where jar_file_id = ?1",nativeQuery = true)
	int deleteJarById(Long id);
	
	@Query(value = "select * from jarfiles where user_id = ?1",nativeQuery = true )
	List<JarFiles>findJarFilesByUserId(Long id);
	
	//@Query("select j from JarFiles j where j.jarName Like %:q%")
	List<JarFiles>findByJarNameStartsWith(String q);
}

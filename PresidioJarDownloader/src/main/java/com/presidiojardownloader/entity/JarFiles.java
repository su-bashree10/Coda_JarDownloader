package com.presidiojardownloader.entity;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "jarfiles")
public class JarFiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jarFileId;
	
	@Column(nullable = false)
	private String jarFileName;
	// line added
		@Column(nullable = false)
		private String jarName;
	
	@Column(nullable = false)
	private String jarFileVersion;
	
	@Column(nullable = false)
	private String jarFileDescription;
	
	@Column(nullable = false)
	@Lob
	private byte[] jarFile;
	
	@Column(nullable = false)
	private String jarFileDownloadUrl;
	
	@Column(columnDefinition = "integer default 0")
	private int noOfDownloads;
	
	@Column(nullable = false)
	private String type;
	
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false , cascade = CascadeType.ALL)
	@JoinColumn(name = "userId",referencedColumnName="userId" )
	private User user;
	
	public JarFiles() {}

	public JarFiles(String jarFileName, String jarFileVersion, String jarFileDescription, byte[] jarFile,
			String jarFileDownloadUrl, int noOfDownloads, User user) {
		super();
		this.jarFileName = jarFileName;
		this.jarFileVersion = jarFileVersion;
		this.jarFileDescription = jarFileDescription;
		this.jarFile = jarFile;
		this.jarFileDownloadUrl = jarFileDownloadUrl;
		this.noOfDownloads = noOfDownloads;
		this.user = user;
	}

	public Long getJarFileId() {
		return jarFileId;
	}

	public void setJarFileId(Long jarFileId) {
		this.jarFileId = jarFileId;
	}

	public String getJarFileName() {
		return jarFileName;
	}

	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}

	public String getJarFileVersion() {
		return jarFileVersion;
	}

	public void setJarFileVersion(String jarFileVersion) {
		this.jarFileVersion = jarFileVersion;
	}

	public String getJarFileDescription() {
		return jarFileDescription;
	}

	public void setJarFileDescription(String jarFileDescription) {
		this.jarFileDescription = jarFileDescription;
	}

	public byte[] getJarFile() {
		return jarFile;
	}

	public void setJarFile(byte[] jarFile) {
		this.jarFile = jarFile;
	}

	public String getJarFileDownloadUrl() {
		return jarFileDownloadUrl;
	}

	public void setJarFileDownloadUrl(String jarFileDownloadUrl) {
		this.jarFileDownloadUrl = jarFileDownloadUrl;
	}

	public int getNoOfDownloads() {
		return noOfDownloads;
	}

	public void setNoOfDownloads(int noOfDownloads) {
		this.noOfDownloads = noOfDownloads;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	@Override
	public String toString() {
		return "JarFiles [jarFileId=" + jarFileId + ", jarFileName=" + jarFileName + ", jarFileVersion="
				+ jarFileVersion + ", jarFileDescription=" + jarFileDescription + ", jarFile="
				+ Arrays.toString(jarFile) + ", jarFileDownloadUrl=" + jarFileDownloadUrl + ", noOfDownloads="
				+ noOfDownloads + ", user=" + user + "]";
	}

	public JarFiles(String jarFileName, String jarFileVersion, String jarFileDescription, String type,byte[] jarFile,
			String jarFileDownloadUrl, User user) {
		super();
		this.jarFileName = jarFileName;
		this.jarFileVersion = jarFileVersion;
		this.jarFileDescription = jarFileDescription;
		this.jarFile = jarFile;
		this.type = type;
		this.jarFileDownloadUrl = jarFileDownloadUrl;
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JarFiles(String jarFileName, String jarFileVersion, String jarFileDescription, byte[] jarFile, String type,
			User user) {
		super();
		this.jarFileName = jarFileName;
		this.jarFileVersion = jarFileVersion;
		this.jarFileDescription = jarFileDescription;
		this.jarFile = jarFile;
		this.type = type;
		this.user = user;
	}


	public JarFiles(String jarName , String jarFileName, String jarFileVersion, String jarFileDescription, String type,byte[] jarFile,
			String jarFileDownloadUrl, User user) {
		super();
		this.jarName =jarName;
		this.jarFileName = jarFileName;
		this.jarFileVersion = jarFileVersion;
		this.jarFileDescription = jarFileDescription;
		this.jarFile = jarFile;
		this.type = type;
		this.jarFileDownloadUrl = jarFileDownloadUrl;
		this.user = user;
	}
	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
		
	
	
}

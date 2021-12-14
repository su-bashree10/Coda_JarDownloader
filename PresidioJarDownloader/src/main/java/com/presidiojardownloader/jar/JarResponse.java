package com.presidiojardownloader.jar;

import com.presidiojardownloader.entity.User;

public class JarResponse {
	private Long jarId;
	private String jarFileName;
	private String jarFileVersion;
	private String jarFileDescription;
	private String jarFileDownloadUrl;
	private int noOfDownload;
	private User user;
	
	public JarResponse() {}

	public JarResponse(Long jarId, String jarFileName, String jarFileVersion, String jarFileDescription,
			String jarFileDownloadUrl, int noOfDownload, User user) {
		super();
		this.jarId = jarId;
		this.jarFileName = jarFileName;
		this.jarFileVersion = jarFileVersion;
		this.jarFileDescription = jarFileDescription;
		this.jarFileDownloadUrl = jarFileDownloadUrl;
		this.noOfDownload = noOfDownload;
		this.user = user;
	}

	public Long getJarId() {
		return jarId;
	}

	public void setJarId(Long jarId) {
		this.jarId = jarId;
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

	public String getJarFileDownloadUrl() {
		return jarFileDownloadUrl;
	}

	public void setJarFileDownloadUrl(String jarFileDownloadUrl) {
		this.jarFileDownloadUrl = jarFileDownloadUrl;
	}

	public int getNoOfDownload() {
		return noOfDownload;
	}

	public void setNoOfDownload(int noOfDownload) {
		this.noOfDownload = noOfDownload;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}

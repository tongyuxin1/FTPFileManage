package com.gsp.common.ftpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;



public class FileManage {
	//上传文件
	public String uploadFile(String localFile, String serverSaveRootPath){
		FTPSyncCore ftpClient = new FTPSyncCore();
		File file = new File(localFile);
		if(!file.exists()){
			System.out.println("文件不存在");
			return "";
		}
		try {
			localFile = file.getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if("".equals(localFile)){
			System.out.println("上传路径为空！");
			return "";
		}		
		if(ftpClient.connectServer()){
			try {
				boolean result = ftpClient.uploadFile(localFile, serverSaveRootPath);
				if(!result){
					System.out.println("上传失败！");
					return "上传失败！";
				}
			} catch (IOException e) {
				System.out.println("uploadFile failed!  result = false " );
				ftpClient.closeConnect();	// 关闭连接
			}finally{
				ftpClient.closeConnect();	// 关闭连接
			}	
		}
		
		return "上传成功！";
	}
	//上传文件夹内所有文件
	public String uploadFolder(String localDirectory, String serverSaveRootPath){
		File localDirectoryFile = new File(localDirectory);		
		if (!localDirectoryFile.exists()) {
			System.out.println("路径错误");
			return "";
		}				
		try {
			localDirectory = localDirectoryFile.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		if ("".equals(localDirectory)) {
			System.out.println("上传路径为空！");
			return "";
		}		
		localDirectory.replaceAll("\\\\", "/");
		if (!localDirectory.endsWith("/")) {
			localDirectory += "/";
		}		
		FTPSyncCore ftpClient = new FTPSyncCore();
		ftpClient.setLocalRootPath(localDirectory);		
		if (ftpClient.connectServer()) {
			List<Object> list = ftpClient.uploadManyFile(localDirectory, serverSaveRootPath);
			if (list == null || list.size() < 3) {
				System.out.println("uploadManyFile failed");
				return "";
			}
			int failCount = (Integer) list.get(0);
			int successCount = (Integer) list.get(1);
			String resultString = (String) list.get(2);
			System.out.println("upload dir finished, failed Count：" + failCount + " success count：" + successCount + " detail：" + resultString);
			ftpClient.closeConnect();	// 关闭连接
			if (failCount <= 0) {
				return "上传成功";
			}
		}
		return null;
	}
	//下载文件
	public String loadFile(String remoteFileName, String localFileName) throws IOException{
		FTPSyncCore ftpClient = new FTPSyncCore();
		if("".equals(localFileName)){
			System.out.println("路径为空！");
			return "";
		}	
		//将文件名连接在本地目录后面
		String fileName = remoteFileName.substring(remoteFileName.lastIndexOf("/")+1);
		StringBuffer sb = new StringBuffer(localFileName);
		sb.append("/"+fileName);
		
		//判断是否存在文件夹，如果不存在则创建文件夹
		File fileFolder = new File(localFileName);
		FileUtils.deleteDirs(fileFolder);
		fileFolder.mkdirs();
		if(ftpClient.connectServer()){
			boolean result = ftpClient.loadFile(remoteFileName, sb.toString());
			if (result) {
				System.out.println("downloadFile(" + remoteFileName + ") => SUCCESS");
			} else {
				System.out.println("downloadFile(" + remoteFileName + ") => FAILED");
			}
			ftpClient.closeConnect();	// 关闭连接
		}		
		return "下载成功";
	}
	//下载文件夹
	public String loadFolder(String uploadDirectory, String saveDirectory, String ftpRootPath){
		System.out.println("start down dir: " + ftpRootPath + " to local: " + saveDirectory);
		try {	
			File downloadDirectoryFile = new File(saveDirectory);
			FileUtils.deleteDirs(downloadDirectoryFile);
			downloadDirectoryFile.mkdirs();
			saveDirectory = downloadDirectoryFile.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("download direcroty mkdirs failed");
			return "下载失败";
		}
		
		if ("".equals(saveDirectory)) {
			System.out.println("download direcroty is empty");
			return "下载失败";
		}
		
		FTPSyncCore ftpClient = new FTPSyncCore();
		if (ftpClient.connectServer()) {
			boolean success = ftpClient.loadDirectory(uploadDirectory, ftpRootPath, saveDirectory);
			if (success) {
				System.out.println("downloadDirectory(" + ftpRootPath + ") => SUCCESS");
			} else {
				System.out.println("downloadDirectory(" + ftpRootPath + ") => FAILED");
			}
			ftpClient.closeConnect();	// 关闭连接
		}
		return "下载文件夹成功";
	}
}

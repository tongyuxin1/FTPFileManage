package ftpTest;

import java.io.IOException;

import org.junit.Test;

import com.gsp.common.ftpUtil.FTPConfig;
import com.gsp.common.ftpUtil.FTPSyncCore;
import com.gsp.common.ftpUtil.FileManage;
import com.gsp.common.junit.SpringJunitTest;


/**
 * @Class 
 * @Name:  FtpTest 
 * @Description: ftp文件管理测试
 * @Create Date: Jun 6, 2018
 */
public class FtpTest extends SpringJunitTest {
	@Test//上传文件
	public void uploadFile(){
		FileManage fm = new FileManage();
		String result = fm.uploadFile("D:/BaiduYunDownload/bbbb.txt", "/home/cld123/bbbb.txt");
		System.out.println(result);
	}
	@Test//上传文件夹
	public void uploadFolder(){
		FileManage fm = new FileManage();
		String result = fm.uploadFolder("D:\\BaiduYunDownload", FTPConfig.getRootPath());		
		System.out.print(result);	
	}
	@Test//文件下载
	public void downloadFile() throws IOException{
		FileManage fm = new FileManage();
		String result = fm.loadFile("/home/cld123/bbbb.txt", "D:/homesssss");		
		System.out.print(result);	
	}
	@Test//文件夹下载
	public void downloadFolder(){
		FileManage fm  = new FileManage();
		String result = fm.loadFolder("D:\\BaiduYunDownload","/home/cld123/",FTPConfig.getRootPath());
		System.out.println(result);
	}
	
	
	
	
	
	
	
	@Test
	public void loadMethod(){
		FTPSyncCore fc = new FTPSyncCore();
		if(fc.connectServer()){
			fc.loadFile("/home/cld123/bbbb.txt", "D:/home/bbb.txt");
		}
	}
}

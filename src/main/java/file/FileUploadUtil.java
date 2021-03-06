package file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import sun.misc.BASE64Decoder;

public class FileUploadUtil {
	
	public static String saveFile(String savePath,File uploadFile,String fileType) throws Exception{		
		
		String saveFileName =  getSaveName(fileType);
        FileOutputStream fos;
		
		fos = new FileOutputStream(savePath+"/"+saveFileName);		
		FileInputStream fis;		
		fis = new FileInputStream(uploadFile);		
        byte[] buffer = new byte[1024];
        int len = 0;	       
		while ((len = fis.read(buffer)) > 0){
		    fos.write(buffer , 0 , len);
		}		
		fis.close();
		fos.close();
		return savePath+"/"+saveFileName;		
	}	
	
	public static String getSaveName(String fileType){
		String saveName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+ "." + fileType;
		return saveName;
	}	

	
	/** 获取文件大小(KB) */
	public static int getFileKBSize(File file) {
		if (file == null || !file.exists() || !file.canRead()) {			
			return -1;
		}
		int kbSize = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			kbSize = fis.available() / 1024;
			fis.close();
		} catch (IOException e) {			
			e.printStackTrace();
			return -1;
		}
		return kbSize;
	}
	
//	public static String encodeBase64(byte[] bytes) throws IOException{
//		BASE64Encoder encoder = new BASE64Encoder();
//		return encoder.encode(bytes);
//	}

	public static void decodeBase64(String base64, String encodeType, OutputStream out) throws IOException{
        new BASE64Decoder().decodeBuffer(new ByteArrayInputStream(base64.getBytes(encodeType)), out);
	}

//	public static void main(String[] args){
//		System.out.println("FileUploadUtil.main()");
//		try{
//			String encStr = encodeBase64("aaa".getBytes());
//			decodeBase64(encStr, "utf-8", System.out);
//			System.out.flush();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//

}

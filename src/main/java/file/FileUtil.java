package file;

/**author:tangfeng
 * create date:2010/08/20
 * version :1.0
 * 
 */
import secret.AES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);
	public static boolean removeFile(String fileName) {
		if (fileName == null || fileName.trim().equals("")) {
			log.info("file name is null.");
			return false;
		}
		try {
			File file = new File(fileName);
			if (file.exists() && file.isFile() && file.canRead()) {
				if(!file.delete()){
					log.info("delete file "+file.getName()+" failed.");
				}
			}
			file = null;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean findFile(String fileName) {
		if (fileName == null || fileName.trim().equals("")) {
			log.info("file name is null.");
			return false;
		}
		try {
			File file = new File(fileName);
			if (file.exists()) {
				return true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	/**
	 * 将使用AES加密的文件读入内存，并解密
	 * 
	 * @param fileName
	 *            读取的文件名
	 * @param key
	 *            用于解密的key
	 * @return 返回文件内容
	 */
	public static String readEncryptFile(String fileName, String key) {
		StringBuilder sb = new StringBuilder();
		if (!findFile(fileName)) {
			log.info("file is not exist.");
			return null;
		}
		File inputFile = new File(fileName);
		if (!inputFile.canRead()) {
			log.info("file is unreadable.");
			return null;
		}
		FileInputStream fis = null;
		String result = "";
		try {
			fis = new FileInputStream(inputFile);
			int c = fis.read();
			while (c != -1) {
				sb.append((char) c);
				c = fis.read();
			}
			result = AES.decrypt(sb.toString(), key);
			fis.close();
		} catch (IOException e) {
			log.info("read file error");
		} catch (Exception e) {
			log.info("decrypt encrypt string failure!");
		}
		return result;
	}

	/**
	 * 将字符串使用ASE加密后写入文件中
	 * 
	 * @param fileName
	 *            写入的文件名
	 * @param key
	 *            用于加密的key
	 * @return 返回文件内容
	 */
	public static int writeEncryptFile(String fileName, String key,
			String content) {
		File inputFile = new File(fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(inputFile);
			String encryptString = AES.encrypt(content, key);
			if (!inputFile.canWrite()) {
				log.info("file is unwriteable.");
				return -1;
			}
			fos.write(encryptString.getBytes());
		} catch (IOException e) {
			log.info("read file error");
			return -1;
		} catch (Exception e) {
			log.info("decrypt encrypt string failure!");
			return -1;
		}finally{
			try{
				if (fos != null) {
					fos.close();
				}
			}catch(IOException e){
				log.info("close file output stream failed.");
			}
		}
		return 1;
	}

	public static void writeFile(String fileName, boolean isRewrite, Vector<String> vct) {
		if (fileName == null || fileName.trim().equals("")) {
			log.info("file name is null.");
			return;
		}
		try {
			File file = new File(fileName);
			if (file.exists() && file.isFile() && file.canRead()
					&& file.canWrite()) {
				FileOutputStream fos = new FileOutputStream(file);
				for(String str:vct){
					fos.write(str.getBytes("utf-8"));
				}
				fos.close();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	/**
	 * 以文件流的方式复制文件txt，xml，jpg，doc
	 * 
	 * @param src
	 *            文件源目录
	 * @param dest
	 *            文件目的目录
	 * @throws IOException
	 */
	public static void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists()){
			if(!file.createNewFile()){
				log.info("create new file failed.");
			}
		}
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if(oldname == null || path == null || newname == null
				|| newname.trim().equals("") || oldname.trim().equals("")
						|| path.trim().equals("")){
			return ;
		}
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				log.info(newname + "已经存在！");
			else {
				if(!oldfile.renameTo(newfile)){
					log.info("rename '"+newfile+"' failed.");
				}
			}
		}
	}

	/**
	 * 转移文件目录
	 * 
	 * @param filename     文件名
	 * @param oldpath      旧目录
	 * @param newpath      新目录
	 * @param cover        若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件，否则不操作
	 */
	public static void changeDirectory(String filename, String oldpath,
			String newpath, boolean cover) {
		if(filename == null || oldpath == null || newpath == null
				|| filename.trim().equals("") || oldpath.trim().equals("")
						|| newpath.trim().equals("")){
			return ;
		}
		if (!oldpath.equals(newpath)) {
			File oldfile = new File(oldpath + "/" + filename);
			File newfile = new File(newpath + "/" + filename);
			if (newfile.exists()) {// 若在待转移目录下，已经存在待转移文件
				if (cover)// 覆盖
					if(!oldfile.renameTo(newfile)){
						log.info("rename '"+newfile+"' failed.");
					}
				else
					log.info("在新目录下已经存在：" + filename);
			} else {
				if(!oldfile.renameTo(newfile)){
					log.info("rename '"+newfile+"' failed.");
				}
			}
		}
	}

	/** */
	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Vector<String> readFileByVector(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		Vector<String> vct = new Vector<String>();
		while ((fis.read(buf)) != -1) {
			String tmp = new String(buf); 
			vct.add(tmp.trim());
			buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
		}
		fis.close();
		return vct;
	}
	
	/** */
	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readFileByString(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
		}
		fis.close();
		return sb.toString();
	}
	/**
	 * 读文件
	 * 
	 * @param path ,split char
	 * @return
	 * @throws IOException
	 */
	public static String bufferedReader(String path,String split) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
			sb.append(temp + split);
			temp = br.readLine();
		}
		br.close();
		return sb.toString();
	}
	
	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String bufferedReader(String path) throws IOException {		
		return bufferedReader(path,"  ");
	}

	/**
	 * 从目录中读取xml文件
	 * 
	 * @param path
	 *            文件目录
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document readXml(String path) throws DocumentException, IOException {
		File file = new File(path);
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
		SAXReader saxreader = new SAXReader();
		Document document = (Document) saxreader.read(bufferedreader);
		bufferedreader.close();
		return document;
	}

	/** */
	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            目录
	 */
	public static void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists())
			if(!dir.mkdir()){
				log.info("create new direct failed.");
			}
	}

	/**
	 * 创建新文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 * @throws IOException
	 */
	public static void createFile(String path, String filename) throws IOException {
		File file = new File(path + "/" + filename);
		if (!file.exists())
			if(!file.createNewFile()){
				log.info("create new file failed.");
			}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 */
	public static void delFile(String path, String filename) {
		File file = new File(path + "/" + filename);
		if (file.exists() && file.isFile())
			if(!file.delete()){
				log.info("delete file failed.");
			}
	}

	/**
	 * 递归删除文件夹
	 * 
	 * @param path
	 */
	public static void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					if(!tmp[i].delete()){
						log.info("delete file "+tmp[i].getName()+" failed.");
					}
				}
			}
			if(!dir.delete()){
				log.info("delete direct failed.");
			}
		}
	}
	

	/**
	 * 利用StringBuffer写文件
	 * 
	 * @throws IOException
	 */
//	public boolean wirteFile(List<String> lst,String fileDir)  {
//		if(lst == null || lst.size() <= 0 || fileDir == null || fileDir.trim().equals("")){
//			return false;
//		}
//		lst.toString();
//	}
	/**
	 * 利用StringBuffer写文件
	 * 
	 * @throws IOException
	 */
	public static boolean wirteFile(Vector<String> vct,String fileDir)  {
		if(vct == null || vct.size() <= 0 || fileDir == null || fileDir.trim().equals("")){
			return false;
		}
		//vct.toString()
		File file = new File(fileDir);
		FileOutputStream out = null;
		try{
			if (!file.exists()){
				if(!file.createNewFile()){
					log.info("create new file failed.");
				}
			}
			out = new FileOutputStream(file, true);
			for (int i = 0; i < vct.size(); i++) {
				StringBuffer sb = new StringBuffer();
				sb.append(vct.get(i));
				out.write(sb.toString().getBytes("utf-8"));
			}
			out.close();
		}
		catch(IOException ie){
			log.info("write file error.");
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){
					log.info("close file error.");
				}
			}
		}		
		
		return true;
	}
	
	/**
	 * 利用StringBuffer写文件
	 * 
	 * @throws IOException
	 */
	public static boolean wirteFile(String value,String fileDir)  {
		if(value == null || fileDir == null || fileDir.trim().equals("")){
			return false;
		}
		//vct.toString()
		File file = new File(fileDir);
		FileOutputStream out = null;
		try{
			if (!file.exists()){
				if(!file.createNewFile()){
					log.info("create new file failed.");
				}
			}
			out = new FileOutputStream(file, true);
			out.write(value.getBytes("utf-8"));
			out.close();
		}
		catch(IOException ie){
			log.info("write file error.");
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){
					log.info("close file error.");
				}
			}
		}		
		
		return true;
	}

	public static void main(String[] args) {
		// writeEncryptFile("c:\\flag",AES.getAESKey(),"<date>121212</date>");
		// String result = readEncryptFile("c:\\flag", AES.getAESKey());
		// System.out.println(result);
		// String s = "<date>121212</date>";
		// System.out.println(s.substring(s.indexOf("<date>") +
		// "<date>".length(), s.indexOf("</date>")));
		// System.out.println(DogManagerJNI.checkDogPassword(1));
//		String s = readEncryptFile("D:\\space\\HikCMS\\WebRoot\\WEB-INF\\flag",
//				AES.getAESKey());
//		System.out.println(s);
		List<String> lst = new ArrayList<String>();
		lst.add("dflkjsalkfjldsaf;.xfsdfad]");
		lst.add("232323234");
		System.out.println(lst.toString());
		Vector<String> vct = new Vector<String>();
		vct.add("----dflkjsalkfjldsaf;.xfsdfad]");
		vct.add("----232323234");
		System.out.println(vct.toString());
	}
}

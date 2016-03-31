package com.shd.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


/**
 * YULON Project Title: com.yulon.sdk.objdata.FileUtil Description: Copyright:(c) 2007-2008 International Business Machine. All Rights Reserved. Created on Aug 20, 2007
 * 
 * @author Johnson Chen
 * @version 1.0
 */
public class ImFileUtil {

	private static Logger logger = Logger.getLogger(ImFileUtil.class);

	private static ResourceBundle fileSetRb ;//= ResourceBundle.getBundle("FileSet");

	public static String parseTempFileName(String tempFileName){
		String[] ss = tempFileName.split("_");
		int beginIndex = tempFileName.indexOf(ss[1]) + ss[1].length();
		
		return tempFileName.substring(beginIndex);
	}

	
	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	public static void deleteFiles(File path) throws Exception{
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
	}
	
	public static void deleteFile(String filePath) throws Exception {
		File file = new File(filePath);
		file.delete();
	}
	
	/**
	 * 
	 * @param sourceDirPath 來源目錄
	 * @param destDirPath  目的目錄
	 * @throws Exception
	 */
	public static void moveFileFromDir(String sourceDirPath, String destDirPath) throws Exception{
		File sourcedir  = new File(sourceDirPath);
		File dst  = new File(destDirPath);
		if(sourcedir.isDirectory()) {
			File[] content = sourcedir.listFiles();
		    for(int i = 0; i < content.length; i++) {
		    	File moveFile = content[i];
		    	boolean success = moveFile.renameTo(new File(dst, moveFile.getName()));
		    	if (!success)
					throw new Exception("Cannot move file.");
		    }
		}
	}
	
	
	
	public static void moveAndCopyFile(String dstPath, String filePath) throws Exception{
		File file = new File(filePath);
		boolean success = file.renameTo(new File(dstPath));
		if (!success)
			throw new Exception("Cannot move file.");
	}
	
	
	public static void moveFile(String dstPath, String filePath) throws Exception{
		File file = new File(filePath);
		File dst = new File(dstPath);
		boolean success = file.renameTo(new File(dst, file.getName()));
		if (!success)
			throw new Exception("Cannot move file.");
	}
	
	public static void moveFile(File dstFile, File sourceFile) throws Exception{
		boolean	success = sourceFile.renameTo(new File(dstFile, sourceFile.getName()));		
		if (!success)
			throw new Exception("Cannot move file.");
	}
	
	
	public static void moveFile(String dstPath, File sourceFile) throws Exception{		
		File dst = new File(dstPath, sourceFile.getName());	
		if(!dst.exists()){
			boolean	success = sourceFile.renameTo(dst);		
			if (!success){
				logger.error(sourceFile.getName()+"移動檔案失敗");
				throw new Exception(sourceFile.getName()+"移動檔案失敗");
			}
		}else{
			logger.error(sourceFile.getName()+"檔案重複");
			throw new Exception(sourceFile.getName()+"檔案重複");
		}
	}

	public static void createDir(String filePath) throws Exception {
		//logger.debug("Begin createDir() ");
		// File file1=new File(filePath+"/"+filePath);
		File file1 = new File(filePath);
		if (!file1.exists())
			file1.mkdirs();

	}

	public static String getSdpi(String type) throws Exception {
		String dim = "";
		return dim;
	}

	public static void copyFile(String targetPath, File fileObj) throws Exception, IOException {
		File targetFile = new File(targetPath);
		if (!targetFile.getCanonicalPath().equalsIgnoreCase(fileObj.getCanonicalPath())) {
			FileInputStream fin = null;
			FileOutputStream fout = null;
			try {
				fin = new FileInputStream(fileObj);
				fout = new FileOutputStream(targetFile);
				StreamCopier.copy(fin, fout);
				fin.close();
				fout.close();
			} finally {
				try {
					if (fin != null)
						fin.close();
				} catch (IOException e) {
					throw e;
				}
				try {
					if (fout != null)
						fout.close();
				} catch (IOException e) {
					throw e;
				}
			}

		}
	}
	
	public static void copyFiles(String inFilePath, String outFilePath) throws Exception, IOException {
		File inFileDir=new File(inFilePath);
		File[] inFiles=inFileDir.listFiles();
		int inFileSize=inFiles.length;
		for (int i=0;i<inFileSize;i++) {
			File inFile =inFiles[i];
			File outFile=new File(outFilePath+"/"+inFile.getName());
			copyFile(inFile,outFile); 
		}
	}

	public static void copyFile(File inFile, File outFile) throws Exception, IOException {

		if (!inFile.getCanonicalPath().equalsIgnoreCase(outFile.getCanonicalPath())) {
			FileInputStream fin = null;
			FileOutputStream fout = null;
			try {
				fin = new FileInputStream(inFile);
				fout = new FileOutputStream(outFile);
				StreamCopier.copy(fin, fout);
				fin.close();
				fout.close();
			} finally {
				try {
					if (fin != null)
						fin.close();
				} catch (IOException e) {
					throw e;
				}
				try {
					if (fout != null)
						fout.close();
				} catch (IOException e) {
					throw e;
				}
			}

		}
	}

	public static String getContentType(String fileName) throws Exception {
		logger.debug("Begin into getContentType() ");
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		String contentType = "";
		logger.debug("extName=" + extName);
		if (extName.equals("zip")) {
			contentType = fileSetRb.getString("zipFile");
		} else {
			contentType = fileSetRb.getString(extName);
		}
		logger.debug("End into getContentType() ");
		return contentType;
	}

	public static String replacePath(String path, String replacename) throws Exception {
		logger.debug(" Begin into replacePath()");
		logger.debug(" path		=" + path);
		logger.debug(" replaceName=" + replacename);
		path = path.substring(0, path.lastIndexOf("/")) + replacename;
		logger.debug("path=" + path);
		logger.debug(" End into replacePath()");
		return path;
	}

	public static boolean isExtImg(String extName) {
		boolean isImg = false;
		String imgFormat = fileSetRb.getString("extImg");
		StringTokenizer st = new StringTokenizer(imgFormat, ",");
		while (st.hasMoreElements()) {
			String contenType = (String) st.nextToken();
			if (extName.toLowerCase().equalsIgnoreCase(contenType))
				isImg = true;
		}
		return isImg;
	}

	public static boolean isExtHtml(String extName) {
		boolean isHtml = false;
		String imgFormat = fileSetRb.getString("extHtml");
		StringTokenizer st = new StringTokenizer(imgFormat, ",");
		while (st.hasMoreElements()) {
			String contenType = (String) st.nextToken();
			if (extName.toLowerCase().equalsIgnoreCase(contenType))
				isHtml = true;
		}
		return isHtml;
	}

	public static boolean isImg(String sourceContentType) {
		boolean isImg = false;
		String imgFormat = fileSetRb.getString("img");
		StringTokenizer st = new StringTokenizer(imgFormat, ",");
		while (st.hasMoreElements()) {
			String contenType = (String) st.nextToken();
			if (sourceContentType.equalsIgnoreCase(contenType))
				isImg = true;
		}
		return isImg;
	}

	public static boolean isZip(String sourceContentType) {
		boolean isZip = false;
		String zipFormat = fileSetRb.getString("zip");
		StringTokenizer st = new StringTokenizer(zipFormat, ",");
		while (st.hasMoreElements()) {
			String contenType = (String) st.nextToken();
			if (sourceContentType.equalsIgnoreCase(contenType))
				isZip = true;
		}
		return isZip;
	}

	public static boolean isHtml(String sourceContentType) {
		boolean isHtml = false;
		String htmlFormat = fileSetRb.getString("html");
		StringTokenizer st = new StringTokenizer(htmlFormat, ",");
		while (st.hasMoreElements()) {
			String contenType = (String) st.nextToken();
			if (sourceContentType.equalsIgnoreCase(contenType))
				isHtml = true;
		}
		return isHtml;
	}

	@SuppressWarnings("unused")
	public static String getRootPath() throws Exception {
		logger.debug("Begin into getRootPath() ");
		String path = "";
		String currentRoot = "";
		// String currentRoot=fileSetRb.getString("rootPublicDir")+FileEnum.rootDir;
		currentRoot = fileSetRb.getString("rootPublicDir");
		/*
		 * try { File dirfile=new File(currentRoot); File dirFiles[]=dirfile.listFiles(); for (int i=0;i<dirFiles.length;i++) { File tempFile=dirFiles[i]; if
		 * (tempFile.isDirectory()) { String dirName=tempFile.getName(); //logger.debug("dirName="+dirName); currentRoot=fileSetRb.getString("rootPublicDir")+"/"+dirName;
		 * logger.debug("root="+currentRoot); long size; try { logger.debug("aaaaa"); size=(FileUtils.getFreeSpace(currentRoot)); }catch(Exception e) {
		 * logger.debug("error error"); logger.debug("error ="+e.getMessage()); e.printStackTrace(); throw e; } logger.debug(" free space="); logger.debug("size="+size);
		 * long limitSpace=Long.parseLong(fileSetRb.getString("limitFreeSpace")); //size自己在減個10MB 當成安全值 if (size<limitSpace) { //logger.debug("currentRoot="+currentRoot);
		 * logger.debug("end into getRootPath() "); return currentRoot; } else { logger.debug("xxxxxxxxxxx"); } } } }catch(Exception e) { e.printStackTrace(); throw e; }
		 */
		// local
		// path="G:/temp/dev5";
		// 225
		// path="C:/temp/dev";
		return currentRoot;
	}
	

	/**
	 * inner common method() 寫檔
	 * 
	 * @param fileToCreate
	 * @param b
	 * @throws Exception
	 * 
	 * @author peter
	 */
	public static void writeFile(File fileToCreate, byte[] b) throws Exception {

		try {
			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
			fileOutStream.write(b);
			fileOutStream.flush();
			fileOutStream.close();			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * read Binary file 
	 * @param aInputFileName
	 * @return
	 * @author Johnson.Chen
	 */
	public static byte[] read(String aInputFileName){
		//logger.debug("Reading in binary file named : " + aInputFileName);
	    File file = new File(aInputFileName);
	    //logger.debug("File size: " + file.length());
	    byte[] result = new byte[(int)file.length()];
	    try {
	      InputStream input = null;
	      try {
	        int totalBytesRead = 0;
	        input = new BufferedInputStream(new FileInputStream(file));
	        while(totalBytesRead < result.length){
	          int bytesRemaining = result.length - totalBytesRead;	         
	          int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
	          }
	        }
	        //logger.debug("Num bytes read: " + totalBytesRead);
	      }
	      finally {
	    	  //logger.debug("Closing input stream.");
	        input.close();
	      }
	    }catch (FileNotFoundException ex) {
	    	//logger.debug("File not found.");
	    }catch (IOException ex) {
	    	//logger.debug(ex);
	    }
	    return result;
	  }
	
	/**
	 * write Binary File
	 * @param aInput
	 * @param aOutputFileName
	 * @author Johnson.Chen
	 */
	public static void write(byte[] aInput, String aOutputFileName){
		//logger.debug("Writing binary file...");
	    try {
	      OutputStream output = null;
	      try {
	        output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
	        output.write(aInput);
	      }
	      finally {
	        output.close();
	      }
	    }
	    catch(FileNotFoundException ex){
	    	//logger.debug("File not found.");
	    }
	    catch(IOException ex){
	    	//logger.debug(ex);
	    }
	  }	
	
	/*
	 * public static String getExtDir(String fileName) throws Exception { String extName=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase(); String extDir="";
	 * if (isZipFormat(extName)) { extDir=rb.getString("ZipFormatDir"); }else if (isImageFormat(extName)) { extDir=rb.getString("ImageFormatDir"); }else
	 * if(isAudioFormat(extName)) { extDir=rb.getString("AudioFormatDir"); }else if(isVideoFormat(extName)) { extDir=rb.getString("VideoFormatDir"); }else
	 * if(isFlatFileFormat(extName)) { extDir=rb.getString("FlatFileFormatDir"); } else { extDir=rb.getString("ErrorFormatDir"); } return extDir; }
	 */
	public static void main(String[] args){
		String dstPath = "F:/temp/Yulon_NISSAN/temp";		
		try {			
			//ImFileUtil.copyFile(targetPath, new File("c:/vcredist.bmp"));
			File sourceFile=new File("F:/temp/Yulon_NISSAN/nextITAPIWeb.war");
			ImFileUtil.moveFile(dstPath,sourceFile);
			
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

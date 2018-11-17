package com.haima.regeneration.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.haima.regeneration.config.Configurate;
import com.haima.regeneration.model.Fileinfo;
import com.haima.regeneration.net.URLConnection;

public class DirFiles {
	private static Logger log = LoggerFactory.getLogger(DirFiles.class);

	// 文件夹的文件
	public static List<Fileinfo> getFilesByDir(String source) {
		File file = new File(source);
		List<Fileinfo> infoList = new ArrayList<Fileinfo>();
		for (File temp : file.listFiles()) {
			if (temp.isFile()) {
				String filename = temp.getName();
				long size = temp.length();
				// 开发完成可以启用，目前测试便于测试。
				// if(filename.endsWith(".mp4") || filename.endsWith(".avi"))
				Fileinfo info = new Fileinfo(filename, size);
				infoList.add(info);
				log.debug("name:"+filename+" size:"+temp.length());
			}
		}
		return infoList;
	}
	
	public static void uploadFiles() {
		String path = Configurate.rootDir;
		List<Fileinfo> infoList = getFilesByDir(path);
		String infoJson = JSON.toJSONString(infoList);
		log.debug("转化为JSON结果为：");
		log.debug(infoJson);

		// 调用UPM运营平台接口上传信息
		String goUrl = Configurate.upmServiceURL + "/video/updateByChannel";
		URLConnection restTemplate = new URLConnection();
		String result = restTemplate.postUrlByJson(goUrl, infoJson);
		log.info("上传监控文件夹下文件信息,,响应结果==={0}", result);
	}

	// 循环遍历文件夹下的文件夹文件
	public static void showAllFiles(File file) {
		if (file.isDirectory()) {
			for (File temp : file.listFiles()) {
				showAllFiles(temp);
			}
		} else {
			System.out.println(file.getName());
		}
	}
	
	public static boolean copyFile(String fileName) throws IOException {
		File srcFile = new File( Configurate.rootDir, fileName);
		File destFile = new File( Configurate.destDir, fileName);
		
		return copyFile(srcFile,destFile);
	}
	
	public static boolean copyFile(File src, File dest) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);

		byte[] buffer = new byte[1024];
		int length;
		
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.close();
		
		return true;
		/*if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// 递归复制
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
 
			byte[] buffer = new byte[1024];
 
			int length;
			
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}*/
	}

}

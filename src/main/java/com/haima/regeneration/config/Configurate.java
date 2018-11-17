package com.haima.regeneration.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Configurate {

//	public static  String rootDir = "E:\\Tools\\";
	public static  String rootDir = "/working/video/uploadedVideo/";
	public static  String destDir = "/working/video/unUploadedVideo/";
	public static  String ip;
	public static  String hostName;
	
	public static String upmServiceURL = "http://172.16.208.63/";
//	public static String upmServiceURL = "http://172.16.40.80/";
	
	static  {
		try {
			
			//设置监控目录与拷贝目录
			String envRootDir = System.getenv("RootDir");
			rootDir = (envRootDir!=null && !envRootDir.trim().equals("")) ? envRootDir : rootDir;
			
			String envDestDir = System.getenv("DestDir");
			destDir = (envDestDir!=null && !envDestDir.trim().equals("")) ? envDestDir : destDir;
			
			String envUpmServiceURL = System.getenv("upmServiceURL");
			upmServiceURL = (envUpmServiceURL!=null && !envUpmServiceURL.trim().equals("")) ? envUpmServiceURL : upmServiceURL;
			
			InetAddress addr = InetAddress.getLocalHost();
			 //获取本机ip  
			ip=addr.getHostAddress().toString();
			//获取本机计算机名称
			hostName=addr.getHostName().toString(); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ip=hostName="";
		}  
	}
	
	public static Integer getVideoVersion() {
		return new Integer(new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}
}

package com.haima.regeneration.io;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.haima.regeneration.config.Configurate;
import com.haima.regeneration.model.FileEvent;
//import org.apache.log4j.Logger;
import com.haima.regeneration.net.URLConnection;

/**
 * 文件变化监听器
 *
 * 在Apache的Commons-IO中有关于文件的监控功能的代码. 文件监控的原理如下：
 * 由文件监控类FileAlterationMonitor中的线程不停的扫描文件观察器FileAlterationObserver，
 * 如果有文件的变化，则根据相关的文件比较器，判断文件时新增，还是删除，还是更改。（默认为1000毫秒执行一次扫描）
 *
 */
public class FileListener extends FileAlterationListenerAdaptor {
	private Logger log = LoggerFactory.getLogger(FileListener.class);

	/**
	 * 文件创建执行
	 */
	public void onFileCreate(File file) {
//		String fileName = file.getAbsolutePath() ;
		String fileName =file.getName() ;
		long size = file.length();
		log.info("[新建]:" + fileName);
		updataByFileEvent(fileName,size,"create");
	}

	/**
	 * 文件创建修改
	 */
	public void onFileChange(File file) {
		//log.info("[修改]:" + file.getAbsolutePath());
		String fileName =file.getName() ;
		log.info("[修改]:" + fileName);
		updataByFileEvent(fileName,"update");
	}

	/**
	 * 文件删除
	 */
	public void onFileDelete(File file) {
//		log.info("[删除]:" + file.getAbsolutePath());
		String fileName =file.getName() ;
		log.info("[删除]:" + fileName);
		updataByFileEvent(fileName,"delete");
	}

	/**
	 * 目录创建
	 */
	public void onDirectoryCreate(File directory) {
		log.info("[新建]:" + directory.getAbsolutePath());
	}

	/**
	 * 目录修改
	 */
	public void onDirectoryChange(File directory) {
		log.info("[修改]:" + directory.getAbsolutePath());
	}

	/**
	 * 目录删除
	 */
	public void onDirectoryDelete(File directory) {
		log.info("[删除]:" + directory.getAbsolutePath());
	}

	public void onStart(FileAlterationObserver observer) {
		// TODO Auto-generated method stub
		super.onStart(observer);
	}

	public void onStop(FileAlterationObserver observer) {
		// TODO Auto-generated method stub
		super.onStop(observer);
	}

	// 上传UPM运营平台
	private void updataByFileEvent(String fileName, String event) {
		updataByFileEvent(fileName,0,event);
	}
	private void updataByFileEvent(String fileName,long fileSize ,String event) {
		FileEvent fileEvent = new FileEvent(fileName,fileSize, event);
		String eventJson = JSON.toJSONString(fileEvent);
		log.info("上传文件事件信息==={0}", eventJson);

		// 调用UPM运营平台接口上传信息
		String goUrl = Configurate.upmServiceURL + "/video/updataByFileEvent";
		URLConnection restTemplate = new URLConnection();
		String result = restTemplate.postUrlByJson(goUrl,eventJson);
		log.info("上传文件事件信息,,响应结果==={0}", result);
	
	}

}
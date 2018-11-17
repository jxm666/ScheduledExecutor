package com.haima.regeneration.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.haima.regeneration.config.Configurate;
import com.haima.regeneration.io.DirFiles;
import com.haima.regeneration.io.FileListener;
import com.haima.regeneration.model.Fileinfo;
import com.haima.regeneration.model.VideoTask;
import com.haima.regeneration.model.VideoTaskResult;
import com.haima.regeneration.net.URLConnection;

public class ExecutorService {

	private Logger log = LoggerFactory.getLogger(FileListener.class);
	private ScheduledExecutorService scheduExec;

	public long start;
	String rootDir = Configurate.rootDir;
	String destDir = Configurate.destDir;
	
	String ip = Configurate.ip;
	String hostName = Configurate.hostName;

	public ExecutorService() {
		this.scheduExec = Executors.newScheduledThreadPool(8);
		this.start = System.currentTimeMillis();
	}

	// 1、获取文件夹下所有视频信息任务,及设置每天4点执行一次与服务启动执行一次。
	// 2、监控文件夹
	// 3、间隔30秒上报心跳
	// 4、间隔10秒 获取任务 -> 判断是否在监控文件夹下存在该文件，存在执行拷贝到segsave待上传文件夹，调用完成任务接口。

	public void getVidesInfo() {
		scheduExec.schedule(new Runnable() {
			public void run() {
				log.info("开始获取监控文件加下{0}", rootDir);
				DirFiles.uploadFiles();
				log.info("完成获取监控文件加下{0}", rootDir);
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}

	// 每天早4点执行一次
	public void getVidesInfoOfDayDelay() {
		long oneDay = 24 * 60 * 60 * 1000;
		long initDelay = getTimeMillis("04:00:00") - System.currentTimeMillis();
		initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
		scheduExec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				log.info("每天4点开始获取监控文件加下{0}", rootDir);
				DirFiles.uploadFiles();
				log.info("每天4点完成获取监控文件加下{0}", rootDir);
			}
		}, initDelay, oneDay, TimeUnit.MILLISECONDS);
	}
	
	private long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date currentDate = dateFormat.parse(dayFormat.format(new Date()) +  " " +time);
			return currentDate.getTime() ;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void toFileListener() {
		scheduExec.schedule(new Runnable() {
			public void run() {

				log.info("开始监控文件{0}", rootDir);
				try {
					// 轮询间隔 5 秒
					long interval = TimeUnit.SECONDS.toMillis(1);
					// 创建过滤器
					IOFileFilter avi = FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".avi"));
					IOFileFilter mp4 = FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".mp4"));
					IOFileFilter filter = FileFilterUtils.or(avi, mp4);
					// 使用过滤器
					FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir), filter);
					observer.addListener(new FileListener());
					// 创建文件变化监听器
					FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
					// 开始监控
					monitor.start();
				} catch (Exception e) {
					log.error("监控文件 {0}异常", rootDir);
					log.error(e.getMessage());
				}

				log.info("完成监控文件{0}", rootDir);
			}
		}, 2000, TimeUnit.MILLISECONDS);
	}

	public void upHeartBeat() {
		scheduExec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				log.info("开始上传心跳");
				URLConnection restTmplate = new URLConnection();
				String goUrl = Configurate.upmServiceURL + "/hearbeatGather";

				String bodyJson = "{\"systemName\":\"regenneration\",\"hostName\":\"" + hostName + "\",\"ip\":\"" + ip+ "\"}";
				String result = restTmplate.postUrlByJson(goUrl, bodyJson);
				log.info("完成上传心跳,响应结果=== {0}", result);
			}
			// 需要改为30000 定义为30秒上传一次，目前时测试
		}, 1000, 30000, TimeUnit.MILLISECONDS);
	}

	//获取任务 ->判断(本地是否存在) ->Y 拷贝到待上传文件夹,调用任务完成接口; N丢弃任务.
	public void getTask() {
		scheduExec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				log.info("开始获取任务");
            	URLConnection restTmplate = new URLConnection();
            	String goUrl =Configurate.upmServiceURL + "/video/taskVideo";
            	String bodyJson ="";
            	String result =restTmplate.postUrlByJson(goUrl, bodyJson);
            	
	            
            	//反序列化-> 循环任务列表 ->判断是否在监控文件夹
            	VideoTaskResult taskResult = JSON.parseObject(result, VideoTaskResult.class);
            	log.info("反序列化任务结果===",taskResult);
            	
            	if(taskResult !=null && taskResult.getCode() ==0 && taskResult.getTotal()>0) {
            		List<Fileinfo>fileList = DirFiles.getFilesByDir(rootDir);
            		for (VideoTask task : taskResult.getList()) {
						for (Fileinfo file : fileList) {
							//判断监控夹文件是否存在->执行拷贝
							if(task.getFilename().equals(file.getFilename())) {
								try {
									DirFiles.copyFile(file.getFilename());
									//调用完成接口
									URLConnection _restTmplate = new URLConnection();
					            	String _url =Configurate.upmServiceURL + "/video/finishTaskVideo";
					            	String _bodyJson ="{\"id\":"+task.getId()+",\"tstatus\":2}";
					            	String _result =_restTmplate.postUrlByJson(_url, _bodyJson);
					            	log.info("完成任务结果==={0}",_result);
									
								} catch (IOException e) {
									log.error(e.getMessage());
								}
							}
							else{
								continue;
							}
						}
					}
            	}
			}
			//定义为10秒获取一次
		}, 2000, 15000, TimeUnit.MILLISECONDS);
	}
}

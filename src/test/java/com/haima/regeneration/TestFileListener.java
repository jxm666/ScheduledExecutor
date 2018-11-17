package com.haima.regeneration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.haima.regeneration.io.FileListener;

public class TestFileListener {

	public static void main(String[] args) throws Exception {
		// 监控目录
		String rootDir = "E:\\Tools";
		// 轮询间隔 5 秒
		long interval = TimeUnit.SECONDS.toMillis(1);
		// 创建过滤器
		//IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
//		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
		IOFileFilter avi = FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".avi"));
		IOFileFilter mp4 = FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".mp4"));
//		files.accept(FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".avi")));
//		IOFileFilter filter = FileFilterUtils.or(directories, files,FileFilterUtils.and(FileFilterUtils.suffixFileFilter(".mp4")));
		IOFileFilter filter = FileFilterUtils.or(avi,mp4);
		// 使用过滤器
		FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir), filter);
		// 不使用过滤器
		// FileAlterationObserver observer = new FileAlterationObserver(new
		// File(rootDir));
		observer.addListener(new FileListener());
		// 创建文件变化监听器
		FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
		// 开始监控
		monitor.start();
	}
}

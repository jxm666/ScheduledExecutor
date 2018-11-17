package com.haima.regeneration;

import com.haima.regeneration.service.ExecutorService;

public class App {

	public static void main(String[] args) {
		ExecutorService execService = new ExecutorService();
		execService.getVidesInfo();
		execService.getVidesInfoOfDayDelay();
		execService.toFileListener();
		execService.upHeartBeat();
		execService.getTask();
	}

}

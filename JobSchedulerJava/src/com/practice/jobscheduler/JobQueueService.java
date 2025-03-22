package com.practice.jobscheduler;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JobQueueService extends Thread {
	
	private BlockingQueue<Job> queue;
	private JobService service;
	
	public JobQueueService(JobService service) {
		this.queue = new LinkedBlockingQueue<>(1);
		this.service = service;
	}
	
	@Override
	public void run() {
		while(true) {
			List<Job> jobs = this.service.getAllPendingJobs();
			if (jobs != null && jobs.size() > 0) {
				jobs.forEach(item-> {
					try {
						this.queue.put(item);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	public BlockingQueue<Job> getQueue() {
		return queue;
	} 
}

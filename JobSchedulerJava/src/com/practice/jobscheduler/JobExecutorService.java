package com.practice.jobscheduler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobExecutorService extends Thread {

	private JobQueueService jobQueueService;
	private JobService jobService;
	
	public JobExecutorService(JobQueueService jobQueueService, JobService jobService) {
		this.jobQueueService = jobQueueService;
		this.jobService = jobService;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Job job = jobQueueService.getQueue().take();
				ExecutorService executorService = Executors.newSingleThreadExecutor();
				
				Future<?> future = executorService.submit(job.getJobThread());
				
				executorService.submit(() -> {
					try {
						future.get();
						this.jobService.updateJobStatus(job.getId(), JobStatus.FINISHED);
					} catch (InterruptedException e) {
						
					} catch (ExecutionException e) {
						
					}
				});
				executorService.shutdown();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
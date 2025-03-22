package com.practice.jobscheduler;

public class JobSchedulerMain {

	public static void main(String[] args) {
		JobService service = new JobService();
		
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					String name = "Job " + i;
					service.createJob(name, () -> {System.out.println( name + " executed");});
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		JobQueueService jobQueueService = new JobQueueService(service);
		JobExecutorService jobExecutorService = new JobExecutorService(jobQueueService, service);
		JobExecutorService jobExecutorService2 = new JobExecutorService(jobQueueService, service);
		
		jobQueueService.start();
		jobExecutorService.start();
		jobExecutorService2.start();

	}

}

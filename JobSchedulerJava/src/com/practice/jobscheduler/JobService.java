package com.practice.jobscheduler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JobService{

	private Map<Integer, Job> jobMap;
	
	public JobService() {
		this.jobMap = new ConcurrentHashMap<>();
	}
	
	public void createJob(String name, Runnable r) {
		Job job = new Job(name, r);
		this.jobMap.put(job.getId(), job);
	}
	
	public synchronized List<Job> getAllPendingJobs() {
		long currentTime = System.currentTimeMillis();
		List<Job> job = this.jobMap.entrySet().stream().map(item -> item.getValue())
				.filter(item -> item.getStatus() == JobStatus.PENDING && item.getScheduledTime() <= currentTime)
				.peek(item -> item.setStatus(JobStatus.INPROGRESS)) 
				.toList();
		
		return Collections.synchronizedList(job);
	}
	
	public synchronized void  updateJobStatus(int jobId, JobStatus status) {
		Job job = jobMap.getOrDefault(jobId, null);
		if (job != null) {
			job.setStatus(status);
		}
	}
}

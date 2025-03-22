package com.practice.jobscheduler;

public class Job {
	private int id;
	private String name;
	private Runnable jobThread;
	private JobStatus status;
	private long scheduledTime;
	
	public Job(String name, Runnable jobThread) {
		super();
		this.id = IdGenerator.getId();
		this.name = name;
		this.jobThread = jobThread;
		this.status = JobStatus.PENDING;
		long currentTimeMillis = System.currentTimeMillis();
		this.scheduledTime = currentTimeMillis + 10000;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Runnable getJobThread() {
		return jobThread;
	}

	public void setJobThread(Runnable jobThread) {
		this.jobThread = jobThread;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public long getScheduledTime() {
		return scheduledTime;
	}

	static class IdGenerator {
		private static int id = 0;
		public static int getId() {
			id++;
			return id;
		}
	}
}

enum JobStatus {
	PENDING, FINISHED, ERROR, INPROGRESS
}

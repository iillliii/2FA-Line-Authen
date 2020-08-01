package com.iilllii.healthcheck.dto;

public class HealthCheckResultDTO {
	Integer totalWebsites;
	String status;
	Integer success;
	Integer failure;
	Long totalTime;

	public HealthCheckResultDTO() {
		this.success = 0;
		this.failure = 0;
		this.totalWebsites =0;
		this.totalTime =0L;
	}

	public Integer getTotalWebsites() {
		return totalWebsites;
	}

	public void setTotalWebsites(Integer totalWebsites) {
		this.totalWebsites = totalWebsites;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	
	

}

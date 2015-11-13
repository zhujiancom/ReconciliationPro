package com.rci.bean.dto;

import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;

public class SchemeTypeQueryDTO {
	private ActivityType activity;
	
	private ActivityStatus status;
	
	public SchemeTypeQueryDTO(){}

	public SchemeTypeQueryDTO(ActivityType activity,ActivityStatus status){
		this(status);
		this.activity = activity;
	}
	
	public SchemeTypeQueryDTO(ActivityStatus status){
		this.status = status;
	}

	public ActivityType getActivity() {
		return activity;
	}

	public ActivityStatus getStatus() {
		return status;
	}

	public void setActivity(ActivityType activity) {
		this.activity = activity;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}
	
	
}

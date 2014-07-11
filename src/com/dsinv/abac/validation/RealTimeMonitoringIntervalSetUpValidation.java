package com.dsinv.abac.validation;

import com.dsinv.abac.entity.RealTimeInterval;
import com.dsinv.abac.entity.User;
import com.dsinv.abac.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("RealTimeMonitoringIntervalSetUpValidator")
public class RealTimeMonitoringIntervalSetUpValidation {
	private static Logger logger = Logger.getLogger(User.class);

	public boolean supports(Class<?> klass) {
		return User.class.isAssignableFrom(klass);
	}

	public void validate(Object target, Errors errors) {
		RealTimeInterval realTimeInterval = (RealTimeInterval) target;
		if (realTimeInterval.getExecuteTime() < 0) {
			errors.rejectValue("executeTime", "executeTime", "Not Valid");
		}
	}
}

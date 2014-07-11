package com.dsinv.abac.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.dsinv.abac.entity.RealTimeProject;
import com.dsinv.abac.util.Utils;

@Component("realTimeProjectValidation")
public class RealTimeProjectValidation {
	private static Logger logger = Logger.getLogger(RealTimeProject.class);

    public boolean supports(Class<?> klass) {
        return RealTimeProject.class.isAssignableFrom(klass);
    }

    public void validate(Object target,HttpServletRequest request, Errors errors) {

    	RealTimeProject realTimeProject = (RealTimeProject) target;
        /*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
                "projectName",
                "Required");*/

        if (Utils.isEmpty(realTimeProject.getAssignTo())) {
            //Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("assigned.name.not.match"));
            errors.reject(Utils.getMessageBundlePropertyValue("assigned.name.not.match"));
        }

    }
}

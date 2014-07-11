package com.dsinv.abac.validation;

import com.dsinv.abac.entity.Holiday;
import com.dsinv.abac.entity.RealTimeProject;
import com.dsinv.abac.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 12/20/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Component("holidayValidation")
public class HolidayValidation {
    private static Logger logger = Logger.getLogger(HolidayValidation.class);

    public boolean supports(Class<?> klass) {
        return HolidayValidation.class.isAssignableFrom(klass);
    }
    public void validate(Object target,HttpServletRequest request, Errors errors) {

        Holiday holiday= (Holiday ) target;
        /*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
                "projectName",
                "Required");*/

        if (Utils.isEmpty(holiday.getDescription() )) {
            //Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("assigned.name.not.match"));
            errors.reject(Utils.getMessageBundlePropertyValue("typeMismatch.description"));
            Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("typeMismatch.description"));
        } if (holiday.getHolidayDate() == null) {
            //Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("assigned.name.not.match"));
            errors.reject(Utils.getMessageBundlePropertyValue("assigned.name.not.match"));
            Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("typeMismatch.holidayDate"));
        }

    }

}


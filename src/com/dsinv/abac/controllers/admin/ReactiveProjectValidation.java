package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.entity.ReactiveProject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/26/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */

@Component("reactiveProjectValidation")
public class ReactiveProjectValidation implements Validator {

    private static Logger logger = Logger.getLogger(ReactiveProjectController.class);

    public boolean supports(Class<?> klass) {
        return ReactiveProject.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {

        ReactiveProject reactiveProject = (ReactiveProject) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
                "projectName",
                "Required");

        if (reactiveProject.getRegion() == null || reactiveProject.getRegion().getId() == 0) {
            errors.rejectValue("region.id",
                    "region.id",
                    "Required");
        }

    }
}
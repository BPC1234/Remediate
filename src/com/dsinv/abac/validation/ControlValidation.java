package com.dsinv.abac.validation;

import com.dsinv.abac.controllers.admin.ReactiveProjectController;
import com.dsinv.abac.entity.Control;
import com.dsinv.abac.entity.ReactiveProject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 11/12/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("controlValidation")
public class ControlValidation {

    private static Logger logger = Logger.getLogger(ControlValidation.class);

    public boolean supports(Class<?> klass) {
        return ControlValidation.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {

        Control control = (Control) target;
        /*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "",
                "Required");*/

        if (control.getName() == null || control.getName() == "") {
            errors.rejectValue("name",
                    "name",
                    "Required");
        }

        if (control.getTransactionType() == null || control.getTransactionType() == "") {
            errors.rejectValue("transactionType",
                    "transactionType",
                    "Required");
        }


    }
}

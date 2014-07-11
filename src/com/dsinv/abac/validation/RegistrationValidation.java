package com.dsinv.abac.validation;

import com.dsinv.abac.entity.User;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component("RegistrationValidatior")
public class RegistrationValidation /*implements Validator*/ {
    private static Logger logger = Logger.getLogger(RegistrationValidation.class);

    public void validate(Object target, Errors errors, AdminService adminService,AdminJdbcService adminJdbcService) {
        User user = (User) target;
        Long userTypeId = adminJdbcService.getPersonTypeIdByEmail(user);
        logger.debug("SMN LOG: userTypeId:"+userTypeId);
        if (Utils.isEmpty(user.getUserName())) {
            errors.rejectValue("userName",
                    "userName",
                    "User Name must not be Empty.");
        } else if (!(user.getId() > 0) && adminService.isUserNameExist(user.getUserName())) {
            errors.rejectValue("userName",
                    "userName",
                    "User Not Unique.");

        } else if ((Utils.isEmpty(user.getPassword()))) {
            errors.rejectValue("password",
                    "password",
                    "Password Must Not be Empty.");
        } else if (!(user.getPassword()).equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    "confirmPassword",
                    "Password Does Not match.");
        } else if (!adminJdbcService.isValidPersonTypeWithMail(user)) {
            errors.rejectValue("userType",
                    "userType",
                    "Do not Match E-mail with "+user.getUserType()+" type.");
        }else if (!adminJdbcService.isUserExistWithPersonTypeId(userTypeId)) {
            errors.rejectValue("email",
                    "email",
                    "You have an account with this e-mail with "+user.getUserType().toLowerCase()+" type.");
        }/*else if (!adminJdbcService.isEmailExistWithPersonType(user)) {
            errors.rejectValue("email",
                    "email",
                    "You are registered with this e-mail with "+user.getUserType()+" type.");
        }*/
    }
    public void validateNewPassword(Object target, Errors errors, AdminService adminService) {
        User user = (User) target;

        if (Utils.isEmpty(user.getUserName())) {
            errors.rejectValue("userName",
                    "userName",
                    "User Name must not be Empty.");
        } else if (!(user.getId() > 0) && adminService.isUserNameExist(user.getUserName())) {
            errors.rejectValue("userName",
                    "userName",
                    "User Not Unique.");
        }else if (!(user.getGivenPassword()).equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    "confirmPassword",
                    "Password Does Not match.");

        }
    }

}

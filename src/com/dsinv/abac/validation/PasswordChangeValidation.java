package com.dsinv.abac.validation;

import com.dsinv.abac.entity.User;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component("changePasswordValidator")
public class PasswordChangeValidation /*implements Validator*/ {
    private static Logger logger = Logger.getLogger(User.class);
    private AdminService adminService;

    public boolean supports(Class<?> klass) {
        return User.class.isAssignableFrom(klass);
    }


    public void validate(Object target, Errors errors,AdminService adminService) {
        User user = (User) target;
        this.adminService = adminService;
       /* if(!user.getUserName().equals(Utils.getLoggedUserName())){
            errors.rejectValue("userName",
                    "userName",
                    "Not Match with logged User Name");
        }else */if((Utils.isEmpty(user.getPassword()))) {
            errors.rejectValue("password",
                    "password",
                    "Password Must Not be Empty.");
        }else  if (!(user.getPassword()).equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    "confirmPassword",
                    "Password Does Not match.");

           }
        }
    }

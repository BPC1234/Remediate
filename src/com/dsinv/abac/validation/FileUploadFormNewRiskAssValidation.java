package com.dsinv.abac.validation;

import com.dsinv.abac.entity.NewRiskAssessmentTxBean;
import com.dsinv.abac.entity.ProactiveBlockWeightRatio;
import com.dsinv.abac.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;


@Component("FileUploadFormNewRiskAssValidation")
public class FileUploadFormNewRiskAssValidation /*implements Validator*/ {
    private static Logger logger = Logger.getLogger(FileUploadFormNewRiskAssValidation.class);
    private AdminService adminService;

    public boolean supports(Class<?> klass) {
        return NewRiskAssessmentTxBean.class.isAssignableFrom(klass);
    }


    public void validate(Object target, Errors errors) {
        NewRiskAssessmentTxBean newRiskAssessmentTxBean = (NewRiskAssessmentTxBean) target;
        MultipartFile multipartFile = newRiskAssessmentTxBean.getFileData();

        if(multipartFile.getSize() >= 50000000){
            errors.rejectValue("fileData",
                    "fileData",
                    "Max File Size 50 MB");
            logger.debug("-------------in validation6666666666");
        }/*else if(!(id > 0) && adminService.isUserNameExist(user.getUserName())){
            errors.rejectValue("userName",
                    "userName",
                    "User Not Unique.");

        }else if((Utils.isEmpty(user.getPassword()))) {
            errors.rejectValue("password",
                    "password",
                    "Password Must Not be Empty.");
        }else  if (!(user.getPassword()).equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    "confirmPassword",
                    "Password Does Not match.");

           }*/
       // }
    }
}

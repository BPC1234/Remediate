package com.dsinv.abac.validation;

import com.dsinv.abac.entity.Control;
import com.dsinv.abac.entity.GlobalTransactionSearch;
import com.dsinv.abac.entity.RealTimeProject;
import com.dsinv.abac.util.Utils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.log4j.Logger;

@Component()
public class GlobalSearchValidation implements Validator{
    private static Logger logger = Logger.getLogger(GlobalSearchValidation.class);

    public boolean supports(Class<?> klass) {
        return GlobalSearchValidation.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {

        GlobalTransactionSearch globalTransactionSearch = (GlobalTransactionSearch) target;
       /* if (!Utils.isValidDecimal(globalTransactionSearch.getTransactionId()+"")) {
            errors.rejectValue("transactionId",
                    "transactionId",
                    "Required");
        }*/

        /*if (globalTransactionSearch.getTransactionDate() == null) {
            errors.rejectValue("transactionDate",
                    "transactionDate",
                    "Required");
        }*/

    }
}

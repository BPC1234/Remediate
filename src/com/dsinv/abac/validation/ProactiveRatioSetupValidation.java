package com.dsinv.abac.validation;

import com.dsinv.abac.entity.ProactiveBlockWeightRatio;
import com.dsinv.abac.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component("proactiveRatioSetupValidatior")
public class ProactiveRatioSetupValidation /*implements Validator*/ {
    private static Logger logger = Logger.getLogger(ProactiveRatioSetupValidation.class);
    private AdminService adminService;

    public boolean supports(Class<?> klass) {
        return ProactiveBlockWeightRatio.class.isAssignableFrom(klass);
    }


    public void validate(Object target, Errors errors,AdminService adminService) {
        ProactiveBlockWeightRatio proactiveBlockWeightRatio = (ProactiveBlockWeightRatio) target;
        logger.debug(" cpiScore : "+proactiveBlockWeightRatio.getCpiScore());
        if(proactiveBlockWeightRatio.getCpiScore() > 100){
            errors.rejectValue("cpiScore",
                    "cpiScore",
                    "Must be less than 100%.");
        }
        logger.debug(" revenues : "+proactiveBlockWeightRatio.getRevenues());
        if(proactiveBlockWeightRatio.getRevenues() > 100){
            errors.rejectValue("revenues",
                    "",
                    "Must be less than 100%.");
        }
        if(proactiveBlockWeightRatio.getSalesModelRelationships() > 100){
            errors.rejectValue("salesModelRelationships",
                    "salesModelRelationships",
                    "Must be less than 100%.");
        }
        if(proactiveBlockWeightRatio.getGovernmentInteraction() > 100){
            errors.rejectValue("governmentInteraction",
                    "governmentInteraction",
                    "Must be less than 100%.");
        }
        if(proactiveBlockWeightRatio.getNatureOfBusinessOperations() > 100){
            errors.rejectValue("natureOfBusinessOperations",
                    "natureOfBusinessOperations",
                    "Must be less than 100%.");
        }
        }
    }

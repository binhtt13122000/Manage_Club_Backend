package binhtt.dev.manage.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserUniqueValidator implements ConstraintValidator<Unique,String> {

    @Autowired
    private UniqueServiceImp service;

    private String fieldName;
    private String className;

    @Override
    public void initialize(Unique unique) {
        this.fieldName = unique.fieldName();
        this.className = unique.className();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if(service == null){
            return true;
        }
        return !service.fieldValueExists(this.fieldName, fieldValue, this.className);
    }
}

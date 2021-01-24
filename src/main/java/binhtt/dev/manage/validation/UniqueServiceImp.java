package binhtt.dev.manage.validation;

import binhtt.dev.manage.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniqueServiceImp implements UniqueService {
    @Autowired
    private AccountRepository accountRepository;


    public boolean fieldValueExists(String fieldName, String fieldValue, String className) throws UnsupportedOperationException {
        if(fieldValue == null){
            return false;
        }
        if(className.equals("Account")){
            switch (fieldName){
                case "studentID":
                    return accountRepository.existsAccountByStudentID(fieldValue);
                case "email":
                    return accountRepository.existsAccountByEmail(fieldValue);
                default:
                    throw new UnsupportedOperationException("Field name not supported");
            }
        }

        throw new UnsupportedOperationException("Class not supported");
    }
}

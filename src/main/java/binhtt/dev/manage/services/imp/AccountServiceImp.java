package binhtt.dev.manage.services.imp;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Role;
import binhtt.dev.manage.repositories.AccountRepository;
import binhtt.dev.manage.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Account findAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public boolean register(Account account) {
        if(findAccountById(account.getStudentID()) == null && findAccountByEmail(account.getEmail()) == null){
            account.setStatus(true);
            account.setPassword(passwordEncoder.encode(account.getStudentID()));
            account.setRole(new Role(1, "MEMBER"));
            accountRepository.save(account);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElse(null);
    }
}

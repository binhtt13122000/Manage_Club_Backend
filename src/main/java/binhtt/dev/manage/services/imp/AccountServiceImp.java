package binhtt.dev.manage.services.imp;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Role;
import binhtt.dev.manage.repositories.AccountRepository;
import binhtt.dev.manage.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public boolean addMember(Account account) {
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

    @Override
    public void updateProfile(Account currentAccount, Account account) {
        currentAccount.setAvatarId(account.getAvatarId());
        currentAccount.setFullname(account.getFullname());
        currentAccount.setPhone(account.getPhone());
        accountRepository.save(currentAccount);
    }

    @Override
    public void changePassword(Account currentAccount, String newPassword, String oldPassword) {
        if (oldPassword != null) {
            if (BCrypt.checkpw(oldPassword, currentAccount.getPassword())) {
                if (newPassword != null && newPassword.length() >= 6 && newPassword.length() <= 20) {
                    String hashPass = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    currentAccount.setPassword(hashPass);
                    accountRepository.save(currentAccount);
                }
            }
        }
    }

    @Override
    public Page<Account> findAllAccount(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> findByName(String name, Pageable pageable) {
        return accountRepository.findAccountsByFullnameContaining(name, pageable);
    }
}

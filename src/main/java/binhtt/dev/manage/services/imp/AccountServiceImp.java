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

import java.util.List;

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
    public void addMember(Account account) {
        account.setStatus(true);
        account.setPassword(passwordEncoder.encode(account.getStudentID()));
        account.setRole(new Role(1, null));
        accountRepository.save(account);
    }

    @Override
    public void updateProfile(Account currentAccount, Account account) {
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
    public List<Account> findAccountByProperties(String studentID, String fullname, String email, boolean isLeader, boolean isMember) {
        return accountRepository.getAccountByProperties(studentID, fullname, email, isLeader, isMember);
    }


    @Override
    public void banAccount(Account currentAccount) {
        currentAccount.setStatus(false);
        accountRepository.save(currentAccount);
    }

    @Override
    public void activeAccount(Account currentAccount) {
        currentAccount.setStatus(true);
        accountRepository.save(currentAccount);
    }
}

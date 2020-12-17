package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account findAccountById(String id);
    boolean addMember(Account account);
    Account findAccountByEmail(String email);
    void updateProfile(Account currentAccount, Account account);
    void changePassword(Account currentAccount, String newPassword, String oldPassword);
    Page<Account> findAllAccount(Pageable pageable);
    Page<Account> findByName(String name, Pageable pageable);
}

package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account findAccountById(String id);
    void addMember(Account account);
    void updateProfile(Account currentAccount, Account account);
    void changePassword(Account currentAccount, String newPassword, String oldPassword);
    Page<Account> findAllAccount(int roleId, Pageable pageable);
    Page<Account> findByName(int roleId, String name, Pageable pageable);

    void banAccount(Account currentAccount);

    void activeAccount(Account currentAccount);
}

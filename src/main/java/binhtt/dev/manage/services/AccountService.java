package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Account;

public interface AccountService {
    Account findAccountById(String id);
    boolean addMember(Account account);
    Account findAccountByEmail(String email);
    void updateProfile(Account currentAccount, Account account);

    void changePassword(Account currentAccount, String newPassword, String oldPassword);
}

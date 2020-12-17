package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Account;

public interface AccountService {
    Account findAccountById(String id);
    boolean register(Account account);
    Account findAccountByEmail(String email);
}

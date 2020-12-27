package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountService {
    Account findAccountById(String id);
    void addMember(Account account);
    void updateProfile(Account currentAccount, Account account);
    void changePassword(Account currentAccount, String newPassword, String oldPassword);
    List<Account> findAccountByProperties(String studentID, String fullname, String email, boolean isLeader, boolean isMember);

    void banAccount(Account currentAccount);

    void activeAccount(Account currentAccount);
}

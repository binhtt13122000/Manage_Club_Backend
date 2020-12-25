package binhtt.dev.manage.repositories;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findAccountByEmail(String email);
    Page<Account> findAccountsByRoleId(int roleId, Pageable pageable);
    Page<Account> findAccountsByFullnameContainingAndRoleId(String name, int roleId, Pageable pageable);
    boolean existsAccountByEmail(String email);
    boolean existsAccountByStudentID(String studentID);
}

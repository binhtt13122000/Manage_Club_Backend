package binhtt.dev.manage.repositories;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findAccountByEmail(String email);
    Page<Account> findAll(Pageable pageable);
    Page<Account> findAccountsByFullnameContaining(String name, Pageable pageable);
}

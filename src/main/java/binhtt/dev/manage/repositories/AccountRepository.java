package binhtt.dev.manage.repositories;

import binhtt.dev.manage.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsAccountByEmail(String email);
    boolean existsAccountByStudentID(String studentID);
    @Query("select a from Account a where a.studentID like concat('%', :studentID, '%')" +
            "and a.fullname like concat('%', :fullname, '%') " +
            "and a.email like concat('%', :email, '%')" +
            "and a.role.roleId = CASE WHEN :isMember = false THEN -1 ELSE 1 END " +
            "or a.role.roleId = CASE WHEN :isLeader = false THEN -1 ELSE 2 END")
    List<Account> getAccountByProperties(
            @Param("studentID") String studentID,
            @Param("fullname") String fullname,
            @Param("email") String email,
            @Param("isLeader") boolean isLeader,
            @Param("isMember") boolean isMember);


//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.name = :companyName ")

}

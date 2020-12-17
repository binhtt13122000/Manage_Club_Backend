package binhtt.dev.manage.repositories;

import binhtt.dev.manage.supporters.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiUserRepository extends JpaRepository<NotificationUser, String> {
}

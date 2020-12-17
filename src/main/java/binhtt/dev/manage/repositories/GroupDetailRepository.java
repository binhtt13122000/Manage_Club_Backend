package binhtt.dev.manage.repositories;

import binhtt.dev.manage.supporters.GroupDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDetailRepository extends JpaRepository<GroupDetail, String> {
}

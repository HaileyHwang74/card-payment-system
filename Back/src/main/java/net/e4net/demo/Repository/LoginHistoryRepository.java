package net.e4net.demo.Repository;

import net.e4net.demo.Entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory,Long> {
}

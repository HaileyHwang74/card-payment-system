package net.e4net.demo.Repository;

import net.e4net.demo.Entity.MoneyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MoneyHistoryRepository extends JpaRepository <MoneyHistory,Long> {
    List<MoneyHistory> findByMoneyMoneySn(Long membSn);
}

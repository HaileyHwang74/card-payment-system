package net.e4net.demo.Repository;


import net.e4net.demo.Entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MoneyRepository extends JpaRepository<Money, Long> {

}

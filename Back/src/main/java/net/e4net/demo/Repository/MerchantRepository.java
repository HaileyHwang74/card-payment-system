package net.e4net.demo.Repository;


import net.e4net.demo.Entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {

}
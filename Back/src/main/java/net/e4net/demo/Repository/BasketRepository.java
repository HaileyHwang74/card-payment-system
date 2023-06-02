package net.e4net.demo.Repository;

import net.e4net.demo.Entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {

    List<Basket> findAllByMembSn(Long membSn);

}

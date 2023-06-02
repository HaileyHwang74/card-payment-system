package net.e4net.demo.Repository;


import net.e4net.demo.Entity.Goods;
import net.e4net.demo.Entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods,String> {
    List<Goods> findByMerchant(Merchant merchant);

//    Goods findById (String goodsNo);

}
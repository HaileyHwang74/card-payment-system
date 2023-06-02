package net.e4net.demo.Repository;

import net.e4net.demo.Entity.ColumnCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Q. class가 아닌 interface로 작성할 경우 JpaRepository를 상속 받으므로 @Repository 를 안 해줘도 되나?
 */

public interface ColumnCodeRepository extends JpaRepository<ColumnCode,Long> {
    Optional<ColumnCode> findById(Long columnCodeSn);
}

package net.e4net.demo.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.sql.Timestamp;


@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity {

    @Column(name = "USE_YN", length = 1)
    protected String useYn;

    //원래는 nullable false 임 => firstRegistMembSn
    @CreatedBy
    @Column(name = "FIRST_REGIST_MEMB_SN", updatable = false, length = 10)
    protected Long firstRegistMembSn;

    @CreatedDate
    @Column(name = "FIRST_REGIST_DT", updatable = false, length = 14)
    protected Timestamp firstRegistDt;
    @LastModifiedBy
    @Column(name = "LAST_REGIST_MEMB_SN", length = 10)
    private Long lastRegistMembSn;

    @LastModifiedDate
    @Column(name = "LAST_CHANGE_DT", length = 14)
    protected Timestamp lastChangeDt;


    @PrePersist
    public void FirstUseYn() { this.useYn = "Y";
    }


    public BaseEntity(String useYn,Long firstRegistMembSn, Timestamp firstRegistDt, Long lastRegistMembSn, Timestamp lastChangeDt) {
        this.useYn = useYn;
        this.firstRegistMembSn = firstRegistMembSn;
        this.firstRegistDt = firstRegistDt;
        this.lastRegistMembSn = lastRegistMembSn;
        this.lastChangeDt = lastChangeDt;
    }


}

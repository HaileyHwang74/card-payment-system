package net.e4net.demo.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "tb_column_code")
@Table(name = "tb_column_code")
@NoArgsConstructor
@SequenceGenerator(
        name = "ColumnCode_SEQ_GENERATOR",
        sequenceName = "ColumnCode_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class ColumnCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ColumnCode_SEQ_GENERATOR")
    @Column(name = "COLUMN_CODE_SN")
    private Long columnCodeSn;
    //공통코드 테이블 참조컬럼이름
    @Column(name = "CODE_ID")
    private String codeId;
    //코드 , 10,20
    @Column(name = "CODE_VALUE")
    private String codeValue;
    //코드 설명
    @Column(name = "CODE_DESC")
    private String codeDesc;

    /**
     * Q.파라미터에 member 객체로 못 넣는 이유?
     * A. initConfig에서 변수 하나하나해서 값을 넣으니까 객체가 아닌 변수로 넣어줘야한다.
     */

//    나중에 다시 살려야함
    @Builder
    private ColumnCode(Long columnCodeSn, String codeId, String codeValue, String codeDesc) {
        this.columnCodeSn = columnCodeSn;
        this.codeId = codeId;
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }


    /** initConfig에 작성해서 @PostConstruct 로 빈이 다 생성되고 난후 실행할 수있게 해주었다.*/
//    public static ColumnCode createColumnCode(Long columnCodeSn, String codeId, String codeValue, String codeDesc){
//        return ColumnCode.builder()
//                .columnCodeSn(columnCodeSn)
//                .codeId(codeId)
//                .codeValue(codeValue)
//                .codeDesc(codeDesc)
//                .build();
//    }

    /**
     * 연관관계 주인(외래키 가진 사람)만이 외래키를 관리(등록, 수정, 삭제) 할 수있고, 주인이 아닌 엔티티(pk)는 읽기만 가능
     * 이번 프로젝트에서는 단방향관계를 전제로 한다.
     * 따라서, 밑의 조인관계는 주석처리해주었다.
     */
//    @OneToOne(mappedBy = "membCls", cascade  = CascadeType.PERSIST)
//    private Member member;
    //    @OneToOne(mappedBy = "membStatusCd", cascade  = CascadeType.PERSIST)
//    private Member member;

}

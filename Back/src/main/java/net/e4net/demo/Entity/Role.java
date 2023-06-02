package net.e4net.demo.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="role")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //권한이름
    @Column(nullable = false, length =50)
    private String roleName;
}

package net.e4net.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.Member;

@Getter
@Setter
public class LoginHistoryDto {

    private Long loginSn;
    private Member member;
    private String connectIp;

}

package net.e4net.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private Long membSn;
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

    private String membCls;
}

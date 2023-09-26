package com.example.jtest1;

import java.io.Serializable;


/********************************************** */
/**
 * @brief : 로그인정보DTO
 **/
public class TokenDto implements Serializable {


        private String grantType;
        private String accessToken;
        private Long tokenExpiresIn;
        private String membId;
        private Long membSn;
        private String membCls;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(Long tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public Long getMembSn() {
        return membSn;
    }

    public void setMembSn(Long membSn) {
        this.membSn = membSn;
    }

    public String getMembCls() {
        return membCls;
    }

    public void setMembCls(String membCls) {
        this.membCls = membCls;
    }
}

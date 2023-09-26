package com.example.jtest1;



import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/********************************************** */
/**
 * @brief : 안드로이드 <-> 웹서버 통신 RETROFIT2
 **/
public interface RetrofitService {

    /** 연동테스트 **/
    @GET("/public/sample/data")
    Call<Map<String,Object>> getSampleData(@Query("param") String param);

    /** ID중복체크 **/
    @POST("/member/android/idcheck")
    Call<Map<String,String>> getIdcheck(@Body Map<String,String> map);

    /** SMS인증발송 **/
    @POST("/android/sendsms")
    Call<Map<String,String>> sendsms(@Body Map<String,String> map);

    /** 회원가입 **/
    @POST("/auth/signup")
    Call<Object> signup(@Body MemberDto memberDto);

    /** 로그인 **/
    @POST("/auth/login")
    Call<TokenDto> login(@Body MemberDto memberDto);

    /** 결제 머니충전 정보 저장 **/
    @POST("/member/inserttransfer/{money}")
    Call<Object> insrttrans(@Path("money") Long money,@Body TransDto trans);

    /** 상품구매 결제정보 저장 **/
    @POST("8888/member/savebuydetail/{money}")
    Call<Object> savebuydetail(@Path("money") Long money,@Body BuyHstDto buyHstDto);


}

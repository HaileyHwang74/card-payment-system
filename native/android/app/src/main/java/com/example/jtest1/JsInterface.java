package com.example.jtest1;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Settings.System.getString;

import static com.example.jtest1.Ipinfo.serverip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/********************************************** */

/**
 * @brief : JS인터페이스
 * 웹뷰와 안드로이드간의 브릿지 , 연동 함수들
 **/
public class JsInterface {

    Context context;
    Activity activity;
    WebView webView;

    PaymentWebViewActivity paymentWebViewActivity = (PaymentWebViewActivity)PaymentWebViewActivity.paymentWebViewActivity;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String fcmToken;

    String b;
    String dataString;

    Retrofit retrofit;
    RetrofitService retrofitService;


    /********************************************** */
    /**
     * @brief : JSINTERFACE생성자
     * RETROFIT과 SHAREDPREFERENCES 를 생성
     **/
    public JsInterface(Context context, WebView webView) {

        this.context = context;
        this.activity = (Activity) context;
        this.webView = webView;
        this.sharedPreferences = context.getSharedPreferences("pref_default", MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        b = sharedPreferences.getString("text", "hi");

        retrofit = new Retrofit.Builder()
                .baseUrl(serverip + "8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

    }

    /********************************************** */
    /**
     * @brief : 연동테스트
     **/
    @JavascriptInterface
    public void appFunction2(String msg) {
        Toast.makeText(context, " in app=" +msg, Toast.LENGTH_SHORT).show();
        Log.d("asdfasdfasdf",msg);
        Log.d("asdfasdfasdf","failfial");

    }

    /********************************************** */
    /**
     * @brief : 웹뷰로 프론트 접속 시 웹세션에 로그인 정보 저장되는 로직
     *          해당함수는 웹뷰 프론트에서 바로 호출됨
     *          "dataString"에 JWT토큰, 로그인 정보가 저장되어있음(MEMBSN, MEMBID , 권한)
     **/
    @JavascriptInterface
    public String setLogininfo() {
        dataString = sharedPreferences.getString("dataString","hi");
        Log.d("zxcvzxcv",dataString);
        return dataString;

    }

    /********************************************** */
    /**
     * @brief : 웹뷰로 프론트 접속 시 웹세션에 로그인 정보 저장되는 로직
     *          해당함수는 웹뷰 프론트에서 바로 호출됨
     *          "dataString"에 JWT토큰, 로그인 정보가 저장되어있음(MEMBSN, MEMBID , 권한)
     **/
    @JavascriptInterface
    public void logout() {
        Log.d("qwerqwer" , "exit");

        new AlertDialog.Builder(this.context)
                .setTitle("app title")
                .setMessage("app" )
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("[webView]", "확인버튼 클릭");
                        paymentWebViewActivity.finish();

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("[webView]", "취소버튼 클릭");
                    }
                })
                .setCancelable(false)
                .create().show();


    }


    /********************************************** */
    /**
     * @brief :   머니충전 결제정보 임시 저장 메서드
     * 프론트에서 사용자가 결제를 진행할 때,  결제 정보를 임시로
     * SHAREDPREFERENCES에 저장한 뒤,
     * 결제가 완료되면 웹서버에 해당 정보를 가져와 저장하도록 설계함
     **/
    @JavascriptInterface
    public void setDetails(String transDto, String money) {
        Toast.makeText(context, " in app=", Toast.LENGTH_SHORT).show();
        editor.putString("transDetail", transDto);
        editor.putLong("updatemoney", Long.parseLong(money));
        editor.putString("transone", "one");
        editor.apply();
        Log.d("zxcvzxcv", "hello");

    }

    /********************************************** */
    /**
     * @brief : 머니충전 시 프론트에서 웹서버로 푸시알람을 요청하기위해
     * 프론트에서 해당 함수를 실행하여 머니충전 정보를 불러온다
     **/
    @JavascriptInterface
    public String getDetails() {
        String transone = sharedPreferences.getString("transone", "false");
        String trans = "notone";
        if (transone.equals("one")) {
            trans = sharedPreferences.getString("transDetail", "notone");
            Log.d("getDetails", trans.toString());
        }
        return trans;
    }

    /********************************************** */
    /**
     * @brief : 머니충전 시 결제가 완료되면 웹서버로 요청하여 결제정보를 저장하는 로직
     * 프론트와 연동되어 작동하므로 프론트에서 다중실행을 방지 하기 위해
     * 1번만 실행되도록 SHAREDPREFERENCES를 설정하였음
     **/
    @JavascriptInterface
    public void saveDetail(String trueval) throws JSONException {
        String transone = sharedPreferences.getString("transone", "false");

        if (trueval.equals("true") && transone.equals("one")) {
            String transDto = sharedPreferences.getString("transDetail", "hi");
            Long money = sharedPreferences.getLong("updatemoney", 100L);
            Log.d("zxcvzxcv", "hi");
            Log.d("zxcvzxcv", transDto);
            Gson gson = new Gson();
            TransDto trans = gson.fromJson(transDto, TransDto.class);

            Call<Object> request = retrofitService.insrttrans(money, trans);
            editor.remove("transDetail");
            editor.remove("transone");
            editor.commit();
            request.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            });
        }
    }

    /********************************************** */
    /**
     * @brief : FIREBASE 웹 푸시알람을 보내기 위해 해당기기의 토큰값을 가져오는 메서드
     **/
    @JavascriptInterface
    public String setAndroidToken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("asdf", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        fcmToken = token;
                        String msg = token;
                        Log.d("asdf", msg);
                    }
                });
        return fcmToken;

    }

    /********************************************** */
    /**
     * @brief :   상품구매 결제정보 임시 저장 메서드
     * 프론트에서 사용자가 상품구매 결제를 진행할 때,  결제 정보를 임시로
     * SHAREDPREFERENCES에 저장한 뒤,
     * 결제가 완료되면 웹서버에 해당 정보를 가져와 저장하도록 설계함
     **/
    @JavascriptInterface
    public void setBuyHstDetails(String transDto, String mdeposmoney) {
        Toast.makeText(context, " in app=", Toast.LENGTH_SHORT).show();
        editor.putString("buyHstDetail", transDto);
        editor.putString("transone", "one");
        editor.putLong("mdeposmoney", Long.parseLong(mdeposmoney));
        editor.apply();
        Log.d("zxcvzxcv", "hello");
    }

    /********************************************** */
    /**
     * @brief : 상품구매 시 프론트에서 웹서버로 푸시알람을 요청하기위해
     * 프론트에서 해당 함수를 실행하여 상품구매 정보를 불러온다
     **/
    @JavascriptInterface
    public String getBuyHstDetails() {
        String transone = sharedPreferences.getString("transone", "false");
        String buyHst = "notone";
        if (transone.equals("one")) {
            buyHst = sharedPreferences.getString("buyHstDetail", "notone");
            Log.d("getDetails", buyHst);
        }
        return buyHst;
    }

    /***********************************************/
    /**
     * @brief : 상품구매 시 결제가 완료되면 웹서버로 요청하여 결제정보를 저장하는 로직
     * 프론트와 연동되어 작동하므로 프론트에서 다중실행을 방지 하기 위해
     * 1번만 실행되도록 SHAREDPREFERENCES를 설정하였음
     **/
    @JavascriptInterface
    public void saveBuyHstDetail(String trueval) throws JSONException {
        String transone = sharedPreferences.getString("transone", "false");

        if (trueval.equals("true") && transone.equals("one")) {
            String buyHsy = sharedPreferences.getString("transDetail", "hi");
            Long money = sharedPreferences.getLong("updatemoney", 100L);
            Log.d("zxcvzxcv", "hi");
            Log.d("zxcvzxcv", buyHsy);
            Gson gson = new Gson();
            BuyHstDto buyHsyDto = gson.fromJson(buyHsy, BuyHstDto.class);

            Call<Object> request = retrofitService.savebuydetail(money, buyHsyDto);

            editor.remove("transDetail");
            editor.remove("transone");
            editor.commit();

            request.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            });


        }
    }


}
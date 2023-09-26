package com.example.jtest1;

import static com.example.jtest1.Ipinfo.serverip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/********************************************** */

/**
 * @brief : 메인페이지
 **/
public class MainPageAvtivity extends AppCompatActivity {

    Button signupBtn;
    Button loginBtn;
    EditText idTi;
    EditText pwTi;
    MemberDto memberDto = new MemberDto();
    TokenDto tokenDto = new TokenDto();

    Retrofit retrofit;
    RetrofitService retrofitService;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_avtivity);

        sharedPreferences = getSharedPreferences("pref_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverip + "8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        signupBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        idTi = findViewById(R.id.idTi);
        pwTi = findViewById(R.id.pwTi);


        /********************************************** */
        /**
        * @brief : 회원가입 화면이동
        **/
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageAvtivity.this, SignupPageActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        /********************************************** */
        /**
         * @brief : 로그인
         *          로그인 성공 시 tokenDto에 JWT토큰, 회원정보를 저장하고
         *          웹뷰 인텐트에 전달
         *          로그인 실패 시 다이어로그 띄움
         **/
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberDto.setMembId(idTi.getText().toString());
                memberDto.setMembPwd(pwTi.getText().toString());

                Call<TokenDto> request = retrofitService.login(memberDto);
                request.enqueue(new Callback<TokenDto>() {
                    @Override
                    public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {

                        if (response.isSuccessful()) {
                            Log.d("asdfasdf", "야야야");
                            tokenDto = response.body();
                            Log.d("asdfasdf", tokenDto.getAccessToken());

                            Gson gson = new Gson();
                            String dataString = gson.toJson(tokenDto);
                            editor.putString("dataString", dataString);
                            editor.apply();

                            Intent intent = new Intent(MainPageAvtivity.this, PaymentWebViewActivity.class);
                            intent.putExtra("TokenDto", tokenDto);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainPageAvtivity.this);
                            builder.setTitle("알림")
                                    .setMessage("로그인정보가 일치하지 않습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TokenDto> call, Throwable t) {

                    }
                });

            }
        });


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }


}
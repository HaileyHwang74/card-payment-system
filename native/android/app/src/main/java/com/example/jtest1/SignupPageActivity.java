package com.example.jtest1;

import static com.example.jtest1.Ipinfo.serverip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/********************************************** */
/**
 * @brief : 회원가입페이지
 **/
public class SignupPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    Retrofit retrofit;
    RetrofitService retrofitService;

    String userchecknum;
    Boolean  checkidYn = false ;
    Boolean  checkPhoneYn =false ;
    Boolean  checkPwYn    = false;
    TextView checkPw    ;

    Button   idCheckBtn ;
    TextView checkMent ;
    EditText inputidText ;
    EditText inppwText;
    EditText inppwcheckText ;
    EditText inpName ;
    EditText inpPhone ;
    EditText inpCheckNum ;
    Button   sendSMS ;
    Button   numCheckBtn ;
    EditText inpEmail ;
    EditText inpPost ;
    EditText inpPostDetail1 ;
    EditText inpPostDetail2 ;
    Button   postFindBtn ;
    Button   signupBtn  ;
    Spinner clsSelect;
    MemberDto memberDto = new MemberDto();

    String cls = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        retrofit = new Retrofit.Builder()
                .baseUrl(serverip + "8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

      idCheckBtn     = findViewById(R.id.idCheckBtn);
      checkMent      = findViewById(R.id.checkMent);
      inputidText    = findViewById(R.id.inpId);
      inppwText      = findViewById(R.id.inpPw);
      inppwcheckText = findViewById(R.id.inpPwCheck);
      inpName        = findViewById(R.id.inpName);
      inpPhone       = findViewById(R.id.inpPhone);
      inpCheckNum    = findViewById(R.id.inpCheckNum);
      sendSMS        = findViewById(R.id.sendSmsBtn);
      numCheckBtn    = findViewById(R.id.numCheckBtn);
      inpEmail       = findViewById(R.id.inpEmail);
      inpPost        = findViewById(R.id.inpPost);
      inpPostDetail1 = findViewById(R.id.inpPostDetail1);
      inpPostDetail2 = findViewById(R.id.inpPostDetail2);
      postFindBtn    = findViewById(R.id.postFindBtn);
      signupBtn      = findViewById(R.id.signUpBtn);
      clsSelect      = findViewById(R.id.clsSelect);
      checkPw        = findViewById(R.id.checkPw);

        /********************************************** */
        /**
        * @brief : SELECT ADAPTER을 사용하여 실렉트 박스 활성화, 권한선택
        **/
        String[] cls = new String[]{"이용권한선택", "일반사용자", "판매자"};
        clsSelect.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clsSelect.setAdapter(adapter);


            idCheckBtn.setOnClickListener(new View.OnClickListener() {


                /********************************************** */
                /**
                 * @brief : ID중복체크
                 **/
                @Override
                public void onClick(View view) {
                    String inputid = inputidText.getText().toString();
                    Map<String,String> map = new HashMap<>();
                    map.put("inputid",inputid);

                    Call<Map<String, String>> request = retrofitService.getIdcheck(map);
                    request.enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                            String result = "";
                            if (response.isSuccessful()) {
                                Log.d("asdfasdf",response.toString());
                                Map<String, String> body = response.body();
                                result = body.get("sucess");
                               } else {
                                  //  checkMent.setText("error = " + response.errorBody());
                                    }
                                if (result.equals("1")) {
                                    checkMent.setText("사용가능한 ID입니다");
                                    checkidYn = true;
                                    }else {
                                        checkMent.setText("이미 등록된 ID입니다");
                                        }
                            }
                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                            checkMent.setText("fail=" + t.getLocalizedMessage());
                        }});
                    }
                });


        /********************************************** */
        /**
        * @brief : SMS 인증문자 발송
        **/
            sendSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = inpPhone.getText().toString();
                    Map<String,String> map = new HashMap<>();
                    map.put("phoneNumber",phoneNumber);
                    Call<Map<String, String>> request = retrofitService.sendsms(map);
                    request.enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                            if (response.isSuccessful()) {
                                Map<String, String> body = response.body();
                            //    checkMent.setText( body.get("result")); 번호 테스트용
                                Log.d("message", body.get("result").toString());
                                userchecknum = body.get("result");
                            }
                        }
                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        }});
                }


            });

        /********************************************** */
        /**
        * @brief : 주소찾기 API 웹뷰 호출
        **/
            postFindBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(SignupPageActivity.this, DaumPostActivity.class);
                    startActivityForResult(i,SEARCH_ADDRESS_ACTIVITY);
                }
            });


            /********************************************** */
            /**
            * @brief : 인증번호 확인 함수
            **/
            numCheckBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(SignupPageActivity.this,inpCheckNum.getText(), Toast.LENGTH_SHORT).show();

                    if (userchecknum.equals(inpCheckNum.getText().toString())){
                        Toast.makeText(SignupPageActivity.this,"인증번혹가 확인되었습니다", Toast.LENGTH_SHORT).show();
                        checkPhoneYn = true;
                    }     else {
                        Toast.makeText(SignupPageActivity.this,"인증번혹가 틀립니다", Toast.LENGTH_SHORT).show();
                        checkPhoneYn = false;
                    }
                }
            });


        /********************************************** */
        /**
        * @brief : 회원가입 API
         * **/
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputidText.getText().toString().equals("") ||
                    inppwText.getText().toString().equals("")   ||
                    inpName.getText().toString().equals("")     ||
                    inpPhone.getText().toString().equals("")    ||
                    inpEmail.getText().toString().equals("")    ||
                    inpPost.getText().toString().equals("")     ||
                    checkidYn    == false ||
                    checkPhoneYn == false ||
                    checkPwYn    == false ||
                      memberDto.getMembCls().toString().equals("이용권한선택")


                    //양식 다 맞추기 ***
                ) {
                    Toast.makeText(SignupPageActivity.this,"정보를 모두 입력 해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                memberDto.setMembId(inputidText.getText().toString());
                memberDto.setMembPwd(inputidText.getText().toString());

                Call<Object> request = retrofitService.signup(memberDto);

                request.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        if(response.isSuccessful()){
                            Toast.makeText(SignupPageActivity.this,"회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupPageActivity.this,MainPageAvtivity.class);
                                intent.putExtra("fromsignup","true");
                                startActivity(intent);
                        }else {
                            Toast.makeText(SignupPageActivity.this,"회원가입 실패", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(SignupPageActivity.this,"회원가입 실패!",Toast.LENGTH_SHORT);
                    }
                });

            }
        });

        /********************************************** */
        /**
         * @brief : 비밀번호 확인
         **/
        inppwcheckText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(inppwText.getText().toString().equals(inppwcheckText.getText().toString())){
                    checkPw.setText("비밀번호가 일치합니다");
                    checkPwYn = true;
                }else{
                    checkPw.setText("비밀번호가 일치하지 않습니다!!");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    /********************************************** */
    /**
     * @brief : 다음 주소찾기 웹뷰가 끝나면 찾은 주소값을 전달받아온다
     **/
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data1 = intent.getExtras().getString("data1");
                    if (data1 != null) {
                        inpPost.setText(data1);
                    }
                    String data2 = intent.getExtras().getString("data2");
                    if (data2 != null) {
                        inpPostDetail1.setText(data2);
                    }

                }
                break;
        }
    }


    /********************************************** */
    /**
     * @brief : 실렉트 권한선택
     **/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cls = clsSelect.getItemAtPosition(i).toString();
        Log.d("cls",cls);
        if(i==1){
            memberDto.setMembCls("ROLE_USER");
        }else{
            memberDto.setMembCls("ROLE_SELLER");
        }

        Toast.makeText(SignupPageActivity.this,"선택된 아이템 : "+memberDto.getMembCls(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
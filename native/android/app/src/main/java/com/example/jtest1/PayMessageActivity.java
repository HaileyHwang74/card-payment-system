package com.example.jtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


/********************************************** */
/**
 * @brief : 푸쉬알람 클릭시 나오는 화면
 **/
public class PayMessageActivity extends AppCompatActivity {

    EditText membId;
    EditText goodsNm;
    EditText amount;
    EditText payDate;

    /********************************************** */
    /**
     * @brief : PENDINGINTENT로 안드로이드 어느 프로그램에서도 열리어
     *          결제 정보를 가져옴
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_message);

        membId = findViewById(R.id.membId);
        goodsNm = findViewById(R.id.goodsNm);
        amount = findViewById(R.id.amount);
        payDate = findViewById(R.id.payDate);

        Intent intent = getIntent();

        membId.setText(intent.getStringExtra("membId"));
        goodsNm.setText(intent.getStringExtra("goodsNm"));
        amount.setText(intent.getStringExtra("amount"));
        payDate.setText(intent.getStringExtra("payDate"));
        Log.d("qwerqwer", "어 왔니");
        Log.d("qwerqwer", intent.getExtras().toString());

    }

}
package com.example.jtest1;

import static com.example.jtest1.Ipinfo.serverip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;


/********************************************** */
/**
 * @brief : 상품구매 웹뷰
 **/
public class PaymentWebViewActivity extends AppCompatActivity {

    WebView paymentWebView;

    public static Activity paymentWebViewActivity;

    TokenDto tokenDto = new TokenDto();
    private long backpressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        paymentWebViewActivity = PaymentWebViewActivity.this;

        getSupportActionBar().hide(); //앱바 숨기기

        Intent intent = getIntent();
        tokenDto = (TokenDto) intent.getSerializableExtra("TokenDto");

        paymentWebView = findViewById(R.id.webView);
        paymentWebView.getSettings().setJavaScriptEnabled(true);
        paymentWebView.addJavascriptInterface(new JsInterface(this, paymentWebView), "android");

        paymentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
        WebSettings webSettigs = paymentWebView.getSettings();
        webSettigs.setJavaScriptEnabled(true);
        webSettigs.setLoadWithOverviewMode(true);
        paymentWebView.loadUrl(serverip + "3000/godepositmoney");

        /********************************************** */
        /**
         * @brief : 웹뷰 alert 나오게, 커스터마이징
         **/
        paymentWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("app title")
                        .setMessage("app" + message)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("[webView]", "확인버튼 클릭");
                                result.confirm();

                            }
                        })
                        .setCancelable(false)
                        .create().show();
                //return super.onJsAlert(view, url, message, result);
                return true;
            }
        });

    }

    /********************************************** */
    /**
     * @brief : 웹뷰에서 뒤로가기 시 이벤트 설정
     *          첫페이지에서 두번 클릭 시 프로그램 종료되도록
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && paymentWebView.canGoBack()) {
            paymentWebView.goBack();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && !paymentWebView.canGoBack() && System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(PaymentWebViewActivity.this, "첫페이지 입니다 뒤로가기 두번누를 경우 종료됩니다", Toast.LENGTH_SHORT).show();
            return true;
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
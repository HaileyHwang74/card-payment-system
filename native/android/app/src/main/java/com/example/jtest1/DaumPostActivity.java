package com.example.jtest1;

import static com.example.jtest1.Ipinfo.serverip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;


/********************************************** */

/**
 * @brief : 다음 주소찾기 API
 **/

public class DaumPostActivity extends AppCompatActivity {

    private WebView browser;

    class MyJavaScriptInterface {
        /********************************************** */
        /**
         * @brief : 다음 주소찾기에서 주소 선택 시 화면에 해당 주소코드를 RETURN
         **/
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            int idx = data.indexOf(",");
            String data1 = data.substring(0, idx);
            String data2 = data.substring(idx+1);
            extra.putString("data1", data1);
            extra.putString("data2", data2);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /********************************************** */
        /**@brief : 주소찾기 버튼 클릭 시 웹서버로 요청하고 웹뷰로 다음 주소찾기 HTML을 연다,
         * **/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        browser.loadUrl(serverip + "8888/android/daum");
    }
}
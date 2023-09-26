package com.example.jtest1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/********************************************** */

/**
 * @brief :  FIREBASE 웹 푸시알람 서비스
 **/
public class FcmService extends FirebaseMessagingService {


    /********************************************** */
    /**
     * @brief : 웹서버에서 해당하는 안드로이드 기기고유 토큰값으로 푸시알람으로 보낼 시 실행됨
     * 버전이슈로 버전체크함
     **/
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getData() != null) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel_id01", "fcmservice", NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("description...");
                manager.createNotificationChannel(channel);

            }

            /********************************************** */
            /**@brief : 웹서버에서 결제정보 토대로 알림내용을 보낸다
             *          사용자가 안드로이드로 다른 프로그램을 사용중에서도 알림을 위해
             *          pendingintent를 사용
             *          intent에 웹서버에서 보내온 결제정보를 저장한 뒤 , pendingintent start함
             * **/
            Intent intent = new Intent(this, PayMessageActivity.class);
            intent.putExtra("membId", message.getData().get("membId"));
            intent.putExtra("goodsNm", message.getData().get("goodsNm"));
            Log.d("qwerqwer", message.getData().get("goodsNm"));
            intent.putExtra("amount", message.getData().get("amount").toString());
            intent.putExtra("payDate", message.getData().get("payDate"));
            PendingIntent pendingIntent
                    = PendingIntent.getActivity(this, 15, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            /********************************************** */
            /**@brief : 알림 설정
             * **/
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id01")
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("content"))
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
            manager.notify(16, builder.build());


        }

    }

    /********************************************** */
    /**
     * @brief :  ONNEWTOKEN을사용하여 FCB토큰 생성시 토큰을 저장할 수 도 있으나,
     * 알림을 보낼 때, 토큰정보를 수동적으로 가져오는 메서드를 따로 작성하였음
     **/
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("[fcmservice]", "token=" + token);

    }
}
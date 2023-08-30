//package com.lira_turkish.dollarstocks.utils.util;
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//
//import com.lira_turkish.dollarstocks.IosNotification;
//import com.lira_turkish.dollarstocks.R;
//import com.lira_turkish.dollarstocks.feature.splash.SplashActivity;
//import com.lira_turkish.dollarstocks.models.ApiResult;
//import com.lira_turkish.dollarstocks.models.MessageNotification;
//import com.lira_turkish.dollarstocks.models.NotificationData;
//import com.lira_turkish.dollarstocks.services.api.ApiClient;
//import com.lira_turkish.dollarstocks.services.api.ApiInterface;
//import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessagingPresenter;
//import com.lira_turkish.dollarstocks.utils.AppPreferences;
//import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
//
//import java.util.Objects;
//
//
//public class NotificationUtil {
//    ApiInterface service;
//
//    @SuppressLint("NewApi")
//    public void sendNotification(Context context, String title, String message, String link) {
//        String channelID = "spChannel"; // In case you don't add a channel android oreo will not create the notification and it will give you a log error
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);
//        builder.setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSmallIcon(R.drawable.ic_logo)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(Notification.PRIORITY_HIGH);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        builder.setSound(alarmSound);
//
//        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            try {
//                NotificationChannel channel = new NotificationChannel(channelID, title,
//                        NotificationManager.IMPORTANCE_HIGH);
//                channel.setLightColor(Color.GREEN);
//                notificationManager.createNotificationChannel(channel);
//                builder.setChannelId(channelID);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
//
//        Intent intent;
//        intent = new Intent(context, SplashActivity.class);
//        intent.putExtra("link", link);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        Notification notification = builder.build();
//        notification.contentIntent = PendingIntent.getActivity(context
//                , iUniqueId, intent, PendingIntent.FLAG_IMMUTABLE);
//        notificationManager.notify(iUniqueId, notification);
//
////        FirebaseMessagingPresenter.getInstance().notifyNow(title);
//
//    }
//
//    public void pushFCMNotification(Context context, String title, String message, String link) {
//        MessageNotification notification = new MessageNotification(new NotificationData(title, message), link,new IosNotification(title,message));
//        service = ApiClient.getRetrofit().create(ApiInterface.class);
//
//        Log.e(getClass().getName() + "test", notification.toString());
//
//        new APIUtil<String>(context).getData(service
//                        .sendFirebaseNotification(notification)
//                , new RequestListener<ApiResult<String>>() {
//                    @Override
//                    public void onSuccess(ApiResult<String> stringApiResult, String msg) {
//                        if (AppPreferences.getInstance(context).isNotificationOn()==true){
//                          new NotificationUtil(). sendNotification(context, Objects.requireNonNull(title), message, link);
//                                }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        Log.e("onError",msg);
//
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        Log.e("onFail",msg);
//
//                    }
//                });
//    }
//
//}
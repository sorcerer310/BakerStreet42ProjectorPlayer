/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;


import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.bsu.bk42projectorplayer.app.DeviceUtils;
import com.bsu.bk42projectorplayer.app.ProjectorPlayerActivity;


import java.util.List;

/**
 * Broadcast receiver that handles push notification messages from the server.
 * This should be registered as receiver in AndroidManifest.xml.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public final class NotificationReceiver extends BroadcastReceiver {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationReceiver.class);

    //    private NotificationService notificationService;

    public NotificationReceiver() {
    }

    //    public NotificationReceiver(NotificationService notificationService) {
    //        this.notificationService = notificationService;
    //    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_NOTIFICATION.equals(action)) {
            String notificationId = intent
                    .getStringExtra(Constants.NOTIFICATION_ID);
            String notificationApiKey = intent
                    .getStringExtra(Constants.NOTIFICATION_API_KEY);
            String notificationTitle = intent
                    .getStringExtra(Constants.NOTIFICATION_TITLE);
            String notificationMessage = intent
                    .getStringExtra(Constants.NOTIFICATION_MESSAGE);
            String notificationUri = intent
                    .getStringExtra(Constants.NOTIFICATION_URI);
            String notificationFrom = intent
                    .getStringExtra(Constants.NOTIFICATION_FROM);
            String packetId = intent
                    .getStringExtra(Constants.PACKET_ID);

            Log.d(LOGTAG, "notificationId=" + notificationId);
            Log.d(LOGTAG, "notificationApiKey=" + notificationApiKey);
            Log.d(LOGTAG, "notificationTitle=" + notificationTitle);
            Log.d(LOGTAG, "notificationMessage=" + notificationMessage);
            Log.d(LOGTAG, "notificationUri=" + notificationUri);

//            Notifier notifier = new Notifier(context);
//            notifier.notify(notificationId, notificationApiKey,
//                    notificationTitle, notificationMessage, notificationUri,notificationFrom,packetId);

            //直接转到对应的Activity
            Intent nintent = new Intent(context, ProjectorPlayerActivity.class);
            nintent.putExtra(Constants.NOTIFICATION_ID, notificationId);
            nintent.putExtra(Constants.NOTIFICATION_API_KEY, notificationApiKey);
            nintent.putExtra(Constants.NOTIFICATION_TITLE, notificationTitle);
            nintent.putExtra(Constants.NOTIFICATION_MESSAGE, notificationMessage);
            nintent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
            nintent.putExtra(Constants.NOTIFICATION_FROM, notificationFrom);
            nintent.putExtra(Constants.PACKET_ID, packetId);
            nintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(nintent);


//            //先获得视频数据
//            Map<String,String> m = Utils.parseVideoData(notificationUri);
//            //判断该视频如果已存在,就不发送,如果不存在则发送视频到手机
//            if(!Utils.isVideoExistSharedPreferences(context, notificationUri)){
//	            //如果锁屏发出通知。否则直接显示指定的Activity
//	            if(NotificationReceiver.isScreenLocked(context)){
//	            	Notifier notifier = new Notifier(context);
//	//            	notifier.notify(notificationId, notificationApiKey,
//	//                    notificationTitle, notificationMessage, notificationUri,notificationFrom,packetId);
//	            	notifier.notify(notificationId, notificationApiKey,
//	            		"贝克街42号-凶宅:"+m.get("vtitle").toString(), notificationMessage, notificationUri,notificationFrom,packetId);
//	            }else{
//
//	            	Intent nintent = new Intent(context,VideoActivity.class);
//	            	nintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//	        		nintent.putExtra("title", m.get("vtitle").toString());
//	        		nintent.putExtra("vpath", m.get("vpath").toString());
//
//	        		//向持久化数据中增加对应的数据
//	        		Utils.saveSharedPreferences(context, notificationUri);
//
//	            	context.startActivity(nintent);
//	            }
//            }
        }
    }
//    /**
//     * 判断是否处于锁屏状态
//     * @param c
//     * @return	返回ture为锁屏,返回flase为未锁屏
//     */
//    public final static boolean isScreenLocked(Context c) {
//    	android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c.getSystemService(c.KEYGUARD_SERVICE);
//    	return mKeyguardManager.inKeyguardRestrictedInputMode();
//
//    }
//    public final static boolean isAppForground(Context mContext) {
//        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
//                return false;
//            }
//        }
//        return true;
//    }
}

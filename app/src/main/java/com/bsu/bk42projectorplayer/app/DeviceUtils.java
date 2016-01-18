package com.bsu.bk42projectorplayer.app;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.WindowManager;

/**
 * 设备工具类，全部都是操作硬件设备功能的一些类
 * Created by fengchong on 16/1/18.
 */
public class DeviceUtils {

    //锁屏部分-------------------------
    //继承了设备管理器的广播类，没做任何操作
    public static class AdminReceiver extends DeviceAdminReceiver{}
    private  static DevicePolicyManager policyManager;
    private static ComponentName componentName;
    private static final int MY_REQUEST_CODE = 9999;

    /**
     * 初始化锁屏
     * @param context
     */
    public static void initLockScreen(Context context){
        policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context,DeviceUtils.AdminReceiver.class);
    }

    /**
     * 激活设备管理器，设置app为激活状态
     */
    public static void activeDeviceManager(Activity activity){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"激活一键锁屏");
        activity.startActivityForResult(intent, MY_REQUEST_CODE);
    }

    /**
     * 被Activity的onActivityResult函数调用
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(Activity activity,int requestCode,int resultCode,Intent data){
        if(requestCode==MY_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            policyManager.lockNow();
            activity.finish();
        }else{
            activity.finish();
        }
    }

    /**
     * 锁定屏幕操作
     * @param activity
     */
    public static void lockScreen(Activity activity){
        if(policyManager.isAdminActive(componentName)){
            policyManager.lockNow();
            activity.finish();
        }else{
            activeDeviceManager(activity);
        }
    }
    //锁屏部分-------------------------

    //唤醒屏幕部分----------------------
    private static KeyguardManager km;                                                                                         //键盘管理
    private static PowerManager pm;                                                                                            //电源管理
    private static PowerManager.WakeLock wakeLock;                                                                             //屏幕唤醒对象

    /**
     * 初始化唤醒屏幕
     * @param activity
     */
    public static void initWakeScrenUnlock(Activity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        wakeLock.acquire();
    }

    /**
     * 唤醒屏幕
     */
    public static void wakeScreen(Activity activity){
        //屏幕解锁
        km= (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        kl.disableKeyguard();

        //屏幕唤醒
        if(wakeLock==null) {
            pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        }
        wakeLock.acquire();
        wakeLock.release();
    }
    //唤醒屏幕部分----------------------

    //手机振动部分部分----------------------
    /**
     * 手机震动函数
     * @param activity
     * @param milliseconds
     */
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    //手机振动部分部分----------------------
}

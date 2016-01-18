package com.bsu.bk42projectorplayer.app;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;


public class ProjectorPlayerActivity extends ActionBarActivity {

    //用于处理屏幕解锁
//    private KeyguardManager km;                                                                                         //键盘管理
//    private PowerManager pm;                                                                                            //电源管理
//    private PowerManager.WakeLock wakeLock;                                                                             //屏幕唤醒对象

    public static ProjectorPlayerActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector_player);

        ProjectorPlayerActivity.instance = this;

        initAndroidpnService();
        //唤醒屏幕初始化
        DeviceUtils.initWakeScrenUnlock(this);
        //设备管理服务
        DeviceUtils.initLockScreen(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String urivalue = intent.getStringExtra(Constants.NOTIFICATION_URI);
        if(urivalue==null || urivalue.equals(""))
            return;

        DeviceUtils.vibrate(this, 500);

        DeviceUtils.wakeScreen(ProjectorPlayerActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DeviceUtils.onActivityResult(this,requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projector_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DeviceUtils.lockScreen(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化androidpn服务
     */
    private void initAndroidpnService(){
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
    }

}

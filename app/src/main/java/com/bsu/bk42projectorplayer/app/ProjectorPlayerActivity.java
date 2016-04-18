package com.bsu.bk42projectorplayer.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;



public class ProjectorPlayerActivity extends ActionBarActivity {
    private VideoView vv;
    private MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_projector_player);

        //Androidpn服务初始化
        initAndroidpnService();
        //唤醒屏幕初始化
        DeviceUtils.initWakeScrenUnlock(this);
        //设备管理服务
        DeviceUtils.initLockScreen(this);
        //初始化视频部分
        initVideoView();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        wakeAndPlay(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        wakeAndPlay(intent);
    }

    /**
     * 唤醒并播放影片
     * @param intent    唤醒时获得的intent包含要播放视频的信息
     */
    private void wakeAndPlay(Intent intent){
        String urivalue = intent.getStringExtra(Constants.NOTIFICATION_URI);
        DeviceUtils.vibrate(this, 500);
        DeviceUtils.wakeScreen(this);
        this.playVideo(urivalue);
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

    /**
     * 初始化视频播放部分
     */
    private void initVideoView(){
        vv = (VideoView) findViewById(R.id.vv);
        mc = new MediaController(this);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(900,720);      //华为分辨率
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(1920,1080);      //坚果分辨率
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(1280,720);      //投影仪分辨率分辨率
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(2048,1536);      //Android pad分辨率分辨率

        RelativeLayout.LayoutParams lp=
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

//        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        vv.setLayoutParams(lp);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        vv.setMediaController(mc);
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer arg0) {
                DeviceUtils.lockScreen(ProjectorPlayerActivity.this);
            }});


//        调试代码
        vv.setVideoURI(Uri.parse("android.resource://com.bsu.bk42projectorplayer.app/"+R.raw.pyramid));
        vv.start();

//        playVideo("pyramid");
//        调试代码
    }

    /**
     * 根据发送的消息播放视频
     * @param vpath 视频播放协议，包含要播放视频的名字
     */
    private void playVideo(String vpath){
        if(vpath==null) {
            Toast.makeText(this,"视频播放失败,地址无效",Toast.LENGTH_LONG);
            return;
        }
        String[] uri = vpath.split(":");
        if(uri.length==2 && uri[0].equals("video")) {
            vv.setVideoURI(Uri.parse("android.resource://com.bsu.bk42projectorplayer.app/"+vpath2VideoResource(uri[1])));
            vv.start();
            Toast.makeText(this,"播放视频:"+uri[1],Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this,"视频播放失败:"+vpath,Toast.LENGTH_LONG);
        }
    }

    /**
     * 根据获得的消息返回资源id
     * @param vname 字符串形式资源名
     * @return      返回系统中资源ID
     */
    private int vpath2VideoResource(String vname){
        //结局视频
//        if(vname.equals("end1")){
//            return R.raw.end1;
//        }else if(vname.equals("end2")){
//            return R.raw.end2;
//        }else{
//            return -1;
//        }

        //玄武宫视频
//        if(vname.equals("xuanwu1")){
//            return R.raw.xuanwu1;
//        }else if(vname.equals("xuanwu2")){
//            return R.raw.xuanwu2;
//        }else{
//            return -1;
//        }

        //金字塔视频
        if(vname.equals("pyramid")){
            return R.raw.pyramid;
        }else{
            return -1;
        }
    }
}


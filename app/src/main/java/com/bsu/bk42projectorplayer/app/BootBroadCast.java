package com.bsu.bk42projectorplayer.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadCast extends BroadcastReceiver {
    public BootBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //开机启动的Activity
        Intent activity=new Intent(context,ProjectorPlayerActivity.class);
        activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//不加此句会报错。
        context.startActivity(activity);

    }
}

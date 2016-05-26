package com.example.administrator.myuniversaltoolapplication.bmobutils;

import android.content.Context;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**消息接收器
 * @author smile
 * @project DemoMessageHandler
 * @date 2016-03-08-17:37
 */
public class DemoMessageHandler extends BmobIMMessageHandler {

    private Context context;

    public DemoMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
    }

}

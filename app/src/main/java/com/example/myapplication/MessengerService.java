package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * <pre>
 *     @author : dengcy
 *     @date :  2021/2/23
 *     @desc :
 * </pre>
 */
public class MessengerService extends Service {

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Log.i("quella", Objects.requireNonNull(msg.getData().getString("msg")));
                Messenger client = msg.replyTo;
                Message replyMessage = Message.obtain(null,1);
                Bundle bundle = new Bundle();
                bundle.putString("msg","hello  this is service");
                replyMessage.setData(bundle);
                try {
                    client.send(replyMessage);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            } else {
                super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}

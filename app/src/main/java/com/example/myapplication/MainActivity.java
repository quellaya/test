package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView tvText;

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null,1);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello this is client");
            msg.setData(bundle);
            msg.replyTo = messenger;
            try {
                mService.send(msg);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    private Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                Log.i("quella", Objects.requireNonNull(msg.getData().getString("msg")));
            }else {
                super.handleMessage(msg);
            }


        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







//        tvText = findViewById(R.id.tv_text);
//
//        Intent intent = new Intent(this,MessengerService.class);
//        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
//
//        final int startX = 0;
//        final int deltaX = 100;
//        final ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float fraction = animator.getAnimatedFraction();
//                tvText.scrollTo(startX + (int) (deltaX * fraction),0);
//            }
//        });
//        animator.start();
//
//        tvText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }








}

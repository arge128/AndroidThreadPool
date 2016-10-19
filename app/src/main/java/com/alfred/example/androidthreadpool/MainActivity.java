package com.alfred.example.androidthreadpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alfred.example.androidthreadpool.thread.Priority;
import com.alfred.example.androidthreadpool.thread.ThreadPoolManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button backBtn, foreBtn, serialBtn, mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backBtn = (Button) findViewById(R.id.background_btn);
        foreBtn = (Button) findViewById(R.id.foreground_btn);
        serialBtn = (Button) findViewById(R.id.serial_btn);
        mainBtn = (Button) findViewById(R.id.main_thread_btn);
        backBtn.setOnClickListener(this);
        foreBtn.setOnClickListener(this);
        serialBtn.setOnClickListener(this);
        mainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background_btn:
                ThreadPoolManager.INSTANCE.execute(backRunnable, Priority.BACKGROUND);
                break;
            case R.id.foreground_btn:
                ThreadPoolManager.INSTANCE.execute(foreRunnable, Priority.NORMAL);
                break;
            case R.id.serial_btn:
                ThreadPoolManager.INSTANCE.executeSerial(serialRunnable1);
                ThreadPoolManager.INSTANCE.executeSerial(serialRunnable2);
                break;
            case R.id.main_thread_btn:
                ThreadPoolManager.INSTANCE.executeOnMainThread(mainRunnable);
                break;
        }
    }
    private Runnable backRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + "   thread priority:" + Thread.currentThread().getPriority());
            lotsOfWork();
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + " backRunnable done");
        }
    };
    private Runnable foreRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + "   thread priority:" + Thread.currentThread().getPriority());
            lotsOfWork();
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + " foreRunnable done");
        }
    };
    private Runnable serialRunnable1 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + "   thread priority:" + Thread.currentThread().getPriority());
            littleWork();
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + " serialRunnable1 done");
        }
    };
    private Runnable serialRunnable2 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + "   thread priority:" + Thread.currentThread().getPriority());
            littleWork();
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + " serialRunnable2 done");
        }
    };
    private Runnable mainRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + "   thread priority:" + Thread.currentThread().getPriority());
            littleWork();
            Log.i(TAG, "thread name:" + Thread.currentThread().getName() + " mainRunnable done");
        }
    };

    private void lotsOfWork(){
        for (int i = 0; i < 100000000; i++) {
            Math.sqrt(i);
        }
    }
    private void littleWork(){
        for (int i = 0; i < 100; i++) {
            Math.sqrt(i);
        }
    }
}

package com.view.surfaceview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.view.surfaceview.Instant;
import com.view.surfaceview.ISurfaceViewManagerImpl;

/**
 * @author : wanghailong
 * @date :
 * @description:
 */
public class ServiceSurfaceView extends Service {

    public ServiceSurfaceView() {
    }

    @Override
    public void onCreate() {
        Log.e(Instant.TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(Instant.TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(Instant.TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(Instant.TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(Instant.TAG, "onRebind");
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(Instant.TAG, "onBind");
        return new ISurfaceViewManagerImpl().asBinder();
    }
}

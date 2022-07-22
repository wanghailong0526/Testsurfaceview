package com.view.surfaceview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.view.surfaceview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback, View.OnTouchListener {

    private com.view.surfaceview.databinding.ActivityMainBinding mViewBind;
    private ISurfaceViewManager mISurfaceViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mViewBind.getRoot());
        mViewBind.viewSurface.getHolder().addCallback(this);
        mViewBind.viewSurface.setOnTouchListener(this);
        mViewBind.viewSurface.setClickable(true);

        mViewBind.bindService.setOnClickListener(this);
        mViewBind.createSurface.setOnClickListener(this);
        mViewBind.clearSurface.setOnClickListener(this);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mISurfaceViewManager = ISurfaceViewManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_service:
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext().getPackageName(), getApplicationContext().getPackageName() + ".service.ServiceSurfaceView");
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.create_surface:
                mViewBind.viewSurface.setVisibility(View.VISIBLE);
                break;
            case R.id.clear_surface:
                surfaceClear();
                break;
            default:
                break;
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            mISurfaceViewManager.onSurfaceCreated(holder.getSurface());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        try {
            mISurfaceViewManager.onSurfaceChanged(holder.getSurface());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        try {
            mISurfaceViewManager.onSurfaceDestroyed(holder.getSurface());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void surfaceClear() {
        try {
            mISurfaceViewManager.onSurfaceClear();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            mISurfaceViewManager.sendTouchEvent(event);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

}
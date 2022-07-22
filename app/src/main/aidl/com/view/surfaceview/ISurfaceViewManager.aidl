// ISurfaceViewManager.aidl
package com.view.surfaceview;

import android.view.Surface;

import android.view.MotionEvent;

// Declare any non-default types here with import statements

interface ISurfaceViewManager {
   // SurfaceView onCreate 时调用
   void onSurfaceCreated(in Surface surface);
   // SurfaceView onChanged 时调用
   void onSurfaceChanged(in Surface surface);
   // SurfaceView onDestroyed 时调用
   void onSurfaceDestroyed(in Surface surface);
   // SurfaceView 清空内容时调用
   void onSurfaceClear();
   // 发送SurfaceView的 Touch 事件
   void sendTouchEvent(in MotionEvent event);

}
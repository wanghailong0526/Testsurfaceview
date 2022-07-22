package com.view.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;

/**
 * @author : wanghailong
 * @date :
 * @description:
 */
public class ISurfaceViewManagerImpl extends ISurfaceViewManager.Stub {
    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;
    private Surface mSurface;

    @Override
    public void onSurfaceCreated(Surface surface) throws RemoteException {
        Log.e(Instant.TAG, "surfaceCreated: ");
        // 拿到客户端Surface
        mSurface = surface;

        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);

        // 路径
        mPath = new Path();
        mPath.moveTo(0, 100);
        draw();
    }

    @Override
    public void onSurfaceChanged(Surface surface) throws RemoteException {
        Log.e(Instant.TAG, "surfaceChanged: ");
    }

    @Override
    public void onSurfaceDestroyed(Surface surface) throws RemoteException {
        Log.e(Instant.TAG, "surfaceDestroyed: ");
    }

    @Override
    public void onSurfaceClear() throws RemoteException {
        // 下面三行清空画布
        mPath = new Path();
        mPath.moveTo(0, 100);
        draw();
    }

    @Override
    public void sendTouchEvent(MotionEvent event) throws RemoteException {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                draw();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    // 绘图
    private void draw() {
        try {
            //获得canvas对象
            mCanvas = mSurface.lockCanvas(new Rect(0, 0, 1920, 500));
            Log.e(Instant.TAG, "canvas size" + mCanvas.getWidth() + "-" + mCanvas.getHeight());
            //绘制背景
            mCanvas.drawColor(Color.WHITE);

            mCanvas.drawRect(10, 0, 1900, 400, mPaint);
            //绘制路径
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                //释放canvas对象并提交画布
                mSurface.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}

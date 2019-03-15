package com.cn.xuetenglong.sawtoothview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyView extends View {

    private static final String TAG = "MyView";

    private List<PointF> mPointDatas; //放置四个数据点的集合
    private List<PointF> mPointControlls;//方式8个控制点的集合

    private int mWidth;
    private int mHeight;
    private int mCenterX;//圆的中心坐标x
    private int mCenterY;//圆的中心坐标y
    private int mCircleRadius = 150;//圆的半径
    Paint mPaint;
    Path mPath;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0.01f);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        Log.d(TAG, "宽的模式:" + widthMode);
        Log.d(TAG, "高的模式:" + heightMode);
        Log.d(TAG, "宽的尺寸:" + widthSize);
        Log.d(TAG, "高的尺寸:" + heightSize);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
            width = (int) (getPaddingLeft() + getPaddingRight());
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (getPaddingTop() + getPaddingBottom());
        }

        Log.d(TAG, "修改后的高的尺寸:" + height);
        Log.d(TAG, "修改后的宽的尺寸:" + width);
        mWidth = width;
        mHeight = height;
        //保存测量宽度和测量高度
        setMeasuredDimension(width + 10, height + 100);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "onDraw获取的高的尺寸:" + height);
        Log.d(TAG, "onDraw获取的宽的尺寸:" + width);
        int halfWidth = width / 2;
        int startPoint = halfWidth - 100;
        int endPoint = halfWidth + 100;

        drawCircle(canvas);
        mPath.lineTo(0, 0 + 50);
        mPath.lineTo(0, mHeight + 50);
        mPath.lineTo(width, mHeight + 50);
        mPath.lineTo(width, 0 + 50);
        mPath.lineTo(halfWidth + mCircleRadius, 0 + 50);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setShadowLayer(30, 0, 0, Color.GRAY);
        canvas.drawPath(mPath, mPaint);

//        mPaint.setAlpha(127);
//        //绘制填充区域
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPath(mPath, mPaint);

//        mPath.moveTo(0, 0);
//        mPath.lineTo(startPoint, 0);
//
////        mPath.rQuadTo(100, 150, 200, 0);
//
//        mPath.lineTo(width, 0);
//        mPath.lineTo(width, mHeight);
//        mPath.lineTo(0, mHeight);
//        mPath.lineTo(0, mHeight);
//        mPath.close();

//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawPath(mPath, mPaint);
//
//        mPaint.setAlpha(127);
//        //绘制填充区域
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawPath(mPath, mPaint);
    }

    public void drawCircle(Canvas canvas) {
        mPointDatas = new ArrayList<>();
        mPointControlls = new ArrayList<>();

        mCenterX = mWidth / 2;
        mCenterY = 0;
//        mPointDatas.add(new PointF(mCenterX, mCenterY - mCircleRadius));
//        mPointDatas.add(new PointF(mCenterX + mCircleRadius, mCenterY));
        mPointDatas.add(new PointF(mCenterX + mCircleRadius, 50));
        mPointDatas.add(new PointF(mCenterX, mCircleRadius + 50));
        mPointDatas.add(new PointF(mCenterX - mCircleRadius, 50));


        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY + mCircleRadius / 2 + 50));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY + mCircleRadius + 50));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY + mCircleRadius + 50));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY + mCircleRadius / 2 + 50));

        //利用三阶贝塞尔曲线实现画圆
        mPath.moveTo(mPointDatas.get(0).x, mPointDatas.get(0).y);
        mPath.cubicTo(mPointControlls.get(0).x, mPointControlls.get(0).y, mPointControlls.get(1).x, mPointControlls.get(1).y, mPointDatas.get(1).x, mPointDatas.get(1).y);
//        //绘制
//        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(mPointDatas.get(1).x, mPointDatas.get(1).y);
        mPath.cubicTo(mPointControlls.get(2).x, mPointControlls.get(2).y, mPointControlls.get(3).x, mPointControlls.get(3).y, mPointDatas.get(2).x, mPointDatas.get(2).y);
//
//        //绘制
//        canvas.drawPath(mPath, mPaint);
    }
}

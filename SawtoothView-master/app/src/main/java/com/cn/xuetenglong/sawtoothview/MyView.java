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
    private int littleRadius = 50;//小圆弧的半径
    private int paddinngTop = 20;
    private int paddinngBottom = 20;
    private int paddinngleft = 20;
    private int paddinngRight = 20;
    Paint mPaint;
    Path mPath;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);
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
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "onDraw获取的高的尺寸:" + height);
        Log.d(TAG, "onDraw获取的宽的尺寸:" + width);
        int halfWidth = width / 2;


        drawCircle(canvas);
        mPath.lineTo(paddinngleft + littleRadius, paddinngTop);

        mPath.lineTo(0 + paddinngleft, 0 + paddinngTop + littleRadius);
        mPath.lineTo(0 + paddinngleft, mHeight - paddinngBottom - littleRadius);

        mPath.lineTo(0 + paddinngleft + littleRadius, mHeight - paddinngBottom);
        mPath.lineTo(width - paddinngRight, mHeight - paddinngBottom);
        mPath.lineTo(width - paddinngRight, 0 + paddinngTop);
        mPath.lineTo(halfWidth + mCircleRadius, 0 + paddinngTop);
        drawTopLeftArc();
        drawBottomLeftArc();

//        drawBottomLeftArc();
//        drawBottomRightArc();
//        drawTopRightArc();

        mPaint.setDither(true);
        mPaint.setAlpha(125);
        mPaint.setStyle(Paint.Style.FILL);
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

    public void drawTopLeftArc() {
        List<PointF> pointDatas = new ArrayList<>();
        List<PointF> pointControlls = new ArrayList<>();

        pointDatas.add(new PointF(paddinngleft, paddinngTop + littleRadius));
        pointDatas.add(new PointF(paddinngleft + littleRadius, paddinngTop));

        pointControlls.add(new PointF(paddinngleft, paddinngTop + littleRadius / 2));
        pointControlls.add(new PointF(paddinngleft + littleRadius / 2, paddinngTop));

        mPath.moveTo(pointDatas.get(1).x, pointDatas.get(1).y);
        mPath.cubicTo(pointControlls.get(1).x, pointControlls.get(1).y, pointControlls.get(0).x, pointControlls.get(0).y, pointDatas.get(0).x, pointDatas.get(0).y);
    }

    public void drawBottomLeftArc() {
        List<PointF> pointDatas = new ArrayList<>();
        List<PointF> pointControlls = new ArrayList<>();

        pointDatas.add(new PointF(paddinngleft, mHeight - paddinngBottom - littleRadius));
        pointDatas.add(new PointF(paddinngleft + littleRadius, mHeight - paddinngBottom));

        pointControlls.add(new PointF(paddinngleft, mHeight - paddinngBottom - littleRadius / 2));
        pointControlls.add(new PointF(paddinngleft + littleRadius / 2, mHeight - paddinngBottom));

        mPath.moveTo(pointDatas.get(0).x, pointDatas.get(0).y);
        mPath.cubicTo(pointControlls.get(0).x, pointControlls.get(0).y, pointControlls.get(1).x, pointControlls.get(1).y, pointDatas.get(1).x, pointDatas.get(1).y);
    }

    public void drawBottomRightArc() {
        List<PointF> pointDatas = new ArrayList<>();
        List<PointF> pointControlls = new ArrayList<>();

        pointDatas.add(new PointF(mWidth - paddinngRight - littleRadius, mHeight - paddinngBottom));
        pointDatas.add(new PointF(mWidth - paddinngRight, mHeight - paddinngBottom - littleRadius));

        pointControlls.add(new PointF(mWidth - paddinngRight - littleRadius / 2, mHeight - paddinngBottom));
        pointControlls.add(new PointF(mWidth - paddinngRight, mHeight - paddinngBottom - littleRadius / 2));

        mPath.moveTo(pointDatas.get(0).x, pointDatas.get(0).y);
        mPath.cubicTo(pointControlls.get(0).x, pointControlls.get(0).y, pointControlls.get(1).x, pointControlls.get(1).y, pointDatas.get(1).x, pointDatas.get(1).y);
    }

    public void drawTopRightArc() {
        List<PointF> pointDatas = new ArrayList<>();
        List<PointF> pointControlls = new ArrayList<>();

        pointDatas.add(new PointF(mWidth - paddinngRight, paddinngTop + littleRadius));
        pointDatas.add(new PointF(mWidth - paddinngRight - littleRadius, paddinngTop));

        pointControlls.add(new PointF(mWidth - paddinngRight, paddinngTop + littleRadius / 2));
        pointControlls.add(new PointF(mWidth - paddinngRight - littleRadius / 2, paddinngTop));

        mPath.moveTo(pointDatas.get(0).x, pointDatas.get(0).y);
        mPath.cubicTo(pointControlls.get(0).x, pointControlls.get(0).y, pointControlls.get(1).x, pointControlls.get(1).y, pointDatas.get(1).x, pointDatas.get(1).y);
    }


    public void drawCircle(Canvas canvas) {
        mPointDatas = new ArrayList<>();
        mPointControlls = new ArrayList<>();

        mCenterX = mWidth / 2;
        mCenterY = 0;
//        mPointDatas.add(new PointF(mCenterX, mCenterY - mCircleRadius));
//        mPointDatas.add(new PointF(mCenterX + mCircleRadius, mCenterY));
        mPointDatas.add(new PointF(mCenterX + mCircleRadius, paddinngTop));
        mPointDatas.add(new PointF(mCenterX, mCircleRadius + paddinngTop));
        mPointDatas.add(new PointF(mCenterX - mCircleRadius, paddinngTop));


        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY + mCircleRadius / 2 + paddinngTop));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY + mCircleRadius + paddinngTop));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY + mCircleRadius + paddinngTop));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY + mCircleRadius / 2 + paddinngTop));

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

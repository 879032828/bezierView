package com.cn.xuetenglong.sawtoothview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class circle extends View {

    private int mCenterX;
    private int mCenterY;
    private Paint mPaint;

    private Paint mPaintCircle;
    private Paint mPaintPoint;
    private int mCircleRadius;
    private List<PointF> mPointDatas; //放置四个数据点的集合
    private List<PointF> mPointControlls;//方式8个控制点的集合

    public circle(Context context) {
        super(context);
    }

    public circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.RED);
        mPaintCircle.setStrokeWidth(10);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setAntiAlias(true);

        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.BLACK);
        mPaintPoint.setStrokeWidth(5);
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setAntiAlias(true);

        mCircleRadius = 150;
    }

    public circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        //初始化坐标系
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        mPointDatas = new ArrayList<>();
        mPointControlls = new ArrayList<>();

        mPointDatas.add(new PointF(mCenterX, mCenterY - mCircleRadius));
        mPointDatas.add(new PointF(mCenterX + mCircleRadius, mCenterY));
        mPointDatas.add(new PointF(mCenterX, mCenterY + mCircleRadius));
        mPointDatas.add(new PointF(mCenterX - mCircleRadius, mCenterY));

        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY - mCircleRadius));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY - mCircleRadius / 2));

        mPointControlls.add(new PointF(mCenterX + mCircleRadius, mCenterY + mCircleRadius / 2));
        mPointControlls.add(new PointF(mCenterX + mCircleRadius / 2, mCenterY + mCircleRadius));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY + mCircleRadius));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY + mCircleRadius / 2));

        mPointControlls.add(new PointF(mCenterX - mCircleRadius, mCenterY - mCircleRadius / 2));
        mPointControlls.add(new PointF(mCenterX - mCircleRadius / 2, mCenterY - mCircleRadius));


        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //绘制x，y轴坐标系
        canvas.drawLine(mCenterX, 0, mCenterX, getHeight(), mPaint);
        canvas.drawLine(0, mCenterY, getWidth(), mCenterY, mPaint);
        canvas.restore();

        //绘制数据点
        for (int i = 0; i < mPointDatas.size(); i++) {
            canvas.drawPoint(mPointDatas.get(i).x, mPointDatas.get(i).y, mPaintPoint);
        }

        //绘制控制点
        for (int i = 0; i < mPointControlls.size(); i++) {
            canvas.drawPoint(mPointControlls.get(i).x, mPointControlls.get(i).y, mPaintPoint);
        }

        //利用三阶贝塞尔曲线实现画圆
        Path path = new Path();
        path.moveTo(mPointDatas.get(0).x, mPointDatas.get(0).y);
        path.cubicTo(mPointControlls.get(0).x, mPointControlls.get(0).y, mPointControlls.get(1).x, mPointControlls.get(1).y, mPointDatas.get(1).x, mPointDatas.get(1).y);

//        //绘制
        canvas.drawPath(path, mPaintCircle);
    }

}

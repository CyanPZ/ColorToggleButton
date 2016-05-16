package com.cpz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cpz.togglebutton.R;

/**
 * Created by cpzzzz on 5/9/2016.
 */
public class ToggleButton extends View
{

    private boolean mState;
    private int mDefaultWidth = 54;
    private int mDefaultHeight = 27;
    private int mLineWidth = 7;
    private int mSliderOnColor;
    private int mSliderOffColor;
    private int currentColor;
    private int currentX;
    private boolean mIsMoving = false;
    private OnToggleStateChangeListener mOnClickListener = null;

    public interface OnToggleStateChangeListener
    {
        void onToggleStateChange(boolean state);
    }

    public ToggleButton(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        mState = true;
        mSliderOnColor = getResources().getColor(R.color.colorToggleOn);
        mSliderOffColor = getResources().getColor(R.color.colorToggleOff);
        if (mState)
            currentColor = mSliderOnColor;
        else
            currentColor = mSliderOffColor;
    }

    public ToggleButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * 设置高度
     * @param height
     */
    public void setHeight(int height)
    {
        mDefaultHeight = height;
        invalidate();
    }

    /**
     * 设置宽度
     *
     * @param width
     */
    public void setWidth(int width)
    {
        mDefaultWidth = width;
        invalidate();
    }

    /**
     * 设置点击监听
     *
     * @param listener
     */
    public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener)
    {
        mOnClickListener = listener;
    }

    /**
     * 设置滑动开关状态
     *
     * @param state
     */
    public void setToggleState(boolean state)
    {
        mState = state;
        invalidate();
    }


    /**
     * 设置开关打开滑块颜色
     *
     * @param colorId
     */
    public void setSliderOnColor(int colorId)
    {
        mSliderOnColor = getResources().getColor(colorId);
        invalidate();
    }

    /**
     * 设置开关关闭滑块颜色
     *
     * @param colorId
     */
    public void setSliderOffColor(int colorId)
    {
        mSliderOffColor = getResources().getColor(colorId);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(mTBBackground.getWidth(), mTBBackground.getHeight());
        setMeasuredDimension(mDefaultWidth, mDefaultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        mPaint.setColor(currentColor);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setAntiAlias(true);
        canvas.drawLine(0, mDefaultHeight / 2, mDefaultWidth, mDefaultHeight / 2, mPaint);
        mPaint.setStrokeWidth(0);
        canvas.drawLine(0, mDefaultHeight / 2, mDefaultWidth, mDefaultHeight / 2, mPaint);
        if (mIsMoving)
        {
            if (mState)
            {
                mPaint.setColor(mSliderOffColor);
                mPaint.setStrokeWidth(mLineWidth);
                canvas.drawLine(currentX, mDefaultHeight / 2, mDefaultWidth, mDefaultHeight / 2, mPaint);
            }
            else
            {
                mPaint.setColor(mSliderOnColor);
                mPaint.setStrokeWidth(mLineWidth);
                canvas.drawLine(0, mDefaultHeight / 2, currentX, mDefaultHeight / 2, mPaint);
            }
            mPaint.setColor(mSliderOnColor);
            canvas.drawCircle(currentX, mDefaultHeight / 2, mDefaultHeight / 2, mPaint);

        }
        else
        {
            if (mState)
            {
                mPaint.setColor(currentColor);
                canvas.drawCircle(mDefaultWidth - mDefaultHeight / 2,
                        mDefaultHeight / 2, mDefaultHeight / 2, mPaint);
            }
            else
            {
                mPaint.setColor(currentColor);
                canvas.drawCircle(mDefaultHeight / 2, mDefaultHeight / 2,
                        mDefaultHeight / 2, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        currentX = (int) event.getX();
        if (currentX <= mDefaultHeight / 2)
            currentX = mDefaultHeight / 2;
        if (currentX >= mDefaultWidth - mDefaultHeight / 2)
            currentX = mDefaultWidth - mDefaultHeight / 2;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mIsMoving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mIsMoving = false;
                if (currentX < mDefaultWidth / 2)
                {
                    mState = false;
                    currentColor = mSliderOffColor;

                }
                else
                {
                    mState = true;
                    currentColor = mSliderOnColor;
                }
                if (mOnClickListener != null)
                    mOnClickListener.onToggleStateChange(mState);
                break;
        }
        invalidate();
        return true;
    }
}

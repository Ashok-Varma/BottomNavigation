package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see AppCompatTextView
 * @since 23 Jun 2017
 */
public class BadgeTextView extends AppCompatTextView {

    private ShapeBadgeItem mShapeBadgeItem;

    private boolean mAreDimensOverridden;
    private int mDesiredWidth = 100;
    private int mDesiredHeight = 100;

    public BadgeTextView(Context context) {
        this(context, null);
    }

    public BadgeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // method stub
    }

    void clearPrevious() {
        mAreDimensOverridden = false;
        mShapeBadgeItem = null;
    }

    void setShapeBadgeItem(ShapeBadgeItem shapeBadgeItem) {
        mShapeBadgeItem = shapeBadgeItem;
    }

    void setDimens(int width, int height) {
        mAreDimensOverridden = true;
        mDesiredWidth = width;
        mDesiredHeight = height;
        requestLayout();
    }

    void recallOnDraw() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShapeBadgeItem != null) {
            mShapeBadgeItem.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAreDimensOverridden) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            int width;
            int height;

            //Measure Width
            if (widthMode == MeasureSpec.EXACTLY) {
                //Must be this size
                width = widthSize;
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //Can't be bigger than...
                width = Math.min(mDesiredWidth, widthSize);
            } else {
                //Be whatever you want
                width = mDesiredWidth;
            }

            //Measure Height
            if (heightMode == MeasureSpec.EXACTLY) {
                //Must be this size
                height = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                //Can't be bigger than...
                height = Math.min(mDesiredHeight, heightSize);
            } else {
                //Be whatever you want
                height = mDesiredHeight;
            }

            //MUST CALL THIS
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

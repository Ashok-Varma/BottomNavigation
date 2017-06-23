package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see
 * @since 23 Jun 2017
 */
public class ShapeBadgeItem extends BadgeItem<ShapeBadgeItem> {

    public static final int SHAPE_OVAL = 0;
    public static final int SHAPE_RECTANGLE = 1;
    //    public static final int SHAPE_HEART = 2;
    public static final int SHAPE_STAR_3_VERTICES = 3;
    public static final int SHAPE_STAR_4_VERTICES = 4;
    public static final int SHAPE_STAR_5_VERTICES = 5;
    public static final int SHAPE_STAR_6_VERTICES = 6;

    @IntDef({SHAPE_OVAL, SHAPE_RECTANGLE, SHAPE_STAR_3_VERTICES, SHAPE_STAR_4_VERTICES, SHAPE_STAR_5_VERTICES, SHAPE_STAR_6_VERTICES})
    @Retention(RetentionPolicy.SOURCE)
    @interface Shape {
    }

    private
    @Shape
    int mShape = SHAPE_OVAL;

    private String mShapeColorCode;
    private int mShapeColor = Color.RED;

    // init values set at bindToBottomTabInternal
    private int mHeightInPixels;
    private int mWidthInPixels;
    private int mEdgeMarginInPx;

    private RectF mCanvasRect = new RectF();
    private Paint mCanvasPaint;
    private Path mPath = new Path();// used for pathDrawables

    public ShapeBadgeItem() {
        mCanvasPaint = new Paint();
        mCanvasPaint.setColor(mShapeColor);
        // If stroke needed
//            paint.setStrokeWidth(widthInPx);
//            paint.setStyle(Paint.Style.STROKE);
        mCanvasPaint.setAntiAlias(true);
        mCanvasPaint.setStyle(Paint.Style.FILL);
    }

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    ///////////////////////////////////////////////////////////////////////////

    public ShapeBadgeItem setShape(@Shape int shape) {
        this.mShape = shape;
        refreshDraw();
        return this;
    }

    /**
     * @param context       to get color
     * @param colorResource resource for background color
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setShapeColorResource(Context context, @ColorRes int colorResource) {
        this.mShapeColor = ContextCompat.getColor(context, colorResource);
        refreshColor();
        return this;
    }

    /**
     * @param colorCode color code for background color
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setShapeColor(@Nullable String colorCode) {
        this.mShapeColorCode = colorCode;
        refreshColor();
        return this;
    }

    /**
     * @param color background color
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setShapeColor(int color) {
        this.mShapeColor = color;
        refreshColor();
        return this;
    }

    /**
     * @param context    to convert dp to pixel
     * @param heightInDp dp size for height of badge item
     * @param widthInDp  dp size for width of badge item
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setSizeInDp(Context context, int heightInDp, int widthInDp) {
        mHeightInPixels = Utils.dp2px(context, heightInDp);
        mWidthInPixels = Utils.dp2px(context, widthInDp);
        if (isWeakReferenceValid()) {
            getTextView().get().setDimens(mWidthInPixels, mHeightInPixels);
        }
        return this;
    }

    /**
     * @param heightInPx pixel size for height of badge item
     * @param widthInPx  pixel size for width of badge item
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setSizeInPixels(int heightInPx, int widthInPx) {
        mHeightInPixels = heightInPx;
        mWidthInPixels = widthInPx;
        if (isWeakReferenceValid()) {
            getTextView().get().setDimens(mWidthInPixels, mHeightInPixels);
        }
        return this;
    }

    /**
     * @param context        to convert dp to pixel
     * @param edgeMarginInDp dp size for margin of badge item
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setEdgeMarginInDp(Context context, int edgeMarginInDp) {
        mEdgeMarginInPx = Utils.dp2px(context, edgeMarginInDp);
        refreshMargin();
        return this;
    }

    /**
     * @param edgeMarginInPx pixel size for margin of badge item
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setEdgeMarginInPixels(int edgeMarginInPx) {
        mEdgeMarginInPx = edgeMarginInPx;
        refreshMargin();
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Library internal methods
    ///////////////////////////////////////////////////////////////////////////

    void draw(Canvas canvas) {
        mCanvasRect.set(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        if (mShape == SHAPE_RECTANGLE) {
            canvas.drawRect(mCanvasRect, mCanvasPaint);
        } else if (mShape == SHAPE_OVAL) {
            canvas.drawOval(mCanvasRect, mCanvasPaint);
        } else if (mShape == SHAPE_STAR_3_VERTICES || mShape == SHAPE_STAR_4_VERTICES || mShape == SHAPE_STAR_5_VERTICES || mShape == SHAPE_STAR_6_VERTICES) {
            drawStar(canvas, mCanvasPaint, mShape);
        }
    }

    @Override
    ShapeBadgeItem getSubInstance() {
        return this;
    }

    @Override
    void bindToBottomTabInternal(ShapeBadgeItem badgeItem, BottomNavigationTab bottomNavigationTab) {
        if (mHeightInPixels == 0)
            mHeightInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12);
        if (mWidthInPixels == 0)
            mWidthInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12);
        if (mEdgeMarginInPx == 0)
            mEdgeMarginInPx = Utils.dp2px(bottomNavigationTab.getContext(), 4);

        refreshMargin();

        bottomNavigationTab.badgeView.setShapeBadgeItem(this);

        bottomNavigationTab.badgeView.setDimens(mWidthInPixels, mHeightInPixels);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Class only access methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return shape color
     */
    private int getShapeColor() {
        if (!TextUtils.isEmpty(mShapeColorCode)) {
            return Color.parseColor(mShapeColorCode);
        } else {
            return mShapeColor;
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    private void refreshColor() {
        mCanvasPaint.setColor(getShapeColor());
        refreshDraw();
    }

    private void refreshDraw() {
        mCanvasPaint.setColor(getShapeColor());
        if (isWeakReferenceValid()) {
            getTextView().get().recallOnDraw();
        }
    }

    private void refreshMargin() {
        if (isWeakReferenceValid()) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getTextView().get().getLayoutParams();
            layoutParams.bottomMargin = mEdgeMarginInPx;
            layoutParams.topMargin = mEdgeMarginInPx;
            layoutParams.rightMargin = mEdgeMarginInPx;
            layoutParams.leftMargin = mEdgeMarginInPx;
            getTextView().get().setLayoutParams(layoutParams);
        }
    }

    private void drawStar(Canvas canvas, Paint paint, int numOfPt) {
        double section = 2.0 * Math.PI / numOfPt;

        float x = (float) canvas.getWidth() / 2.0f;
        float y = (float) canvas.getHeight() / 2.0f;
        float radius, innerRadius;

        if (canvas.getWidth() > canvas.getHeight()) {
            radius = canvas.getHeight() * 0.5f;
            innerRadius = canvas.getHeight() * 0.25f;
        } else {
            radius = canvas.getWidth() * 0.5f;
            innerRadius = canvas.getWidth() * 0.25f;
        }


        mPath.reset();

        mPath.moveTo(
                (float) (x + radius * Math.cos(0)),
                (float) (y + radius * Math.sin(0)));
        mPath.lineTo(
                (float) (x + innerRadius * Math.cos(0 + section / 2.0)),
                (float) (y + innerRadius * Math.sin(0 + section / 2.0)));

        for (int i = 1; i < numOfPt; i++) {
            mPath.lineTo(
                    (float) (x + radius * Math.cos(section * i)),
                    (float) (y + radius * Math.sin(section * i)));
            mPath.lineTo(
                    (float) (x + innerRadius * Math.cos(section * i + section / 2.0)),
                    (float) (y + innerRadius * Math.sin(section * i + section / 2.0)));
        }

        mPath.close();

        canvas.drawPath(mPath, paint);
    }
}

package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ashokvarma.bottomnavigation.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see BadgeItem
 * @since 23 Jun 2017
 */
public class ShapeBadgeItem extends BadgeItem<ShapeBadgeItem> {

    public static final int SHAPE_OVAL = 0;
    public static final int SHAPE_RECTANGLE = 1;
    public static final int SHAPE_HEART = 2;
    public static final int SHAPE_STAR_3_VERTICES = 3;
    public static final int SHAPE_STAR_4_VERTICES = 4;
    public static final int SHAPE_STAR_5_VERTICES = 5;
    public static final int SHAPE_STAR_6_VERTICES = 6;

    @IntDef({SHAPE_OVAL, SHAPE_RECTANGLE, SHAPE_HEART, SHAPE_STAR_3_VERTICES, SHAPE_STAR_4_VERTICES, SHAPE_STAR_5_VERTICES, SHAPE_STAR_6_VERTICES})
    @Retention(RetentionPolicy.SOURCE)
    @interface Shape {
    }

    private
    @Shape
    int mShape = SHAPE_STAR_5_VERTICES;

    private String mShapeColorCode;
    private int mShapeColorResource;
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

    /**
     * @param shape new shape that needs to be drawn
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setShape(@Shape int shape) {
        this.mShape = shape;
        refreshDraw();
        return this;
    }

    /**
     * @param colorResource resource for background color
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setShapeColorResource(@ColorRes int colorResource) {
        this.mShapeColorResource = colorResource;
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

    /**
     * draw's specified shape
     *
     * @param canvas on which shape has to be drawn
     */
    void draw(Canvas canvas) {
        mCanvasRect.set(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        switch (mShape) {
            case SHAPE_RECTANGLE:
                canvas.drawRect(mCanvasRect, mCanvasPaint);
                break;
            case SHAPE_OVAL:
                canvas.drawOval(mCanvasRect, mCanvasPaint);
                break;
            case SHAPE_STAR_3_VERTICES:
            case SHAPE_STAR_4_VERTICES:
            case SHAPE_STAR_5_VERTICES:
            case SHAPE_STAR_6_VERTICES:
                drawStar(canvas, mShape);
                break;
            case SHAPE_HEART:
                drawHeart(canvas);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    ShapeBadgeItem getSubInstance() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void bindToBottomTabInternal(BottomNavigationTab bottomNavigationTab) {
        if (mHeightInPixels == 0)
            mHeightInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12);
        if (mWidthInPixels == 0)
            mWidthInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12);
        if (mEdgeMarginInPx == 0)
            mEdgeMarginInPx = Utils.dp2px(bottomNavigationTab.getContext(), 4);

        refreshMargin();
        refreshColor();// so that user set color will be updated

        bottomNavigationTab.badgeView.setShapeBadgeItem(this);

        bottomNavigationTab.badgeView.setDimens(mWidthInPixels, mHeightInPixels);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Class only access methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return shape color
     */
    private int getShapeColor(Context context) {
        if (this.mShapeColorResource != 0) {
            return ContextCompat.getColor(context, mShapeColorResource);
        } else if (!TextUtils.isEmpty(mShapeColorCode)) {
            return Color.parseColor(mShapeColorCode);
        } else {
            return mShapeColor;
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * refresh's paint color if set and redraw's shape with new color
     */
    private void refreshColor() {
        if (isWeakReferenceValid()) {
            mCanvasPaint.setColor(getShapeColor(getTextView().get().getContext()));
        }
        refreshDraw();
    }

    /**
     * notifies BadgeTextView to invalidate so it will draw again and redraws shape
     */
    private void refreshDraw() {
        if (isWeakReferenceValid()) {
            getTextView().get().recallOnDraw();
        }
    }

    /**
     * refresh's margin if set
     */
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

    /**
     * @param canvas  on which star needs to be drawn
     * @param numOfPt no of points a star should have
     */
    private void drawStar(Canvas canvas, int numOfPt) {
        double section = 2.0 * Math.PI / numOfPt;
        double halfSection = section / 2.0d;
        double antiClockRotation = getStarAntiClockRotationOffset(numOfPt);

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
                (float) (x + radius * Math.cos(0 - antiClockRotation)),
                (float) (y + radius * Math.sin(0 - antiClockRotation)));
        mPath.lineTo(
                (float) (x + innerRadius * Math.cos(0 + halfSection - antiClockRotation)),
                (float) (y + innerRadius * Math.sin(0 + halfSection - antiClockRotation)));

        for (int i = 1; i < numOfPt; i++) {
            mPath.lineTo(
                    (float) (x + radius * Math.cos(section * i - antiClockRotation)),
                    (float) (y + radius * Math.sin(section * i - antiClockRotation)));
            mPath.lineTo(
                    (float) (x + innerRadius * Math.cos(section * i + halfSection - antiClockRotation)),
                    (float) (y + innerRadius * Math.sin(section * i + halfSection - antiClockRotation)));
        }

        mPath.close();

        canvas.drawPath(mPath, mCanvasPaint);
    }

    /**
     * offset to make star shape look straight
     *
     * @param numOfPt no of points a star should have
     */
    private double getStarAntiClockRotationOffset(int numOfPt) {
        if (numOfPt == 5) {
            return 2.0 * Math.PI / 20.0d; // quarter of (section angle for 5 point star) = 36 degrees
        } else if (numOfPt == 6) {
            return 2.0 * Math.PI / 12.0d; // half the (section angle for 6 point star) = 60 degrees
        }
        return 0;
    }

    private void drawHeart(Canvas canvas) {
        mPath.reset();
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        // Starting point
        mPath.moveTo(width / 2, height / 5);

        // Upper left path
        mPath.cubicTo(5 * width / 14, 0,
                0, height / 15,
                width / 28, 2 * height / 5);

        // Lower left path
        mPath.cubicTo(width / 14, 2 * height / 3,
                3 * width / 7, 5 * height / 6,
                width / 2, height);

        // Lower right path
        mPath.cubicTo(4 * width / 7, 5 * height / 6,
                13 * width / 14, 2 * height / 3,
                27 * width / 28, 2 * height / 5);

        // Upper right path
        mPath.cubicTo(width, height / 15,
                9 * width / 14, 0,
                width / 2, height / 5);

        mPath.close();

        canvas.drawPath(mPath, mCanvasPaint);
    }
}

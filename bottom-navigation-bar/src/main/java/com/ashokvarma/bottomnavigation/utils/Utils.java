package com.ashokvarma.bottomnavigation.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Class description : These are common utils and can be used for other projects as well
 *
 * @author ashokvarma
 * @version 1.0
 * @since 19 Mar 2016
 */
public class Utils {

    private Utils() {}

    /**
     * @param context used to get system services
     * @return screenWidth in pixels
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * This method can be extended to get all android attributes color, string, dimension ...etc
     *
     * @param context          used to fetch android attribute
     * @param androidAttribute attribute codes like R.attr.colorAccent
     * @return in this case color of android attribute
     */
    public static int fetchContextColor(Context context, int androidAttribute) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttribute});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    /**
     * @param context used to fetch display metrics
     * @param dp      dp value
     * @return pixel value
     */
    public static int dp2px(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return Math.round(px);
    }
}

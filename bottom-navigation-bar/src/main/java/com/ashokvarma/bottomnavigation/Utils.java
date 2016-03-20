package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @since 19 Mar 2016
 */
class Utils {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int fetchContextColor(Context context, int androidAttrbuite) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttrbuite});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
}

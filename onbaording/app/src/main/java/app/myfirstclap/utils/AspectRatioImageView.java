package app.myfirstclap.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by admin on 31/12/2017.
 */

public class AspectRatioImageView extends AppCompatImageView {

    public AspectRatioImageView(Context context) {
        super(context);
        this.setScaleType(ScaleType.FIT_XY);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setScaleType(ScaleType.FIT_XY);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();

        if (d != null && d.getIntrinsicWidth() > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (width <= 0)
                width = getLayoutParams().width;

            int height = width * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(width, height);
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
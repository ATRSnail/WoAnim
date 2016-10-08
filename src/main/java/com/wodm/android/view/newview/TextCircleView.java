package com.wodm.android.view.newview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wodm.R;

/**
 * Created by ATRSnail on 2016/10/8.
 */

public class TextCircleView extends View {
    public TextCircleView(Context context) {
        this(context, null, 0);
    }

    public TextCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private Paint paint;

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private int width = 0;
    private int height = 0;
    private float radius = 0f;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        radius = Math.min(width / 2, height / 2);
    }

    private int textWidth = 0;
    private int textHeight = 0;
    private String textStr = "LV1";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        Rect rect = new Rect();
        paint.setTextSize(14);
        paint.setColor(Color.parseColor("#333333"));
        paint.getTextBounds(textStr, 0, textStr.length(), rect);
        textHeight = rect.height();
        textWidth = rect.width();
        canvas.drawText(textStr, (width - textWidth) / 2, (height + textHeight) / 2, paint);


    }
}

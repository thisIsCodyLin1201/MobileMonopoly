package com.example.mobilemonopoly;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SpotDrawingView extends View {
    private List<PointF> bottomSpots = new ArrayList<>();
    private List<PointF> topSpots = new ArrayList<>();
    private Paint paint;

    public SpotDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    public void addSpot(float x, float y, boolean isTopImage) {
        if (isTopImage) {
            topSpots.add(new PointF(x, y));
        } else {
            bottomSpots.add(new PointF(x, y));
        }
        invalidate(); // 重新繪製
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 繪製 Bottom Image 的點
        paint.setColor(Color.RED);
        for (PointF point : bottomSpots) {
            canvas.drawCircle(point.x, point.y, 50, paint);
        }

        // 繪製 Top Image 的點
        paint.setColor(Color.BLUE);
        for (PointF point : topSpots) {
            canvas.drawCircle(point.x, point.y, 50, paint);
        }
    }
}

package com.example.lenovo.timescroller.View;

import android.animation.TypeEvaluator;
import android.graphics.PointF;


/**
 * Created by kevin.tian on 2016/9/26.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mPointF;

    public BezierEvaluator(PointF pointF) {
        this.mPointF = pointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return calculateBezierPointForQuadratic(fraction, startValue, mPointF, endValue);
    }

    private PointF calculateBezierPointForQuadratic(float t, PointF p0, PointF p1, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
        point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
        return point;
    }
}

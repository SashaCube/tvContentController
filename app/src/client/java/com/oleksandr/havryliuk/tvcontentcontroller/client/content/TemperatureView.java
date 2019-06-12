package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class TemperatureView extends View {


    private int topPartColor, bottomPartColor, separatorColor, textColor,
            startValue, endValue, maxValue, minValue, separatorWidth;
    private boolean showStartValue, showEndValue, showSeparator;
    int height, width, start, end;
    float textSize;

    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TemperatureView, 0, 0);

        try {

            topPartColor = a.getInteger(R.styleable.TemperatureView_topPartColor, 255);
            bottomPartColor = a.getInteger(R.styleable.TemperatureView_bottomPartColor, 0);
            separatorColor = a.getInteger(R.styleable.TemperatureView_separatorColor, 0);
            textColor =  a.getInteger(R.styleable.TemperatureView_textColor, 0);

            textSize = a.getDimension(R.styleable.TemperatureView_textSize, 0);
            startValue = a.getInt(R.styleable.TemperatureView_startValue, 0);
            endValue = a.getInt(R.styleable.TemperatureView_endValue, 0);
            maxValue = a.getInt(R.styleable.TemperatureView_maxValue, 0);
            minValue = a.getInt(R.styleable.TemperatureView_minValue, 0);
            separatorWidth = a.getInt(R.styleable.TemperatureView_separatorWidth, 10);

            showEndValue = a.getBoolean(R.styleable.TemperatureView_shoeEndValue, false);
            showStartValue = a.getBoolean(R.styleable.TemperatureView_showStartValue, false);
            showSeparator = a.getBoolean(R.styleable.TemperatureView_showSeparator, false);

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        height = this.getMeasuredHeight();
        width = this.getMeasuredWidth();
        start = getStartHeight();
        end = getEndHeight();

        drawBottomTrapeze(canvas);
        drawTopTrapeze(canvas);

        if (showSeparator) {
            drawSeparator(canvas);
        }

        if (showStartValue) {
            showStartVal(canvas);
        }

        if (showEndValue) {
            showEndVal(canvas);
        }
    }

    private void showStartVal(Canvas canvas) {

        Paint startVal = new Paint();
        startVal.setColor(textColor);
        startVal.setTextSize(textSize);
        startVal.setTextAlign(Paint.Align.CENTER);

        String text = String.valueOf(startValue);
        int x = 30,
                y = getStartHeight() - separatorWidth - 30;

        canvas.drawText(text, x, y, startVal);
    }

    private void showEndVal(Canvas canvas) {

        Paint endVal = new Paint();
        endVal.setColor(textColor);
        endVal.setTextSize(textSize);
        endVal.setTextAlign(Paint.Align.CENTER);


        String text = String.valueOf(startValue);
        int x = this.getMeasuredWidth() - 30,
                y = getEndHeight() - separatorWidth - 30;

        canvas.drawText(text, x, y, endVal);
    }

    private void drawBottomTrapeze(Canvas canvas) {
        Paint bottomTrapeze = new Paint();

        bottomTrapeze.setColor(bottomPartColor);
        bottomTrapeze.setStyle(Paint.Style.FILL);

        Path bottomTrapPath = new Path();
        bottomTrapPath.reset();
        bottomTrapPath.moveTo(0, height);
        bottomTrapPath.lineTo(0, start);
        bottomTrapPath.lineTo(halfx(width), getAverage(start, end));
        bottomTrapPath.lineTo(width, end);

        bottomTrapPath.lineTo(width, height);
        bottomTrapPath.lineTo(0, height);

        canvas.drawPath(bottomTrapPath, bottomTrapeze);
    }

    private void drawTopTrapeze(Canvas canvas) {
        Paint topTrapeze = new Paint();
        topTrapeze.setColor(topPartColor);
        topTrapeze.setStyle(Paint.Style.FILL);

        Path topTrapPath = new Path();
        topTrapPath.reset();
        topTrapPath.moveTo(0, 0);
        topTrapPath.lineTo(0, start);
        topTrapPath.lineTo(halfx(width), getAverage(start, end));
        topTrapPath.lineTo(width, end);
        topTrapPath.lineTo(width, 0);
        topTrapPath.lineTo(0, 0);

        canvas.drawPath(topTrapPath, topTrapeze);
    }

    private void drawSeparator(Canvas canvas) {
        Paint sepTrapeze = new Paint();
        sepTrapeze.setColor(separatorColor);
        sepTrapeze.setStyle(Paint.Style.FILL);

        Path sepTrapPath = new Path();
        sepTrapPath.reset();
        sepTrapPath.moveTo(0, start + separatorWidth);
        sepTrapPath.lineTo(0, start);
        sepTrapPath.lineTo(halfx(width), getAverage(start, end));
        sepTrapPath.lineTo(width, end);
        sepTrapPath.lineTo(width, end + separatorWidth);
        sepTrapPath.lineTo(halfx(width), getAverage(start + separatorWidth,
                end + separatorWidth));
        sepTrapPath.lineTo(0, start + separatorWidth);

        canvas.drawPath(sepTrapPath, sepTrapeze);
    }

    private int getStartHeight() {
        double procent = 1.0 - (double) (startValue - minValue) / (double) (maxValue - minValue);
        return (int) ((double) height * procent);
    }

    private int getEndHeight() {
        double procent = 1.0 - (double) (endValue - minValue) / (double) (maxValue - minValue);
        return (int) ((double) height * procent);
    }

    private int getAverage(int x, int y) {
        return (x + y) / 2;
    }

    private int halfx(int value) {
        double result = ((double) value / 2.0) * 1.05;
        if (result > 0) {
            return (int) (result * 0.8);
        } else {
            return (int) (result * 1.2);
        }
    }

    public int getTopPartColor() {
        return topPartColor;
    }

    public void setTopPartColor(int topPartColor) {
        this.topPartColor = topPartColor;
    }

    public int getBottomPartColor() {
        return bottomPartColor;
    }

    public void setBottomPartColor(int bottomPartColor) {
        this.bottomPartColor = bottomPartColor;
    }

    public int getSeparatorColor() {
        return separatorColor;
    }

    public void setSeparatorColor(int separatorColor) {
        this.separatorColor = separatorColor;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public void setEndValue(int endValue) {
        this.endValue = endValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getSeparatorWidth() {
        return separatorWidth;
    }

    public void setSeparatorWidth(int separatorWidth) {
        this.separatorWidth = separatorWidth;
    }

    public boolean isShowStartValue() {
        return showStartValue;
    }

    public void setShowStartValue(boolean showStartValue) {
        this.showStartValue = showStartValue;
    }

    public boolean isShowEndValue() {
        return showEndValue;
    }

    public void setShowEndValue(boolean showEndValue) {
        this.showEndValue = showEndValue;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isShowSeparator() {
        return showSeparator;
    }

    public void setShowSeparator(boolean showSeparator) {
        this.showSeparator = showSeparator;
    }
}

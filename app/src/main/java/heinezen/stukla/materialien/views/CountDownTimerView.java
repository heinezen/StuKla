package heinezen.stukla.materialien.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import heinezen.stukla.R;

/**
 * Created by Christophad on 25.12.2014.
 */
public class CountDownTimerView extends View
{
    private int _counterColor;
    private int _backgroundColor;
    private int _labelColor;

    private String _counterText;

    private Paint _countdownPaint;

    private long _maxZeit;
    private int _fortschritt;

    public CountDownTimerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        _fortschritt = 0;

        _countdownPaint = new Paint();

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CountDownTimerView, 0, 0);

        _counterColor = attributes.getInt(R.styleable.CountDownTimerView_counterColor, Color.GREEN);
        _backgroundColor = attributes.getInt(R.styleable.CountDownTimerView_backgroundColor,
                Color.RED);
        _labelColor = attributes.getInt(R.styleable.CountDownTimerView_labelColor, Color.BLACK);

        _counterText = attributes.getString(R.styleable.CountDownTimerView_counterLabel);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int viewBreite = this.getMeasuredWidth();
        int viewHoehe = this.getMeasuredHeight();

        _countdownPaint.setStyle(Paint.Style.STROKE);
        _countdownPaint.setAntiAlias(true);
        _countdownPaint.setStrokeWidth(4);
        _countdownPaint.setColor(_backgroundColor);

        canvas.drawRect(0, 0, viewBreite, viewHoehe, _countdownPaint);

        _countdownPaint.setStyle(Paint.Style.FILL);
        _countdownPaint.setAntiAlias(true);
        _countdownPaint.setColor(_counterColor);

        canvas.drawRect(0, 0, viewBreite - _fortschritt, viewHoehe, _countdownPaint);

        _countdownPaint.setColor(_labelColor);
        _countdownPaint.setTextAlign(Paint.Align.LEFT);
        _countdownPaint.setTextSize(12);

        canvas.drawText(_counterText, 0, 0, _countdownPaint);
    }

    public void setFortschritt(long millisUntilFinish)
    {
        int intervalle = this.getMeasuredWidth();
        long countDownIntervall = _maxZeit / intervalle;

        long offset = (_maxZeit - (countDownIntervall * _fortschritt)) - millisUntilFinish;

        if(offset >= countDownIntervall)
        {
            _fortschritt += offset / countDownIntervall;

            if(_fortschritt > intervalle)
            {
                _fortschritt = intervalle;
            }
        }
    }

    public void setMaxZeit(long millisInFuture)
    {
        _maxZeit = millisInFuture;
    }
}

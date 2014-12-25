package heinezen.stukla.materialien.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Christophad on 25.12.2014.
 */
public class CountDownTimerView extends View
{
    public CountDownTimerView(Context context, AttributeSet attrs, CountDownTimer timer)
    {
        super(context, attrs);

        instantiate(timer);
    }

    private void instantiate(CountDownTimer timer)
    {
        timer.start();
    }
}

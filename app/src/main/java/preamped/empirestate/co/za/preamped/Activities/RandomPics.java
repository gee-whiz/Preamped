package preamped.empirestate.co.za.preamped.Activities;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;

import preamped.empirestate.co.za.preamped.R;

/**
 * Created by George Kapoya on 2015-04-22.
 */
public class RandomPics {

    static Timer timer;

    public interface RandomPicsListener {
        public void onCompleteFlash();
    }

    static RandomPicsListener mListener;
    static int count = 0;

    public static void getImage(Context ctx, ImageView v, TextView txt, RandomPicsListener listener) {
        mListener = listener;
        Random random = new Random(System.currentTimeMillis());
        Log.i("Random", "starting switch");
        int index = random.nextInt(3);
        try {
            switch (index) {

                case 0:
                    v.setImageDrawable(ctx.getResources().getDrawable(R.drawable.pb));

                    txt.setText("Power up");
                    break;
                case 1:
                    v.setImageDrawable(ctx.getResources().getDrawable(R.drawable.pc));
                    txt.setText("Power up");
                    break;
                case 2:
                    v.setImageDrawable(ctx.getResources().getDrawable(R.drawable.px));
                    txt.setText("Power up");
                    break;

            }
            count++;
            if (count > 4) {
                count = 0;
                listener.onCompleteFlash();
            }
        } catch (Exception e) {
            Log.d("RandomPics", e.toString());
        }
    }

    public static void getResources() {
        return;
    }
}
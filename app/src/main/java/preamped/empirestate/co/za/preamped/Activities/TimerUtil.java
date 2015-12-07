package preamped.empirestate.co.za.preamped.Activities;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by George Kapoya on 2015-04-22.
 */
public class TimerUtil{
   public interface TimerListener {
    public void onSessionDisconnected();
}
public interface TimerFlashListener{
    public void onStartFlash();
}
static TimerListener listener;
static Timer timer;
static final long TEN_SECONDS = 10 * 1000;
    public static void startTimer(TimerListener timerListener) {
        //
        Log.d("TimerUtil", "########## Websocket Session Timer starting .....");
        listener = timerListener;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerUtil", "########## about to disconnect websocket session");
                
                listener.onSessionDisconnected();
            }
        }, TEN_SECONDS);
    }
    public static void killTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            Log.w("TimerUtil", "########## Websocket Session Timer KILLED");
        }
    }
static TimerFlashListener timerFlashListener;
static Timer timerFlash;
    public static void startFlashTime(final TimerFlashListener flashListener){
        timerFlashListener = flashListener;
        timerFlash = new Timer();
        timerFlash.schedule(new TimerTask() {
            @Override
            public void run() {
                flashListener.onStartFlash();
            }
        },100, 5000);
    }

    public static void killFlashTimer() {
        if (timerFlash != null) {
            timerFlash.cancel();
            timerFlash = null;
            Log.w("TimerUtil", "########## Flash Timer KILLED");
        }
    }
}

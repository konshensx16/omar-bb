package com.example.konshensx.omar_bb;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {

    public enum Time {
        HALF_HOUR,
        ONE_HOUR,
        ONE_AND_HALF,
        FREE
    }

    // SAY NOT TO MAGIC NUMBERS
    public static final int HALF_HOUR = 5;
    public static final int ONE_HOUR = 3600;
    public static final int ONE_AND_HALF= 4200;

    private static final String TAG = "TIMER";
    private long secondsPassed = 0;
    private int id = 0;
    private long startedAt = 0;
    private long stoppedAt = 0;
    private boolean isRunning = false;
    private Time time;
    private boolean isPlaying;

    private Timer timer;
    private TimerTask timerTask;
    final MediaPlayer mp;

    private Context context;


    // FIXME: this is the constructor
    CustomTimer(Context context, Time time) {
        this.isRunning = false;
        this.id = MainActivity.id++;
        this.time = time;
        this.context = context;
        this.mp = MediaPlayer.create(this.context, R.raw.iphone_best_alarm);
        this.isPlaying = false;
    }

    public void startTimer() {
        this.startedAt = System.currentTimeMillis();
        this.timer = new Timer(true);
        // i need to recreate the timerTask
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                CustomTimer.this.secondsPassed += 1000;
            }
        };
        this.timer.scheduleAtFixedRate(timerTask, 0, 1000);
        this.isRunning = true;
    }

    public void stopTimer() {
        this.stoppedAt = System.currentTimeMillis();
        this.timer.cancel();
        this.timer = null;
        this.secondsPassed = 0;
        this.isRunning = false;
        // this is for the alarm
        this.isPlaying = false;
        Log.d(TAG, "TIMER STOPPED");
        Log.d(TAG, String.valueOf(this.isRunning));
    }

    public void pauseTimer() {
        this.timer.cancel();
        this.isRunning = false;
    }

    public void resumeTimer() {
        this.timer = new Timer(true);

        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsPassed += 1000;
            }
        };
        this.timer.scheduleAtFixedRate(timerTask, 0, 1000);
        this.isRunning = true;
    }


    public long getSecondsPassed() {
        return this.secondsPassed;
    }

    public long getHumanSecondsPassed()
    {
        return this.secondsPassed / 1000;
    }

    public String getElapsedTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(this.secondsPassed);
        String result = (formatter.format(date));
        return result;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getId() {
        return this.id;
    }

    public int getHumanId() {
        return this.id + 1;
    }

    public Time getTargetTime() {
        return this.time;
    }


    public void playSound() {
        this.mp.start();
        this.isPlaying = true;
    }

    public void stopSound()
    {
        this.mp.stop();
        this.isPlaying = false;
    }

    public boolean isPlaying()
    {
        return this.isPlaying;
    }

    public int getHumanTargetTime()
    {
        switch (this.time) {
            case HALF_HOUR:
                return 30;
            case ONE_HOUR:
                return 60;
            case ONE_AND_HALF:
                return 90;
        }

        return 0;
    }

    public String getExtraElapsedTime() {
        // TODO: get the extra time , the target_time minus the elapsed seconds
        // elapsed - target = extra
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        int targetTimeInSeconds = 0;
        switch (this.time) {
            case HALF_HOUR:
                targetTimeInSeconds = HALF_HOUR * 1000;
                break;
            case ONE_HOUR:
                targetTimeInSeconds = ONE_HOUR * 1000;
                break;
            case ONE_AND_HALF:
                targetTimeInSeconds = ONE_AND_HALF * 1000;
                break;
        }
        int extra = (int)this.secondsPassed - targetTimeInSeconds;
        Date date = new Date(extra);
        String result = (formatter.format(date));
        return "+" + result;
    }

}

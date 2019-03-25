package com.example.konshensx.omar_bb;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {

    private static final String TAG = "TIMER";
    private long secondsPassed = 0;
    private int id = 0;
    private long startedAt = 0;
    private long stoppedAt = 0;
    private boolean isRunning = false;

    private Timer timer;
    private TimerTask timerTask;


    // FIXME: this is the constructor
    CustomTimer() {
        this.isRunning = false;
        this.id = MainActivity.id++;
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

    public int getHumanId()
    {
        return this.id + 1;
    }
}

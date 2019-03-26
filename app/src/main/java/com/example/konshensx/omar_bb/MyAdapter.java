package com.example.konshensx.omar_bb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MY_ADAPTER";


    private List<CustomTimer> mDataset;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public Button startStopTimer;
        public Button pauseResumeTimer;
        public TextView humanIdText;
        public TextView targetTime;
        public TextView extraTime;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.info_text);
            startStopTimer = v.findViewById(R.id.start_stop);
            pauseResumeTimer = v.findViewById(R.id.pause_resume);
            humanIdText = v.findViewById(R.id.human_id);
            targetTime = v.findViewById(R.id.target_time);
            extraTime = v.findViewById(R.id.extra_time);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<CustomTimer> myDataset, Activity activity) {
        mDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final CustomTimer myTimer = mDataset.get(position);
        holder.mTextView.setText(mDataset.get(position).getElapsedTime());
        holder.humanIdText.setText(Integer.toString(myTimer.getHumanId()));
        holder.targetTime.setText(Integer.toString(myTimer.getHumanTargetTime()));
        holder.extraTime.setText("+00:00:00");

        // start / stop the timer
        holder.startStopTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!myTimer.isRunning()) {
                    myTimer.startTimer();
                    holder.startStopTimer.setText("Stop");
                    Drawable stopIcon = activity.getResources().getDrawable(R.drawable.baseline_stop_white_18pt_2x);
                    holder.startStopTimer.setCompoundDrawablesWithIntrinsicBounds(stopIcon, null, null, null);

                } else {
                    myTimer.stopTimer();
                    Drawable stopIcon = activity.getResources().getDrawable(R.drawable.baseline_play_arrow_white_18pt_2x);
                    holder.startStopTimer.setCompoundDrawablesWithIntrinsicBounds(stopIcon, null, null, null);
                    holder.startStopTimer.setText("Start");
                    holder.mTextView.setText("00:00:00");
                }
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (myTimer.isRunning()) {
                                    holder.mTextView.setText(myTimer.getElapsedTime());
                                    holder.extraTime.setText(myTimer.getExtraElapsedTime());
                                    // NOTE: this should only work if the user sets a myTimer, and it's not just counting.
                                    switch (myTimer.getTargetTime()) {
                                        case HALF_HOUR:
                                            // TODO: How am i gonna shut the sound ?
                                            // NOTE: the sound shuts after it's completed it not looping (GOOD THING I THINK)
                                            Log.d(TAG, String.valueOf(myTimer.getHumanSecondsPassed()));
                                            Log.d(TAG, String.valueOf(myTimer.isPlaying()));

                                            if (myTimer.getHumanSecondsPassed() >= CustomTimer.HALF_HOUR && !myTimer.isPlaying()) {
                                                Log.d("MY_ADAPTER", "SHOULD PLAY SOUNDS");
                                                holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                                myTimer.playSound();
                                            }
                                            break;
                                        case ONE_HOUR:
                                            if (myTimer.getHumanSecondsPassed() >= CustomTimer.ONE_HOUR && !myTimer.isPlaying()) {
                                                holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                                myTimer.playSound();
                                            }
                                            break;

                                        case ONE_AND_HALF:
                                            if (myTimer.getHumanSecondsPassed() >= CustomTimer.ONE_AND_HALF && !myTimer.isPlaying()) {
                                                holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                                myTimer.playSound();
                                            }
                                            break;
                                    }
                                }
                            }
                        });
                    }
                };

                timer.scheduleAtFixedRate(timerTask, 0, 100);
            }
        });

        // pause / resume the timer
        holder.pauseResumeTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!myTimer.isRunning()) {
                    myTimer.resumeTimer();
                    holder.pauseResumeTimer.setText("Pause");
                    Drawable stopIcon = activity.getResources().getDrawable(R.drawable.baseline_pause_white_18pt_2x);
                    holder.pauseResumeTimer.setCompoundDrawablesWithIntrinsicBounds(stopIcon, null, null, null);
                } else {
                    myTimer.pauseTimer();
                    holder.pauseResumeTimer.setText("Resume");
                    Drawable stopIcon = activity.getResources().getDrawable(R.drawable.baseline_play_arrow_white_18pt_2x);
                    holder.pauseResumeTimer.setCompoundDrawablesWithIntrinsicBounds(stopIcon, null, null, null);
                }
//                notifyItemChanged(position);

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mDataset.get(position).isRunning()) {
                                    holder.mTextView.setText(mDataset.get(position).getElapsedTime());
                                    holder.extraTime.setText(myTimer.getExtraElapsedTime());
                                }

                                switch (myTimer.getTargetTime()) {
                                    case HALF_HOUR:
                                        // TODO: How am i gonna shut the sound ?
                                        // NOTE: the sound shuts after it's completed it not looping (GOOD THING I THINK)
                                        if (myTimer.getHumanSecondsPassed() >= CustomTimer.HALF_HOUR && !myTimer.isPlaying()) {
                                            // also change the background color or the border color
                                            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                            myTimer.playSound();
                                        }
                                        break;
                                    case ONE_HOUR:
                                        if (myTimer.getHumanSecondsPassed() >= CustomTimer.ONE_HOUR && !myTimer.isPlaying()) {
                                            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                            myTimer.playSound();
                                        }
                                        break;

                                    case ONE_AND_HALF:
                                        if (myTimer.getHumanSecondsPassed() >= CustomTimer.ONE_AND_HALF && !myTimer.isPlaying()) {
                                            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                                            myTimer.playSound();
                                        }
                                        break;
                                }
                            }
                        });
                    }
                };

                timer.scheduleAtFixedRate(timerTask, 0, 100);
                Log.d(TAG, "button clicked ");
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
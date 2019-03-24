package com.example.konshensx.omar_bb;

import android.app.Activity;
import android.content.Context;
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

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.info_text);
            startStopTimer = v.findViewById(R.id.start_stop);
            pauseResumeTimer = v.findViewById(R.id.pause_resume);
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
        holder.mTextView.setText(mDataset.get(position).getElapsedTime());

        // start / stop the timer
        holder.startStopTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CustomTimer myTimer = mDataset.get(position);
                if (!myTimer.isRunning()) {
                    myTimer.startTimer();
                    holder.startStopTimer.setText("Stop");
                } else {
                    myTimer.stopTimer();
                    holder.startStopTimer.setText("Start");
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
//                                    Log.d("MY_ADAPTER", "RUNNING");
                                    holder.mTextView.setText(mDataset.get(position).getElapsedTime());
//                                    Log.d("MY_ADAPTER", String.valueOf(mDataset.get(position).isRunning()));

                                }
                            }
                        });
                    }
                };

                timer.scheduleAtFixedRate(timerTask, 0, 100);

                Log.d(TAG, "button clicked ");
            }
        });

        // pause / resume the timer
        holder.pauseResumeTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CustomTimer myTimer = mDataset.get(position);
                if (!myTimer.isRunning()) {
                    myTimer.resumeTimer();
                    holder.pauseResumeTimer.setText("Pause");
                } else {
                    myTimer.pauseTimer();
                    holder.pauseResumeTimer.setText("Resume");
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
                                    Log.d("MY_ADAPTER", "RUNNING");
                                    holder.mTextView.setText(mDataset.get(position).getElapsedTime());
//                                    Log.d("MY_ADAPTER", String.valueOf(mDataset.get(position).isRunning()));
                                    Log.d("MY_ADAPTER", "FUCK " + String.valueOf(holder.pauseResumeTimer.getText()));
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
package com.example.konshensx.omar_bb;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MAIN_ACTIVITY";

    public static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomTimer> myDataset;

    public static int id = 0;

    private boolean isFABOpen;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionButton fab4;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDataset = new ArrayList<CustomTimer>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);

        // add a swipe event to dismiss the timer
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (swipeDir == ItemTouchHelper.LEFT) {
                    final int index = viewHolder.getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?");    //set message

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Remove swiped item from list and notify the RecyclerView
                            // remove the card
                            // the timer needs to STOPPED before removing it
                            CustomTimer myTimer = myDataset.get(index);
                            if (myTimer.isRunning()) {
                                // FIXME: this line is throwing an exception
                                myTimer.stopTimer();
                                myTimer.stopSound();
                            }
                            myDataset.remove(index);  //then remove item
                            mAdapter.notifyItemRemoved(index);    //item removed from recylcerview

                            return;
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(index + 1);    //notifies the RecyclerView Adapter that data in mAdapter has been removed at a particular index.
                            mAdapter.notifyItemRangeChanged(index, mAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show();  //show alert dialog
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // NOTE: this just open the other useful fab buttons
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        // NOTE: 30 mins button
        this.fab1 = (FloatingActionButton) findViewById(R.id.fab_half);
        this.fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataset.add(new CustomTimer(MainActivity.this, CustomTimer.Time.HALF_HOUR));
                mAdapter.notifyItemInserted(myDataset.size() - 1);
            }
        });

        this.fab2 = (FloatingActionButton) findViewById(R.id.fab_hour);
        this.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataset.add(new CustomTimer(MainActivity.this, CustomTimer.Time.ONE_HOUR));
                mAdapter.notifyItemInserted(myDataset.size() - 1);
            }
        });

        this.fab3 = (FloatingActionButton) findViewById(R.id.fab_one_and_half);
        this.fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataset.add(new CustomTimer(MainActivity.this, CustomTimer.Time.ONE_AND_HALF));
                mAdapter.notifyItemInserted(myDataset.size() - 1);
            }
        });

        this.fab4 = (FloatingActionButton) findViewById(R.id.free);
        this.fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataset.add(new CustomTimer(MainActivity.this, CustomTimer.Time.FREE));
                mAdapter.notifyItemInserted(myDataset.size() - 1);
            }
        });

    }

    private void showFABMenu() {
        this.isFABOpen = true;
        this.fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        this.fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        this.fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        this.fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
    }

    private void closeFABMenu() {
        this.isFABOpen = false;
        this.fab1.animate().translationY(0);
        this.fab2.animate().translationY(0);
        this.fab3.animate().translationY(0);
        this.fab4.animate().translationY(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

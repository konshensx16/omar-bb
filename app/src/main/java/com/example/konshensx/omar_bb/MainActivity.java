package com.example.konshensx.omar_bb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomTimer> myDataset;

    public static int id = 0;

    private boolean isFABOpen;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;


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

    }

    private void showFABMenu() {
        this.isFABOpen = true;
        this.fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        this.fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        this.fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu() {
        this.isFABOpen = false;
        this.fab1.animate().translationY(0);
        this.fab2.animate().translationY(0);
        this.fab3.animate().translationY(0);
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

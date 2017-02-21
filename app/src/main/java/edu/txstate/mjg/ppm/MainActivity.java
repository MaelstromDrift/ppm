package edu.txstate.mjg.ppm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Random;

import edu.txstate.mjg.ppm.activities.ProcessListAdapter;
import edu.txstate.mjg.ppm.core.Process;

public class MainActivity extends AppCompatActivity {

    ArrayList<Process> processList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processList = new ArrayList<>();
        final RecyclerView processRecylcer = (RecyclerView) findViewById(R.id.recycler_view);

        createDummyData(new Random().nextInt(100));
        ProcessListAdapter processListAdapter = new ProcessListAdapter(this, processList);

        processRecylcer.setAdapter(processListAdapter);
        processRecylcer.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processList.add(new Process());
                processList.get(processList.size()-1).setTitle(Integer.toString(processList.size()-1));
                processRecylcer.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void createDummyData(int numOfProcesses) {
        for(int i = 0; i < numOfProcesses; i++) {
            Process temp_process = new Process();
            temp_process.setTitle("Title " + Integer.toString(i));
            processList.add(temp_process);
        }
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

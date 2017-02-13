package edu.txstate.mjg.ppm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;

public class MainActivity extends AppCompatActivity {

    TextView processTextView;
    ArrayList<Process> processList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processList = new ArrayList<Process>();
        processTextView = (TextView) findViewById(R.id.processList);

        createDummyData(5);
        populateTextView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processList.add(new Process());
                populateTextView();
                StringBuilder builder = new StringBuilder();
            }
        });
    }

    private void populateTextView() {
        StringBuilder builtString;
        builtString = new StringBuilder();
        for(int i = 0; i < processList.size(); i++) {
            builtString.append(processList.get(i).getTitle() + ": ");
            builtString.append(processList.get(i).getDescription() + "\n");
            builtString.append("# of tasks: ");
            builtString.append(processList.get(i).numTasks());
            builtString.append("\n\n\n");
        }
        processTextView.setText(builtString.toString());
    }
    private void createDummyData(int numOfProcesses) {
        Random rand = new Random();
        for(int i = 0; i < rand.nextInt(100); i++) {
            Process temp_process = new Process();
            for(int j = 0; j < rand.nextInt(10); j++) {
                temp_process.addTask(new Task());
            }
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

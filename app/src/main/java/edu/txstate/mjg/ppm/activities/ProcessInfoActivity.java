package edu.txstate.mjg.ppm.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.adapters.TaskListItemAdapter;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLUtils;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;

public class ProcessInfoActivity extends AppCompatActivity {

    private Process mProcess;
    Toolbar toolbar;

    TextView description;
    ListView taskListView;

    SQLiteDBHelper sqLiteDBHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.process_info_view);

        Bundle extras = getIntent().getExtras();

        sqLiteDBHelper = new SQLiteDBHelper(this);
        db = sqLiteDBHelper.getWritableDatabase();
        mProcess = SQLUtils.getProcess((new SQLiteDBHelper(this)).getReadableDatabase(), extras.getInt("process_id"));
        loadAndInitViews();

        taskListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Switch to a task fragment/activity
            }
        });
    }

    private void loadAndInitViews() {
        taskListView = (ListView) findViewById(R.id.process_info_task_list);
        description = (TextView) findViewById(R.id.process_info_description);

        toolbar = (Toolbar) findViewById(R.id.process_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mProcess.getTitle());

        description.setText(mProcess.getDescription());

        taskListView.setAdapter(new TaskListItemAdapter(this, R.layout.process_info_task_list_item, mProcess.getTasks()));
    }
}

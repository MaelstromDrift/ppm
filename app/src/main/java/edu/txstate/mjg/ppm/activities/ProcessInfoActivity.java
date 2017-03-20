package edu.txstate.mjg.ppm.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.adapters.TaskListItemAdapter;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;
import edu.txstate.mjg.ppm.utils.SQLUtils;

public class ProcessInfoActivity extends AppCompatActivity {

    private Process mProcess;
    CollapsingToolbarLayout toolbar;

    TextView description;
    ListView taskListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.process_info_view);

        Bundle extras = getIntent().getExtras();

        loadProcess(extras.getInt("process_id"));
        loadAndInitViews();

    }

    private void loadAndInitViews() {
        taskListView = (ListView) findViewById(R.id.process_info_task_list);
        description = (TextView) findViewById(R.id.process_info_description);

        description.setText(mProcess.getDescription());

        Log.d("Loading tasks", mProcess.getTasks().toString());
        taskListView.setAdapter(new TaskListItemAdapter(this, R.layout.process_info_task_list_item, mProcess.getTasks()));
    }


    private void loadProcess(int uID) {
        mProcess = SQLUtils.getProcess((new SQLiteDBHelper(this)).getReadableDatabase(), uID);

        toolbar = (CollapsingToolbarLayout) findViewById(R.id.process_info_collapsing_toolbar);
        toolbar.setTitle(mProcess.getTitle());
    }
}

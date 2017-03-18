package edu.txstate.mjg.ppm.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;
import edu.txstate.mjg.ppm.utils.SQLUtils;

public class ProcessInfoActivity extends AppCompatActivity {

    private Process mProcess;
    TextView description;
    CollapsingToolbarLayout toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.process_info_view);

        Bundle extras = getIntent().getExtras();

        loadProcess(extras.getInt("process_id"));
    }


    private void loadProcess(int uID) {
        mProcess = SQLUtils.getProcess((new SQLiteDBHelper(this)).getReadableDatabase(), uID);

        toolbar = (CollapsingToolbarLayout) findViewById(R.id.process_info_collapsing_toolbar);
        toolbar.setTitle(mProcess.getTitle());

        description = (TextView) findViewById(R.id.process_info_description);
        description.setText(mProcess.getDescription());
    }
}

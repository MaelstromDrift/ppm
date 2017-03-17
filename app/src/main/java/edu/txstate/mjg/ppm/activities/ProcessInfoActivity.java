package edu.txstate.mjg.ppm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;
import edu.txstate.mjg.ppm.utils.SQLUtils;

public class ProcessInfoActivity extends AppCompatActivity {

    private Process loadedProcess;
    TextView title;
    TextView description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.process_info_view);

        Bundle extras = getIntent().getExtras();

        loadedProcess = loadProcess(extras.getInt("process_id"));
        description = (TextView) findViewById(R.id.process_info_description);

        description.setText(loadedProcess.getDescription());
    }

    private Process loadProcess(int uID) {
        return SQLUtils.getProcess((new SQLiteDBHelper(this)).getReadableDatabase(), uID);
    }
}

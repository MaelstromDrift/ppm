package edu.txstate.mjg.ppm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;

/**
 * Created by MarkGitthens on 3/4/2017.
 */

public class CreateProcessActivity extends AppCompatActivity {
    private Spinner categories;
    private Toolbar toolbar;
    private EditText titleText;
    private EditText descriptionText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_process);

        toolbar = (Toolbar) findViewById(R.id.create_process_toolbar);
        setSupportActionBar(toolbar);

        titleText = (EditText) findViewById(R.id.create_process_title);
        descriptionText = (EditText) findViewById(R.id.create_process_description);

        categories = (Spinner) findViewById(R.id.create_process_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_process, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit_process) {
            Log.d("Create Process", categories.getSelectedItem().toString());

            Process newProcess = new Process(titleText.getText().toString(), descriptionText.getText().toString(), Process.Categories.education, 0);
            //TODO: insert the newly created process into the local database
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

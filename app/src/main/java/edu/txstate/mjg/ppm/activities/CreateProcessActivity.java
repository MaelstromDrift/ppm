package edu.txstate.mjg.ppm.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;
import edu.txstate.mjg.ppm.fragments.CreateTaskDialog;
import edu.txstate.mjg.ppm.sql.SQLUtils;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;

public class CreateProcessActivity extends AppCompatActivity implements CreateTaskDialog.CreateTaskDialogListener {

    Toolbar toolbar;
    EditText title;
    EditText description;
    Spinner categories;
    ListView tasksList;
    FloatingActionButton fab;

    ArrayAdapter<Task> taskArrayAdapter;
    //TODO: Pass the database from mainactivity to this fragment so we don't have multiple connections to database
    SQLiteDatabase db;
    SQLiteDBHelper sqLiteDBHelper;

    Process mProcess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_process_create);

        mProcess = new Process();

        sqLiteDBHelper = new SQLiteDBHelper(this);

        //TODO: should be asynchronous
        db = sqLiteDBHelper.getWritableDatabase();

        toolbar = (Toolbar) findViewById(R.id.create_process_toolbar);
        title = (EditText) findViewById(R.id.create_process_title);
        description = (EditText) findViewById(R.id.create_process_description);
        categories = (Spinner) findViewById(R.id.process_create_spinner);
        tasksList = (ListView) findViewById(R.id.process_create_tasklist);
        fab = (FloatingActionButton) findViewById(R.id.create_process_fab);

        taskArrayAdapter = new ArrayAdapter<Task>(this, 0, mProcess.getTasks()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Task task = getItem(position);

                if(convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.create_task_list_item, parent, false);
                }
                TextView title = (TextView) convertView.findViewById(R.id.create_task_list_item_title);

                ImageButton delete = (ImageButton) convertView.findViewById(R.id.create_process_tasklist_delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: This could probably be constant time instead of linear
                        for(Task t: mProcess.getTasks()) {
                            if(t.getTaskId() == task.getTaskId()) {
                                mProcess.getTasks().remove(t);
                                notifyDataSetChanged();
                                return;
                            }
                        }

                    }
                });
                title.setText(task.getTitle());
                return convertView;
            }
        };
        tasksList.setAdapter(taskArrayAdapter);

        setSupportActionBar(toolbar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter);

        getSupportActionBar().setTitle("New Process");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTaskDialog().show(getFragmentManager(), "create task dialog");
            }
        });
    }

    private boolean checkValidInput() {
        boolean valid = true;
        if((title.getText().toString().trim().isEmpty())) {
            valid = false;
            Toast.makeText(this, "Please enter a valid title.", Toast.LENGTH_SHORT).show();
        }
        if((description.getText().toString().trim().isEmpty())) {
            valid = false;
            Toast.makeText(this, "Please enter a valid description.", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
        if (item.getItemId() == R.id.action_submit_process) {
            if(checkValidInput()) {
                mProcess.setTitle(title.getText().toString().trim());
                mProcess.setDescription(description.getText().toString().trim());
                mProcess.setCategory(categories.getSelectedItem().toString());

                SQLUtils.insertProcess(db, mProcess);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //insert task into database to generate the Unique ID;
        //query the new task and add it to the process

        long taskID = SQLUtils.insertTask(db, new Task(((CreateTaskDialog)dialog).getTitle(), ((CreateTaskDialog)dialog).getDescription(), 0));
        mProcess.addTask(SQLUtils.getTask(db, (int) taskID));
        taskArrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        sqLiteDBHelper.close();
    }
}

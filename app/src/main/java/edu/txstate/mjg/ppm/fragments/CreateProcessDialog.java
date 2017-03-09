package edu.txstate.mjg.ppm.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;
import edu.txstate.mjg.ppm.utils.SQLUtils;

public class CreateProcessDialog extends DialogFragment {

    Toolbar toolbar;
    EditText title;
    EditText description;

    Spinner categories;

    SQLiteDatabase db;
    SQLiteDBHelper sqLiteDBHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_process_create, container, false);

        sqLiteDBHelper = new SQLiteDBHelper(view.getContext());

        //TODO: should be asynchronous
        db = sqLiteDBHelper.getWritableDatabase();

        toolbar = (Toolbar) view.findViewById(R.id.create_process_toolbar);
        title = (EditText) view.findViewById(R.id.create_process_title);
        description = (EditText) view.findViewById(R.id.create_process_description);
        categories = (Spinner) view.findViewById(R.id.process_create_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter);

        toolbar.setTitle("New Process");
        toolbar.inflateMenu(R.menu.menu_create_process);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_submit_process) {
                    if(checkValidInput()) {
                        SQLUtils.insertProcess(db,  new Process(title.getText().toString().trim(), description.getText().toString().trim(), categories.getSelectedItem().toString(), 0));
                        ((InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        dismiss();
                        ((ProcessListFragment) getFragmentManager().findFragmentById(R.id.process_list_fragment)).refreshProcesses();
                    }
                }
                return true;
            }
        });

        return view;
    }

    private boolean checkValidInput() {
        boolean valid = true;
        if((title.getText().toString().trim().isEmpty())) {
            valid = false;
            //create toast message saying invalid title;
        }
        if((description.getText().toString().trim().isEmpty())) {
            valid = false;
            //create toast message saying invalid description;
        }
        return valid;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        sqLiteDBHelper.close();
    }
}

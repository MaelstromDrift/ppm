package edu.txstate.mjg.ppm.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.activities.ProcessCardAdapter;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.sql.SQLiteDBHelper;
import edu.txstate.mjg.ppm.utils.SQLUtils;

public class ProcessListFragment extends Fragment {

    ArrayList<Process> mProcessList;
    SQLiteDatabase db;
    SQLiteDBHelper dbHelper;
    ProcessCardAdapter processCardAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.process_list_view, container, false);

        final RecyclerView processRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);

        dbHelper = new SQLiteDBHelper(view.getContext());
        //TODO: This should be asynchronous
        db = dbHelper.getWritableDatabase();

        mProcessList = SQLUtils.getAllProcesses(db);

        processCardAdapter = new ProcessCardAdapter(view.getContext(), mProcessList);

        processRecycler.setAdapter(processCardAdapter);
        processRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                //refreshProcesses();
             //   processRecycler.getAdapter().notifyDataSetChanged();
            }
        });
        return view;
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateProcessDialog newFragment = new CreateProcessDialog();

        //if (mIsLargeLayout) {
       // newFragment.show(getFragmentManager(), "dialog");
        //   } else {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        // }
    }

    public void refreshProcesses() {
        mProcessList.clear();
        mProcessList.addAll(SQLUtils.getAllProcesses(db));
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        dbHelper.close();
    }
}

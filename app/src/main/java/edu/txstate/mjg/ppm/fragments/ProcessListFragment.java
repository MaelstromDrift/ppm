package edu.txstate.mjg.ppm.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.activities.ProcessCardItemClickListener;
import edu.txstate.mjg.ppm.adapters.ProcessCardAdapter;
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
        processRecycler.addOnItemTouchListener(
                new ProcessCardItemClickListener(view.getContext(), processRecycler, new ProcessCardItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(view.getContext(), "Clicked process", Toast.LENGTH_SHORT).show();
                        Intent temp = new Intent().setClass(view.getContext(), edu.txstate.mjg.ppm.activities.ProcessInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("process_id", mProcessList.get(position).getUniqueID());
                        temp.putExtras(bundle);
                        view.getContext().startActivity(temp);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(view.getContext(), "Long clicked process", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }

    public void showDialog() {

        FragmentManager fragmentManager = getFragmentManager();
        CreateProcessDialog newFragment = new CreateProcessDialog();

        //newFragment.show(fragmentManager, "dialog");
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
        processCardAdapter.notifyDataSetChanged();
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

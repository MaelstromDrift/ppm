package edu.txstate.mjg.ppm.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.activities.ProcessCardAdapter;
import edu.txstate.mjg.ppm.core.Process;

public class ProcessListFragment extends Fragment {
    ArrayList<Process> mProcessList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProcessList = new ArrayList<>();
        createDummyData(new Random().nextInt(100));
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.process_list_view, container, false);


        final RecyclerView processRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);

        ProcessCardAdapter processCardAdapter = new ProcessCardAdapter(view.getContext(), mProcessList);

        processRecycler.setAdapter(processCardAdapter);
        processRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent createProcessIntent = new Intent(view.getContext(), CreateProcessActivity.class);
                //view.getContext().startActivity(createProcessIntent);
                mProcessList.add(new Process());
                mProcessList.get(mProcessList.size()-1).setTitle(Integer.toString(mProcessList.size()-1));
                processRecycler.getAdapter().notifyDataSetChanged();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void createDummyData(int numOfProcesses) {
        for(int i = 0; i < numOfProcesses; i++) {
            Process temp_process = new Process();
            temp_process.setTitle("Title " + Integer.toString(i));
            mProcessList.add(temp_process);
        }
    }
}

package edu.txstate.mjg.ppm.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;

public class ProcessListAdapter extends RecyclerView.Adapter<ProcessListAdapter.ViewHolder> {

    private List<Process> mProcesses;

    private Context mContext;

    public ProcessListAdapter(Context context, List<Process> processList) {
        mContext = context;
        mProcesses = processList;
    }

    @Override
    public ProcessListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View processView = inflater.inflate(R.layout.process_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(processView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProcessListAdapter.ViewHolder viewHolder, int position) {
        Process process = mProcesses.get(position);

        TextView nameTextView = viewHolder.processTitle;
        nameTextView.setText(process.getTitle());

        TextView descriptionTextView = viewHolder.processDescription;

        descriptionTextView.setText(process.getDescription());

        TextView processStatus = viewHolder.status;

        //Determine what status text to display
        switch(process.getState()) {
            case PROCESS_PLANNING:
                processStatus.setText("Planning");
                break;
            case PROCESS_INPROGRESS:
                processStatus.setText("In-Progress");
                break;
            case PROCESS_COMPLETED:
                processStatus.setText("Completed");
        }
    }

    @Override
    public int getItemCount() {
        return mProcesses.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView processTitle;
        TextView processDescription;
        TextView startDate;
        TextView status;

        ViewHolder(View itemView) {
            super(itemView);

            processTitle = (TextView) itemView.findViewById(R.id.processTitle);
            processDescription = (TextView) itemView.findViewById(R.id.processDescription);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            status = (TextView) itemView.findViewById(R.id.process_list_status);
        }
    }
}

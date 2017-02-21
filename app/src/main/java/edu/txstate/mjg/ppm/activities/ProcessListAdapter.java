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

    List<Process> mProcesses;

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
    }

    @Override
    public int getItemCount() {
        return mProcesses.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView processTitle;
        public TextView processDescription;
        public TextView startDate;

        public ViewHolder(View itemView) {
            super(itemView);

            processTitle = (TextView) itemView.findViewById(R.id.processTitle);
            processDescription = (TextView) itemView.findViewById(R.id.processDescription);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
        }
    }
}

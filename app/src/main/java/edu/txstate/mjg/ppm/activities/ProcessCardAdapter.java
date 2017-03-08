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

public class ProcessCardAdapter extends RecyclerView.Adapter<ProcessCardAdapter.ViewHolder> {

    private List<Process> mProcesses;

    private Context mContext;

    public ProcessCardAdapter(Context context, List<Process> processList) {
        mContext = context;
        mProcesses = processList;
    }

    @Override
    public ProcessCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View processView = inflater.inflate(R.layout.process_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(processView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProcessCardAdapter.ViewHolder viewHolder, int position) {
        Process process = mProcesses.get(position);

        TextView nameTextView = viewHolder.processTitle;
        nameTextView.setText(process.getTitle());

        TextView descriptionTextView = viewHolder.processDescription;

        descriptionTextView.setText(process.getDescription());

        TextView processStatus = viewHolder.status;

        processStatus.setText(process.getCategory().name());
    }

    @Override
    public int getItemCount() {
        return mProcesses.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView processTitle;
        TextView processDescription;
        TextView status;

        ViewHolder(View itemView) {
            super(itemView);

            processTitle = (TextView) itemView.findViewById(R.id.processTitle);
            processDescription = (TextView) itemView.findViewById(R.id.processDescription);
            status = (TextView) itemView.findViewById(R.id.process_list_status);
        }
    }
}

package edu.txstate.mjg.ppm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Task;


public class TaskListItemAdapter extends ArrayAdapter<Task> {

    Context context;
    int layoutID;
    ArrayList<Task> data;

    TextView taskTitle;
    TextView taskDescription;

    public TaskListItemAdapter(Context context, int layoutID, ArrayList<Task> data) {
        super(context, layoutID, data);

        this.context = context;
        this.layoutID = layoutID;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = data.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        }

        taskTitle = (TextView) convertView.findViewById(R.id.process_info_task_item_title);
        taskDescription = (TextView) convertView.findViewById(R.id.process_info_item_task_description);

        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());

        return convertView;
    }
}

package edu.txstate.mjg.ppm.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;

public class ProcessCardAdapter extends RecyclerView.Adapter<ProcessCardAdapter.CardViewHolder> implements View.OnClickListener {

    private List<Process> mProcesses;

    private Context mContext;

    public ProcessCardAdapter(Context context, List<Process> processList) {
        mContext = context;
        mProcesses = processList;
    }

    @Override
    public ProcessCardAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View processView = inflater.inflate(R.layout.process_card_view, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(processView);
        return viewHolder;
    }

    @Override
    public void onClick(View item) {

    }
    @Override
    public void onBindViewHolder(ProcessCardAdapter.CardViewHolder viewHolder, int position) {
        Process process = mProcesses.get(position);

        TextView nameTextView = viewHolder.processTitle;
        nameTextView.setText(process.getTitle());

        RecyclerView tasksRecyclerView = viewHolder.tasksScrollView;

        bindTasks(mContext, tasksRecyclerView, process.getTasks());
    }

    @Override
    public int getItemCount() {
        return mProcesses.size();
    }


    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView processTitle;
        RecyclerView tasksScrollView;

        CardViewHolder(View itemView) {
            super(itemView);
            processTitle = (TextView) itemView.findViewById(R.id.processTitle);
            tasksScrollView = (RecyclerView) itemView.findViewById(R.id.card_tasks_scrollview);
        }
    }

    void bindTasks(Context context, RecyclerView view, List<Task> tasks) {
        TasksCardAdapter taskAdapter = new TasksCardAdapter(context, tasks);
        view.setAdapter(taskAdapter);
        view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    static class TasksCardAdapter extends RecyclerView.Adapter<TasksCardAdapter.TaskViewHolder> {
        private List<Task> mTaskList;
        private Context mContext;

        public TasksCardAdapter(Context context, List<Task> taskList) {
            mContext = context;
            mTaskList = taskList;
        }

        @Override
        public TasksCardAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View processView = inflater.inflate(R.layout.task_card_item, parent, false);
            TaskViewHolder viewHolder = new TaskViewHolder(processView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(TasksCardAdapter.TaskViewHolder viewHolder, int position) {
            Task task = mTaskList.get(position);

            TextView title = viewHolder.taskTitle;
            title.setText(task.getTitle());
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
        static class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView taskTitle;

            TaskViewHolder(View itemView) {
                super(itemView);
                taskTitle = (TextView) itemView.findViewById(R.id.tasks_card_title);
            }
        }
    }
}


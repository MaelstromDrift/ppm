package edu.txstate.mjg.ppm.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.txstate.mjg.ppm.R;

public class CreateTaskDialog extends DialogFragment {

    public interface CreateTaskDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);

    }

    CreateTaskDialogListener mListener;

    EditText title;
    EditText description;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_task_create, null);
        builder.setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(CreateTaskDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(CreateTaskDialog.this);
                    }
                });
        title = (EditText) view.findViewById(R.id.dialog_task_title_form);
        description = (EditText) view.findViewById(R.id.dialog_task_description_form);
        return builder.create();
    }

    public String getTitle() {
        return title.getText().toString();
    }
    public String getDescription() {
        return description.getText().toString();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (CreateTaskDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CreateTaskDialogListener");
        }

    }
}

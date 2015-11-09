package com.example.leon.taskmanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Leon on 19.10.2015.
 */
public class DeleteForm extends DialogFragment {

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getTargetFragment() instanceof TaskList) {
                            ((TaskList) getTargetFragment()).onDeleteFormOkClick(position);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }
}

package com.example.leon.taskmanager.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

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
        final TextView text = new TextView(getActivity());
        text.setTextSize(20);
        text.setText("Are You Sure?");
        text.setPadding(0, 45, 0, 0);
        text.setGravity(1);

        return  new AlertDialog.Builder(getActivity())
                .setView(text)
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

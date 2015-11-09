package com.example.leon.taskmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leon.taskmanager.R;
import com.example.leon.taskmanager.content.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskListItem extends LinearLayout {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private TextView mTitle;
    private TextView mCreatedAt;
    private TextView mFinishedAt;

    public TaskListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindModel(Task task) {
        mTitle.setText(task.getmName());
        mCreatedAt.setText(DATE_FORMAT.format(new Date(task.getmCreatedAt())));
        mFinishedAt.setText(DATE_FORMAT.format(new Date(task.getmFinishedAt())));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.title);
        mCreatedAt = (TextView) findViewById(R.id.created_at);
        mFinishedAt = (TextView) findViewById(R.id.finished_at);
    }

}

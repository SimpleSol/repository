package com.example.leon.taskmanager.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.leon.taskmanager.R;
import com.example.leon.taskmanager.content.Task;
import com.example.leon.taskmanager.content.Tasks;
import com.example.leon.taskmanager.widget.TaskListAdapter;


public class TaskList extends Fragment {

    private ListView mListView;

    private ArrayAdapter<Task> mListAdapter;
    private int check = 0; // for checking either data was inserted or deleted
    private final ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            updateDatabase();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fmt_task_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteForm form = new DeleteForm();
                form.setPosition(position);
                form.setTargetFragment(TaskList.this, 2);
                form.show(getFragmentManager(), DeleteForm.class.getName());
                return true;
            }
        });
        getActivity().getContentResolver().registerContentObserver(Tasks.TABLE.getUri(), true, mObserver);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TaskListAdapter(getActivity());
        mListView.setAdapter(mListAdapter);
        crateListViewFromDatabase();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new) {
            NewTaskForm form = new NewTaskForm();
            form.setTargetFragment(this, 1);
            form.show(getFragmentManager(), NewTaskForm.class.getName());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNewTaskFormOkClick(String taskName) {
        final Cursor cursor = getActivity().getContentResolver().query(Tasks.TABLE.getUri(), null, null, null, null);
        check = cursor.getCount();
        ContentValues values = new ContentValues();
        values.put("task", taskName);
        values.put("createdAt", System.currentTimeMillis());
        getActivity().getContentResolver().insert(Tasks.TABLE.getUri(), values);
        cursor.close();
    }

    public void onDeleteFormOkClick(int position) {
        final Cursor cursor = getActivity().getContentResolver().query(Tasks.TABLE.getUri(), null, null, null, null);
        check = cursor.getCount();
        Task task = mListAdapter.getItem(position);
        String name = task.getmName();
        getActivity().getContentResolver().delete(Tasks.TABLE.getUri(), "task=?", new String[]{name});
        mListAdapter.remove(mListAdapter.getItem(position));
        cursor.close();

    }

    public void crateListViewFromDatabase() {
        final Cursor cursor = getActivity().getContentResolver().query(Tasks.TABLE.getUri(), null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Task task = new Task();
                task.setmName(cursor.getString(cursor.getColumnIndex("task")));
                task.setmCreatedAt(Long.valueOf(cursor.getString(cursor.getColumnIndex("createdAt"))));
                mListAdapter.add(task);
            } while (cursor.moveToNext());
        }
        Log.i("SQLITE", DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
    }

    public void updateDatabase() {
        final Cursor cursor = getActivity().getContentResolver().query(Tasks.TABLE.getUri(), null, null, null, null);
        if (cursor.moveToLast() && cursor.getCount() > check) {
            Task task = new Task();
            task.setmName(cursor.getString(cursor.getColumnIndex("task")));
            task.setmCreatedAt(Long.valueOf(cursor.getString(cursor.getColumnIndex("createdAt"))));
            mListAdapter.add(task);
        }
        Log.i("SQLITE", DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
    }


}

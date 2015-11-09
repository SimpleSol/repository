package com.example.leon.taskmanager.content;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.leon.taskmanager.sqlite.SQLiteProvider;
import com.example.leon.taskmanager.sqlite.SQLiteTable;

/**
 * Created by Leon on 08.11.2015.
 */
public class Tasks {

    public static final SQLiteTable TABLE = new SQLiteTable(SQLiteProvider.AUTHORITY, "tasks") {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + getName() + "(" +
                    "_id INTEGER PRIMARY KEY, " +
                    "task TEXT, " +
                    "createdAt TEXT);");
        }
    };

    public String getTask() {
        return "";
    }

    public String getCreatedAt() {
        return "";
    }

    public interface Columns extends BaseColumns {
        String TASK = "task";
        String CREATED_AT = "createdAt";
    }

}

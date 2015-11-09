package com.example.leon.taskmanager.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import com.example.leon.taskmanager.content.Tasks;

/**
 * Created by Leon on 08.11.2015.
 */
public class SQLiteProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.leon.taskmanager";

    private static final String DATABASE_NAME = "tasks.db";

    private static final int DATABASE_VERSION = 1;

    private SQLiteSchema mSchema;

    @Override
    public boolean onCreate() {
        mSchema = new SQLiteSchema(getContext(), DATABASE_NAME, DATABASE_VERSION);
        mSchema.addTable(Tasks.TABLE.getUri(), Tasks.TABLE);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String where, String[] whereArgs, String orderBy) {
        final SQLiteTable table = mSchema.acquireTable(uri);
        final Cursor cursor = table.query(mSchema.getReadableDatabase(), columns, where, whereArgs, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), table.getUri());
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final SQLiteTable table = mSchema.acquireTable(uri);
        return "vnd.android.cursor.dir/" + table.getName();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteTable table = mSchema.acquireTable(uri);
        final long rowId = table.insert(mSchema.getReadableDatabase(), values);
        if (rowId > 0) {
            final Uri result = ContentUris.withAppendedId(table.getUri(), rowId);
            getContext().getContentResolver().notifyChange(table.getUri(), null);
            return result;
        }
        throw new SQLiteException("Failed insert into " + table.getName());
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        final SQLiteTable table = mSchema.acquireTable(uri);
        final int affectedRows = table.delete(mSchema.getWritableDatabase(), where, whereArgs);
        if (affectedRows > 0) {
            getContext().getContentResolver().notifyChange(table.getUri(), null);
        }
        return affectedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        final SQLiteTable table = mSchema.acquireTable(uri);
        final int affectedRows = table.update(mSchema.getWritableDatabase(), values, where, whereArgs);
        if (affectedRows > 0) {
            getContext().getContentResolver().notifyChange(table.getUri(), null);
        }
        return affectedRows;
    }
}

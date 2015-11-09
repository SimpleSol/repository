package com.example.leon.taskmanager.content;

public class Task {

    private String mName;
    private long mCreatedAt;
    private long mFinishedAt;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(long mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public long getmFinishedAt() {
        return mFinishedAt;
    }

    public void setmFinishedAt(long mFinishedAt) {
        this.mFinishedAt = mFinishedAt;
    }
}

package com.seailz.eclipse.storage;

public interface StorageSystem {

    long getLastRun(String taskId);
    void setLastRun(String taskId, long time);
    void removeLastRun(String taskId);
    boolean taskExists(String taskId);

}

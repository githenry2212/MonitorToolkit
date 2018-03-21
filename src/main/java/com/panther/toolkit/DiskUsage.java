package com.panther.toolkit;

/**
 * @author yangfan
 * @since 2018/3/21 17:28
 */
public class DiskUsage {

    private String path;
    private long totalSpace;
    private long freeSpace;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }
}

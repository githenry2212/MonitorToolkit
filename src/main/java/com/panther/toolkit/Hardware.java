package com.panther.toolkit;

/**
 * @author yangfan
 * @since 2018/3/21 17:05
 */
public class Hardware {

    private String cpuName;
    private int cpuCores;
    private int cpuProcessors;
    private int cpuFrequency;
    private long memorySize;
    private long diskSize;

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getCpuProcessors() {
        return cpuProcessors;
    }

    public void setCpuProcessors(int cpuProcessors) {
        this.cpuProcessors = cpuProcessors;
    }

    public int getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(int cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(long diskSize) {
        this.diskSize = diskSize;
    }
}

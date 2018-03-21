package com.panther.toolkit;

import com.panther.toolkit.exception.MonitorException;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 监控工具类
 *
 * @author yangfan
 * @since 2018/3/21 17:02
 */
public abstract class MonitorToolkit {

    private static MonitorToolkit toolkit;
    protected OperatingSystemMXBean systemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private final Hardware hardware;

    MonitorToolkit() throws MonitorException {
        hardware = new Hardware();
        initCpu(hardware);
        initMemory(hardware);
        initDisk(hardware);
    }

    /**
     * 返回一个可用的Toolkit对象
     *
     * @return MonitorToolkit
     * @throws MonitorException ex
     */
    public static MonitorToolkit getToolkit() throws MonitorException {
        if (toolkit == null) {
            if (SystemUtils.isWindows()) {
                toolkit = new WindowsMonitorToolkit();
            } else if (SystemUtils.isLinux()) {
                toolkit = new LinuxMonitorToolkit();
            } else {
                throw new MonitorException("unsupported system: " + SystemUtils.getOsName());
            }
        }
        return toolkit;
    }

    /**
     * 初始化CPU信息
     *
     * @param hardware 硬件信息
     * @throws MonitorException 异常
     */
    abstract void initCpu(Hardware hardware) throws MonitorException;

    /**
     * 初始化内存信息
     *
     * @param hardware 硬件信息
     * @throws MonitorException 异常
     */
    void initMemory(Hardware hardware) throws MonitorException {
        hardware.setMemorySize(systemMXBean.getTotalPhysicalMemorySize());
    }

    /**
     * 初始磁盘信息
     *
     * @param hardware 硬件信息
     * @throws MonitorException 异常
     */
    abstract void initDisk(Hardware hardware) throws MonitorException;

    /**
     * 返回基本硬件信息
     *
     * @return 硬件信息
     */
    public Hardware getHardware() {
        return hardware;
    }

    /**
     * 当前进程的CPU占用率
     *
     * @return CPU占用率
     */
    public double getProcessCpuLoad() {
        return checkCpuLoad(systemMXBean.getProcessCpuLoad());
    }

    /**
     * 系统当前的CPU占用率
     *
     * @return CPU占用率
     */
    public double getSystemCpuLoad() {
        return checkCpuLoad(systemMXBean.getSystemCpuLoad());
    }

    /**
     * 剩余可用内存大小
     *
     * @return 内存大小, 单位字节
     */
    public long getFreeMemorySize() {
        return systemMXBean.getFreePhysicalMemorySize();
    }

    /**
     * swap space总大小
     *
     * @return swap space大小
     */
    public long getTotalSwapSpace() {
        return systemMXBean.getTotalSwapSpaceSize();
    }

    /**
     * swap space剩余大小
     *
     * @return swap space剩余大小
     */
    public long getFreeSwapSpace() {
        return systemMXBean.getFreeSwapSpaceSize();
    }

    /**
     * 磁盘使用信息
     *
     * @return 磁盘使用信息
     */
    public abstract List<DiskUsage> getDiskUsage() throws MonitorException;

    private double checkCpuLoad(double cpuLoad) {
        if (Double.compare(cpuLoad, 0) < 0 || Double.compare(cpuLoad, 1) > 0) {
            return 0;
        }
        return cpuLoad;
    }
}

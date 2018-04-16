package com.panther.toolkit;

import com.panther.toolkit.exception.MonitorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangfan
 * @since 2018/3/21 17:10
 */
final class LinuxMonitorToolkit extends MonitorToolkit {
    LinuxMonitorToolkit() throws MonitorException {
    }

    @Override
    void initCpu(Hardware hardware) throws MonitorException {
        try {
            hardware.setCpuName(getCpuName());
            hardware.setCpuCores(getPhysicalCores());
            hardware.setCpuProcessors(Runtime.getRuntime().availableProcessors());
        } catch (IOException e) {
            throw new MonitorException("获取CPU信息失败", e);
        }
    }

    private int getPhysicalCores() throws IOException {
        List<String> cmdList = Arrays.asList("sh", "-c", "cat /proc/cpuinfo | grep \"physical id\" | uniq | wc -l");
        String result = CommandExecutor.execute(cmdList);
        return Integer.parseInt(result.trim());
    }

    private String getCpuName() throws IOException {
        List<String> cmdList = Arrays.asList("sh", "-c", "cat /proc/cpuinfo | grep name | cut -d: -f2 | uniq");
        String result = CommandExecutor.execute(cmdList);
        return result.trim();
    }

    @Override
    void initDisk(Hardware hardware) throws MonitorException {
        List<String> cmdList = Arrays.asList("sh", "-c", "df | tail -n +2 | awk '{sum+=$2} END {print sum}'");
        try {
            String result = CommandExecutor.execute(cmdList);
            if (StringUtils.isNotEmpty(result)) {
                hardware.setDiskSize(Long.parseLong(result) << 10);
            }
        } catch (IOException e) {
            throw new MonitorException("获取磁盘信息失败", e);
        }
    }

    @Override
    public List<DiskUsage> getDiskUsage() throws MonitorException {
        List<String> cmdList = Arrays.asList("sh", "-c", "df | tail -n +2 | awk '{print $6\",\"$2\",\"$4}'");
        try {
            String result = CommandExecutor.execute(cmdList).trim();
            String[] lines = result.split("\n");
            List<DiskUsage> usages = new ArrayList<>(lines.length);
            for (String line : lines) {
                String[] content = line.split(",");
                DiskUsage usage = new DiskUsage();
                usage.setPath(content[0]);
                usage.setTotalSpace(Long.parseLong(content[1]) << 10);
                usage.setFreeSpace(Long.parseLong(content[2]) << 10);
                usages.add(usage);
            }
            return usages;
        } catch (IOException e) {
            throw new MonitorException("获取磁盘信息失败", e);
        }
    }
}

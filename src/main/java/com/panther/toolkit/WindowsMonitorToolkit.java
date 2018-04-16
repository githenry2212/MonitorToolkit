package com.panther.toolkit;

import com.panther.toolkit.exception.MonitorException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yangfan
 * @since 2018/3/21 17:07
 */
final class WindowsMonitorToolkit extends MonitorToolkit {
    WindowsMonitorToolkit() throws MonitorException {
    }

    @Override
    void initCpu(Hardware hardware) throws MonitorException {
        hardware.setCpuProcessors(Runtime.getRuntime().availableProcessors());
        String cmd = "wmic cpu get Name,NumberOfCores,CurrentClockSpeed /VALUE";
        try {
            String result = CommandExecutor.execute(cmd);
            String separator = "\\r+\\n";
            String[] lines = result.split(separator);
            for (String line : lines) {
                if (StringUtils.isNotEmpty(line)) {
                    parseLine(line, hardware);
                }
            }
        } catch (IOException e) {
            throw new MonitorException("获取CPU信息失败", e);
        }
    }

    private void parseLine(String line, Hardware hardware) {
        int idx = line.indexOf('=');
        if (idx != -1) {
            String key = line.substring(0, idx);
            String value = line.substring(idx + 1);
            switch (key) {
                case "Name":
                    hardware.setCpuName(value);
                    break;
                case "NumberOfCores":
                    hardware.setCpuCores(Integer.parseInt(value.trim()));
                    break;
                case "CurrentClockSpeed":
                    hardware.setCpuFrequency(Double.valueOf(value.trim()).intValue());
                    break;
                default:
            }
        }
    }

    @Override
    void initDisk(Hardware hardware) {
        long total = 0;
        for (File file : File.listRoots()) {
            total += file.getTotalSpace();
        }
        hardware.setDiskSize(total);
    }

    @Override
    public List<DiskUsage> getDiskUsage() {
        File[] roots = File.listRoots();
        List<DiskUsage> usages = new ArrayList<>(roots.length);
        for (File root : roots) {
            DiskUsage usage = new DiskUsage();
            usage.setPath(root.getPath());
            usage.setTotalSpace(root.getTotalSpace());
            usage.setFreeSpace(root.getFreeSpace());
            usages.add(usage);
        }
        return usages;
    }
}

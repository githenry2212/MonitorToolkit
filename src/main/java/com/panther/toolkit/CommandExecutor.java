package com.panther.toolkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author yangfan
 * @since 2018/3/21 17:55
 */
class CommandExecutor {


    public static String execute(String command) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(command);
        List<String> cmdList = new ArrayList<>(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            cmdList.add(tokenizer.nextToken());
        }
        return execute(cmdList);
    }

    public static String execute(List<String> cmdList) throws IOException {
        Process process = null;
        try {
            process = buildProcess(cmdList);
            return IOUtils.asString(process.getInputStream());
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static Process buildProcess(List<String> cmdList) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        builder.redirectErrorStream(true);
        return builder.start();
    }
}

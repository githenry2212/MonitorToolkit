package com.panther.toolkit;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author yangfan
 * @since 2018/3/21 17:32
 */
public class IOUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static String asString(InputStream in) throws IOException {
        return asString(in, DEFAULT_CHARSET);
    }

    public static String asString(InputStream in, Charset cs) throws IOException {
        try {
            StringBuilder builder = new StringBuilder(in.available());
            byte[] bytes = new byte[128];
            int len;
            while ((len = in.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len, cs));
            }
            return builder.toString();
        } finally {
            closeQuietly(in);
        }
    }

    public static void closeAll(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                closeQuietly(closeable);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}

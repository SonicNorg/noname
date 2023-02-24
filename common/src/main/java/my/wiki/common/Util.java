package my.wiki.common;

import java.nio.charset.StandardCharsets;

public final class Util {
    private Util() {
    }

    public static int hash(String str) {
        int result = 0;
        for (byte b : str.getBytes(StandardCharsets.UTF_8)) {
            result += b;
        }
        return result;
    }
}

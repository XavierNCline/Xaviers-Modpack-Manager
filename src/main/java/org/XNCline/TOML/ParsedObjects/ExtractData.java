package org.XNCline.TOML.ParsedObjects;

import java.io.File;
import java.nio.file.FileSystem;

public class ExtractData {
    private static String configZip;
    private static String modZip;
    private static boolean downloadMode;

    public ExtractData(String configZip, String modZip, boolean downloadMode) {
        ExtractData.configZip = configZip;
        ExtractData.modZip = modZip;
        ExtractData.downloadMode = downloadMode;
    }

    public static String getConfigZip() {
        return configZip;
    }

    public static String getModZip() {
        return modZip;
    }

    public static boolean isDownloadMode() {
        return downloadMode;
    }

    //TODO: There's got to be a better way for this, but I hit fuck it with this part.
    //I hate String parsing so much.
    private String parsePaths(String input) {
        StringBuffer output = new StringBuffer();
        String winStyle = "\\"; //I hate this, but I don't know how to do it better.
        boolean windowsSlashFlag = false;

        for (char ch : input.toCharArray()) {
            if (ch == winStyle.charAt(0) || ch == '/' && ch != File.separatorChar) {
                ch = File.separatorChar;
                windowsSlashFlag = true;
            } else {
                windowsSlashFlag = false;
            }

            if (!windowsSlashFlag)
                output.append(ch);
        }

        return output.toString();
    }
}

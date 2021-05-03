package org.XNCline.FileIO;

public enum SupportedArchives {
    SEVENZIP(".7z"),
    STDZIP(".zip");

    private final String ext;

    SupportedArchives(final String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }
}

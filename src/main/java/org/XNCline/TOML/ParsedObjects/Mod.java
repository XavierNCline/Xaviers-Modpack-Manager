package org.XNCline.TOML.ParsedObjects;

public class Mod {
    private final String name;
    private final String desc;
    private final String filename;
    private final String URL;

    public Mod (String name, String desc, String filename, String URL) {
        this.name = name;
        this.desc = desc;
        this.filename = filename;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getFilename() {
        return filename;
    }

    public String getURL() {
        return URL;
    }
}

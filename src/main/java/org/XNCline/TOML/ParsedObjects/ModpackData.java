package org.XNCline.TOML.ParsedObjects;

import java.util.ArrayList;
import java.util.List;

public class ModpackData {
    private static String owner;
    private static String name;
    private static String desc;
    private static String mmVer;
    private static String mcVer;
    private static String fVer;

    private static List<Mod> mods = new ArrayList<>();

    public ModpackData(String owner, String name, String desc, String mmVer, String mcVer, String fVer) {
        ModpackData.owner = owner;
        ModpackData.name = name;
        ModpackData.desc = desc;
        ModpackData.mmVer = mmVer;
        ModpackData.mcVer = mcVer;
        ModpackData.fVer = fVer;
    }

    public static void addMod(Mod mod) {
        mods.add(mod);
    }

    public static List<Mod> getMods() {
        return mods;
    }

    public static String getOwner() {
        return owner;
    }

    public static String getName() {
        return name;
    }

    public static String getDesc() {
        return desc;
    }

    public static String getMmVer() {
        return mmVer;
    }

    public static String getMcVer() {
        return mcVer;
    }

    public static String getfVer() {
        return fVer;
    }
}

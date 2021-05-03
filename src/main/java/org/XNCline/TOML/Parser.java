package org.XNCline.TOML;

import org.XNCline.TOML.ParsedObjects.*;
import org.XNCline.XMM;
import org.tomlj.Toml;
import org.tomlj.TomlParseError;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    public Parser(File source) {
        Path path = Paths.get(source.getPath());
        TomlParseResult result = null;
        try {
            result = Toml.parse(path);
        } catch (IOException e) {
            XMM.logger.fatal("Failed parsing .toml file!", e);
            System.exit(1);
        }

        if(result.hasErrors()) {
            XMM.logger.error("Parse errors detected in modpack.toml. Listing errors now.");
            int i = 0;

            for(TomlParseError error : result.errors()) {
                i++;
                XMM.logger.error("Error #" + i);
                XMM.logger.error(error.getMessage());
                XMM.logger.error("Line: " + error.position().line() + " Column: " + error.position().column());
            }

            XMM.logger.fatal("Cannot continue with parse errors. Exiting now.");
            System.exit(1);
        }
        XMM.logger.info("Successfully read modpack.toml. Sorting data...");

        List<TomlTable> mods = getModsAsList(result.getTable("mod"));
        TomlTable meta = result.getTable("meta");
        TomlTable extract = result.getTable("mods");

        new ModpackData(meta.getString("owner"), meta.getString("name"),
                meta.getString("description"), meta.getString("version"),
                meta.getString("MCVersion"), meta.getString("ForgeVersion"));
        XMM.logger.info("Loaded meta data for " + meta.getString("name")  + " created by " +
                meta.getString("owner") + "!");

        for (TomlTable mod : mods) {
            ModpackData.addMod(new Mod(mod.getString("name"), mod.getString("desc"),
                    mod.getString("filename"), mod.getString("URL")));
            XMM.logger.info("Found and indexed mod " + mod.getString("name") + ".");
        }


        new ExtractData(extract.getString("ConfigZip"), extract.getString("ModZip"),
                extract.getBoolean("DownloadMode"));
        XMM.logger.info("Registered zip containing mods as " + ExtractData.getModZip() + ".");
        XMM.logger.info("Registered zip containing config as " + ExtractData.getConfigZip() + ".");
    }

    private List<TomlTable> getModsAsList(TomlTable base) {
        List<TomlTable> mods = new ArrayList<>();
        Map<String, Object> keys = base.toMap();

        for (String mod : keys.keySet()) {
            mods.add((TomlTable) keys.get(mod));
        }

        return mods;
    }
}

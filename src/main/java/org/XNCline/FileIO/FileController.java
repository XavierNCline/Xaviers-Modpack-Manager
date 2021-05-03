package org.XNCline.FileIO;

import org.XNCline.XMM;
import org.XNCline.TOML.ParsedObjects.*;

import java.io.File;
import java.io.IOException;

public class FileController {


    public FileController() {
        selfCheck();
        XMM.logger.info("Beginning extraction of config and mod files!");
        try {
            ArchiveHandler archiveHandler = new ArchiveHandler(getExt(new File(ExtractData.getConfigZip()).getName()));
        } catch (IOException e) {
            XMM.logger.fatal("A fatal error occurred when processing the archives. XMM will now close.", e);
            System.exit(1);
        }

    }

    private void selfCheck() {
        boolean verified = true;
        File[] dirs = new File[] {new File("mods"), new File("config")};
        File[] extractTargets = new File[] {new File(ExtractData.getModZip()), new File(ExtractData.getConfigZip())};

        for (File dir : dirs) {
            XMM.logger.info(dir.getName() + " folder present? " + dir.exists());
            if (!dir.exists() || dir.isFile()) {
                XMM.logger.error(dir.getName() + " either does not exist or is not a directory!");
                verified = false;
            }
        }

        for (File target : extractTargets) {
            XMM.logger.info("Target file " + target.getName() + " exists? " + target.exists());

            if (!target.exists()) {
                XMM.logger.error("Could not find " + target.getName() + "!");
                verified = false;
            } else if (getExt(target.getName()).equals(null)) {
                XMM.logger.error(target.getName() + " is either not an archive, or is incompatible!");
                verified = false;
            }
        }

        if (!verified) {
            XMM.logger.fatal("One or more files/directories failed verification! Exiting now.");
            System.exit(0);
        }
    }

    private SupportedArchives getExt(String fileName) {
        StringBuffer ext = new StringBuffer();
        boolean flag = false;

        for (char index : fileName.toCharArray()) {
            if (index == '.')
                flag = true;

            if (flag)
                ext.append(index);
        }

        for (SupportedArchives e : SupportedArchives.values()) {
            if (e.getExt().equalsIgnoreCase(ext.toString()))
                return e;
        }

        return null;
    }
}
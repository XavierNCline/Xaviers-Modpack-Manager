package org.XNCline;

import org.XNCline.FileIO.FileController;
import org.XNCline.TOML.*;
import org.XNCline.TOML.ParsedObjects.ExtractData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class XMM {
    public static Logger logger = LogManager.getLogger("XMM");

    public static void main(String[] args) {
        logger.info("Welcome to Xavier's Modpack Manager!");
        logger.info("Version 0.0.1");
        logger.info("Checking for modpack.toml file.");
        File modpack = new File("modpack.toml");

        if(!modpack.exists()) {
            logger.error("Could not find modpack.toml file. Cannot continue.");

            try {
                generateDefault();
                logger.debug("File generation complete.");
                logger.info("Default file generated. Please refer to the github for configuration help.");
                System.exit(0);
            } catch (IOException e) {
                logger.fatal("Failed to generate default file.", e);
                System.exit(1);
            }
        }

        logger.info("Attempting to parse modpack.toml file.");
        new Parser(modpack);
        logger.info("Successfully parsed modpack.toml!");

        if (ExtractData.isDownloadMode())
            logger.warn("Download mode is set to true, but download mode is not supported yet! Ignoring.");

        logger.info("Searching for target /mods and /config folders...");
        new FileController();
    }

    private static void generateDefault() throws IOException {
        logger.debug("Beginning default file generation.");
        File dest = new File("modpack.toml");
        InputStream is = null;
        OutputStream os = null;
        try {
            dest.createNewFile();
            is = XMM.class.getClassLoader().getResourceAsStream("default.toml");
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0 , length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
package org.XNCline.FileIO;

import org.XNCline.TOML.ParsedObjects.ExtractData;
import org.XNCline.XMM;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveHandler {
    private final File[] DEST = new File[] {new File("config"), new File("mods")};

    public ArchiveHandler(SupportedArchives archType) throws IOException {
        if (archType.equals(SupportedArchives.STDZIP)) {
            XMM.logger.debug("Standard .zip archive detected.");
            extractZip(DEST[0], new File(ExtractData.getConfigZip()));
            extractZip(DEST[1], new File(ExtractData.getModZip()));
        } else if (archType.equals(SupportedArchives.SEVENZIP)) {
            XMM.logger.debug("7-zip .7z archive detected.");
            extract7Z(DEST[0], new File(ExtractData.getConfigZip()));
            extract7Z(DEST[1], new File(ExtractData.getModZip()));
        }
    }

    private void extract7Z(File dir, File target) throws IOException {
        SevenZFile arch = new SevenZFile(target);
        SevenZArchiveEntry entry;

        XMM.logger.info("Extracting " + target.getName() + " to " + dir.getName());

        while ((entry = arch.getNextEntry()) != null) {
            if (entry.isDirectory())
                continue;

            File curFile = new File(dir, entry.getName());
            File parent = curFile.getParentFile();

            if (!parent.exists())
                parent.mkdirs();

            FileOutputStream out = new FileOutputStream(curFile);
            byte[] data = new byte[(int) entry.getSize()];
            arch.read(data, 0, data.length);
            out.write(data);
            out.close();
        }
    }

    private void extractZip(File dir, File arch) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(arch));
        ZipEntry entry = zipIn.getNextEntry();

        XMM.logger.info("Extracting " + arch.getName() + " to " + dir.getName());

        while (entry != null) {
            String path = dir.getPath() + File.separator + entry.getName();

            if (!entry.isDirectory()) {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
                final int BUFFER_SIZE = 4096;
                byte[] bytesIn = new byte[BUFFER_SIZE];
                int read = 0;

                while ((read = zipIn.read(bytesIn)) != -1) {
                    bos.write(bytesIn, 0 , read);
                }
                bos.close();
            } else {
                new File(path).mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
}

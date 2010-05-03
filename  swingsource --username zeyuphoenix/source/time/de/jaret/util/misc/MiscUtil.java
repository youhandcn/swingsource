/*
 *  File: MiscUtil.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Some static helper methods (mainly concerned with String manipulation).
 * 
 * @author Peter Kliem
 * @version $Id: MiscUtil.java 250 2007-02-12 00:15:49Z olk $
 */
public class MiscUtil {
    /**
     * Retrieve the filename from a complete path string.
     * 
     * @param path path including filename (path separator "/" or "\")
     * @return filename (last part of the path)
     */
    public static String getFilename(String path) {
        int idx = path.lastIndexOf("\\");
        if (idx == -1) {
            idx = path.lastIndexOf("/");
        }
        if (idx == -1) {
            return path;
        } else {
            return path.substring(idx + 1);
        }
    }

    /**
     * Extract path from a complete path including filename.
     * 
     * @param path path including filename (path separator "/" or "\")
     * @return path (path without last element)
     */
    public static String getPath(String path) {
        int idx = path.lastIndexOf("\\");
        if (idx == -1) {
            idx = path.lastIndexOf("/");
        }
        if (idx == -1) {
            return path;
        } else {
            return path.substring(0, idx);
        }

    }

    /**
     * Read a textfile into a StringBuffer.
     * 
     * @param completePath complete path of the file
     * @return StringBuffer
     */
    public static StringBuffer readTextFile(String completePath) {
        StringBuffer buf = new StringBuffer();
        File file = new File(completePath);
        try {
            FileReader fr = new FileReader(file);
            char buffer[] = new char[1024];
            int read = 1;
            while (read > 0) {
                read = fr.read(buffer);
                if (read > 0) {
                    buf.append(buffer, 0, read);
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File could not be read " + completePath + " " + e.getLocalizedMessage());
        }
        return buf;
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        InputStream src = new FileInputStream(srcFile);
        OutputStream dest = new FileOutputStream(destFile);
        byte buffer[] = new byte[1024];
        int read = 1;
        while (read > 0) {
            read = src.read(buffer);
            if (read > 0) {
                dest.write(buffer, 0, read);
            }
        }
        src.close();
        dest.close();
    }

    /**
     * Removes leading whitespaces.
     * 
     * @param str String to treat
     * @return str without leading whitespaces
     */
    public static String leftTrim(String str) {
        if (str.length() == 0) {
            return str;
        }
        int i = 0;
        while (i < str.length()) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else {
                break;
            }
        }
        return str.substring(i);
    }

    /**
     * Removes trailing whitespaces.
     * 
     * @param str String to treat
     * @return str without trailing whitespaces
     */
    public static String rightTrim(String str) {
        if (str.length() == 0) {
            return str;
        }
        int i = str.length() - 1;
        while (i >= 0) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c)) {
                i--;
            } else {
                break;
            }
        }
        return str.substring(0, i + 1);
    }

}

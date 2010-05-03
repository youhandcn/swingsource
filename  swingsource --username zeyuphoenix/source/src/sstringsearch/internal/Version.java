/*
 * Copyright 2005-2008 Kristian Andersen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package sstringsearch.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> Version.java<br/>
 * <b>Class:</b> Version<br/>
 * <b>Created:</b> 5. feb.. 2007<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   Prints the version of the API.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
public class Version {

    /**
     * The projects groupId
     */
    private final static String GROUP_ID = "net.sourceforge";

    /**
     * The projects artifactId
     */
    private final static String ARTIFACT_ID = "sstringsearch";

    /**
     * Holds the properties of pom.properties
     */
    private final static Properties props = new Properties();

    /**
     * Static initializer reading the properties from pom.properties.
     */
    static {
        InputStream propsInputStream = null;
        try {
            propsInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/maven/" + GROUP_ID + "/" + ARTIFACT_ID + "/pom.properties");
            props.load(propsInputStream);
        }
        catch(IOException e) {
            System.out.println("Unable to load pom.properties");
            e.printStackTrace();
        }
        finally {
            if(propsInputStream != null) {
                try {
                    propsInputStream.close();
                }
                catch(IOException ignore) {
                }
            }
        }
    }

    /**
     * 
     * Writes the version info to system out.
     * 
     * @param args Takes no command-line parameters.
     */
    public static void main(final String[] args) {
        final String str = listAllProperties(props);
        System.out.print('\n');
        System.out.print(str);
        System.out.print('\n');
    }

    /**
     * 
     * Returns a property from pom.properties
     * 
     * @param key The property key.
     * @return The property value, or null if the property is not set.
     */
    public static String getProperty(final String key) {
        return props.getProperty(key);
    }

    /**
     * 
     * Returns the version property from pom.properties
     * 
     * @return The version property from pom.properties
     */
    public static String getVersion() {
        return getProperty("version");
    }

    /**
     * 
     * Formats a Properties object (IMHO nicer than Properties.toString() and Properties.list())
     * 
     * @param props Properties object.
     * @return A nicely formatted String representation of the properties object.
     */
    public static String listAllProperties(final Properties props) {
        final StringBuilder buf = new StringBuilder();
        Enumeration<Object> keys = props.keys();
        int max = Integer.MIN_VALUE;
        while(keys.hasMoreElements()) {
            final String key = (String) keys.nextElement();
            if(key.length() > max) {
                max = key.length();
            }
        }
        keys = props.keys();
        while(keys.hasMoreElements()) {
            final String key = (String) keys.nextElement();
            final String value = props.getProperty(key);
            buf.append(Character.toUpperCase(key.charAt(0)));
            buf.append(key.substring(1));
            for(int i = 0; i < (max - key.length()); i++) {
                buf.append(' ');
            }
            buf.append(" : ");
            buf.append(value);
            if(keys.hasMoreElements()) {
                buf.append('\n');
            }
        }
        return buf.toString();
    }
}

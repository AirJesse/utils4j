package com.lujiachao.support.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Created by cyril on 17-2-14.
 */
public class LjcClassLoaderUtils {
    private final static List<String> JAR_VERSION_ATTRIBUTES = Arrays.asList(new String[]
            {"Build-Version",
                    "Specification-Version",
                    "Implementation-Version",
                    "Version"
            });

    public final static String DEFAULT_JAR_VERSION = "";


    public static String getClassJarFileVersion(Class<?> clazz) {
        return getJarVersion(getClassJarFile(clazz));
    }


    public static String getJarVersion(File jarFile)  {
        if (jarFile != null && jarFile.exists() && jarFile.isFile()) {
            if (jarFile.getPath().endsWith(".jar")) {
                try {
                    JarFile jar = new JarFile(jarFile);
                    Attributes attrs = jar.getManifest().getMainAttributes();
                    for (Iterator it = attrs.keySet().iterator(); it.hasNext();) {
                        Attributes.Name attri = (Attributes.Name) it.next();
                        if (JAR_VERSION_ATTRIBUTES.contains(attri.toString())) {
                            return attrs.getValue(attri);
                        }
                    }
                    return DEFAULT_JAR_VERSION;
                } catch (Exception ex) {
                    return DEFAULT_JAR_VERSION;
                }
            }else{
                return DEFAULT_JAR_VERSION;
            }
        } else {
            return DEFAULT_JAR_VERSION;
        }
    }


    public static File getClassJarFile(Class<?> clazz) {
        return new File(clazz.getProtectionDomain().getCodeSource().getLocation().getFile());
    }

    public static void addJarFileToClassLoader(File file) throws Exception {
        Class<?> clazz = URLClassLoader.class;
        Method method = clazz.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        URI uri = file.toURI();
        method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{uri.toURL()});

    }



}

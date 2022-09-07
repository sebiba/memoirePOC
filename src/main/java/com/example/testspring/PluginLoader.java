package com.example.testspring;

import com.example.testspring.Interface.PluginInterface;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

public class PluginLoader {
    List<Class<?>> Plugins = new ArrayList<>();
    List<String> files = new ArrayList<>();

    public PluginLoader() {
        this.getAllJarInFolder("plugins");
    }
    public PluginLoader(String folder) {
        this.getAllJarInFolder(folder);
    }


    public void getAllJarInFolder(String folder) {
        File directory = new File(folder);
        if(!directory.exists()) directory.mkdir();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith("jar")) {
                files.add(file.getPath());
            }
        }
    }

    /**
     * Fonction de chargement de tous les plugins de type Interpreter
     *
     * @return Une collection de StringPlugins contenant les instances des plugins
     * @throws Exception si file = null ou file.length = 0
     */
    public PluginInterface[] loadAllPlugins() throws Exception {

        this.initializeLoader();

        PluginInterface[] tmpPlugins = new PluginInterface[this.Plugins.size()];

        for (int index = 0; index < tmpPlugins.length; index++) {
            tmpPlugins[index] = (PluginInterface)this.Plugins.get(index).getConstructor().newInstance();
        }

        return tmpPlugins;
    }

    private void initializeLoader() throws Exception {
        if (this.files == null || this.files.size() == 0) {
            throw new Exception("no file specified");
        }

        File[] f = new File[this.files.size()];
        URLClassLoader loader;
        String tmp;
        Enumeration<java.util.jar.JarEntry> enumeration;
        Class<?> tmpClass;

        for (int index = 0; index < f.length; index++) {
            f[index] = new File(files.get(index));
            if (!f[index].exists()) {
                break;
            }

            URL u = f[index].toURL();
            //On crée un nouveau URLClassLoader pour charger le jar qui se trouve en dehors du CLASSPATH
            final ClassLoader parent = this.getClass().getClassLoader();
            loader = new URLClassLoader(new URL[]{u}, parent);

            try(JarFile jar = new JarFile(f[index].getAbsolutePath())) {
                enumeration = jar.entries();
                while (enumeration.hasMoreElements()) {

                    tmp = enumeration.nextElement().toString();

                    //On vérifie que le fichier courant est un .class (et pas un fichier d'informations du jar )
                    if (tmp.length() > 6 && tmp.substring(tmp.length() - 6).compareTo(".class") == 0) {

                        tmp = tmp.substring(0, tmp.length() - 6);
                        tmp = tmp.replaceAll("/", ".");

                        tmpClass = Class.forName(tmp, true, loader);

                        for (int i = 0; i < tmpClass.getInterfaces().length; i++) {
                            if(tmpClass.getInterfaces()[i].toString().equals("interface com.example.testspring.Interface.PluginInterface") ) {
                                this.Plugins.add(tmpClass);
                            }
                        }

                    }
                }
            }
            catch(Throwable e){
                e.printStackTrace();
            }
        }

    }
}
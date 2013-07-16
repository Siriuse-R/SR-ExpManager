/**
 * Created: 2013/07/06 12:45:03
 * @author tsuttsu305
 * License: GNU LGPLv3
 */
package net.tsuttsu305.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * JarUtil (JarUtil.java)
 * @author tsuttsu305
 */
public class JarUtil {
    private JarUtil() {}
    
    /**
     * Jarからファイル取り出して外にコピーする
     * @param jarFile 欲しいファイルが入ってるjarファイル
     * @param destFile 宛先File(ディレクトリではない)
     * @param sourcePath jarファイルの中の欲しいファイルのパス
     * @return 
     */
    public static boolean copyJarResource(File jarFile, File destFile, String sourcePath) {
        if (!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        
        InputStream input = null;
        FileOutputStream output = null;
        JarFile jar = null;
        try {
            jar = new JarFile(jarFile);
            
            ZipEntry entry = jar.getEntry(sourcePath);
            
            input = jar.getInputStream(entry);
            output = new FileOutputStream(destFile);
            
            //copy
            byte[] buf = new byte[1024];
            int len;
            while ((len = input.read(buf)) != -1) {
                output.write(buf, 0, len);
            }
            
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if (jar != null){
                try {jar.close();} catch (IOException e) {}
            }
            if (output != null){
                try {output.close();} catch (IOException e) {}
            }
        }
        
        return true;
    }
}

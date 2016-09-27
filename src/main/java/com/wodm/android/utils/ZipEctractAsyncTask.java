package com.wodm.android.utils;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by moon1 on 2016/5/12.
 */
public class ZipEctractAsyncTask extends AsyncTask<String, Void, File> {
    /**
     * 解压缩
     *
     * @param sZipPathFile 要解压的文件
     * @param sDestPath    解压到某文件夹
     * @return
     */
    @SuppressWarnings("unchecked")
    public static File Ectract(String sZipPathFile, String sDestPath) {
//        ArrayList<String> allFileName = new ArrayList<String>();
        try {
            if (!new File(sDestPath).exists() || !new File(sDestPath).isDirectory()) {
                new File(sDestPath).mkdirs();
            }
            // 先指定压缩档的位置和档名，建立FileInputStream对象
            FileInputStream fins = new FileInputStream(sZipPathFile);
            // 将fins传入ZipInputStream中
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte[] ch = new byte[1024];
            while ((ze = zins.getNextEntry()) != null) {
                String filename = ze.getName().substring(0, ze.getName().lastIndexOf(".")) + ".cache";
                File zfile = new File(sDestPath + filename);
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists())
                        zfile.mkdirs();
                    zins.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
//                    allFileName.add(zfile.getAbsolutePath());
                    while ((i = zins.read(ch)) != -1)
                        fouts.write(ch, 0, i);
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fins.close();
            zins.close();
            return new File(sDestPath);
        } catch (Exception e) {
            System.err.println("Extract error:" + e.getMessage());
            return null;
        }
    }

    @Override
    protected File doInBackground(String... params) {
        FileUtils.deleteFile(params[1]);
        return Ectract(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (zipEctractCallback == null) {
            return;
        }
        if (file != null) {
            zipEctractCallback.onEctractSucess(file);
        } else {
            zipEctractCallback.onEctractFaulite(file);
        }
    }

    ZipEctractCallback zipEctractCallback;

    public void setZipEctractCallback(ZipEctractCallback zipEctractCallback) {
        this.zipEctractCallback = zipEctractCallback;
    }

    public interface ZipEctractCallback {

        void onEctractSucess(File file);

        void onEctractFaulite(File file);
    }
}

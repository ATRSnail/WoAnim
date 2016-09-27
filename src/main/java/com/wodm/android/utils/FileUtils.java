/**
 *
 */
package com.wodm.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @description 有关文件操作工具
 */
public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/unicom.dm/";

    public static String saveBitmap(Bitmap bm, String picName) {
        String path = "";
        Log.e("", "保存图片");
        try {
            File f = new File(SDPATH + "formats/", picName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out);
            path = f.getAbsolutePath();
            out.flush();
            out.close();

            Log.e("", "已经保存");

            return path;
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        }

    }

    /**
     * 文件大小转换
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "0.00";
        if (fileS == 0) {
        } else if (fileS < 1024) {
//			fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(String file) {
        return getFolderSize(getFile(file));
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {

            if (file.isDirectory()) {
                java.io.File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);

                    } else {
                        size = size + fileList[i].length();

                    }
                }
                return size;
            } else {

                if (file != null && file.exists()) {
                    return file.length();
                }
                return 0;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return size/1048576;
        return size;

    }

    public static File getFile(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return null;
        } else {
            return new File(dir);
        }
    }

    public static void deleteFile(String file) {
        deleteFile(new File(file));
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int index = 0; index < files.length; index++) {
                File f = files[index];
                if (f.exists()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFile(f);
                    }
                }
            }

            if (file != null && file.listFiles().length == 0) {
                file.delete();
            }

        } else {
            file.delete();
        }

    }


    /**
     * 判断外部存储(SD卡)是否可用。
     *
     * @return
     */
    public static boolean isExternalStorageAvail() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getCachePath(Context c, int maxSize) {
        // 初始化缓存文件夹，默认使用手机内部存储卡
        String _externalCachePath = "/sdcard/Android/data/"
                + c.getPackageName() + "/cache/";
        String _internalCachePath = c.getCacheDir().getAbsolutePath() + "/";

        // TODO structure to be improved
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 有sdcard可用
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
                    .getAbsolutePath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            if (availCount * blockSize > maxSize) {
                // sdcard可用空间充足
                File cacheFolder = new File(_externalCachePath);
                if (cacheFolder.exists() || cacheFolder.mkdirs()) {
                    // 文件存在或者创建缓存文件夹成功，使用sdcard
                    return _externalCachePath;
                }
            }
        }

        return _internalCachePath;
    }

    /**
     * 获取SD存储卡总容量
     *
     * @return SD存储卡总容量(单位：字节)
     */
    public static synchronized long getTotalExternalMemorySize() {
        long nSDTotalSize = -1;
        if (!isExternalStorageAvail()) {
            return nSDTotalSize;
        }
        StatFs statfs = new StatFs(
                getExternalStorageDirectory().getPath());
        // 获取SDCard上BLOCK总数
        long nTotalBlocks = statfs.getBlockCount();
        // 获取SDCard上每个block的SIZE
        long nBlocSize = statfs.getBlockSize();
        // 获取可供程序使用的Block的数
        // long nAvailaBlock = statfs.getAvailableBlocks();
        // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
        // long nFreeBlock = statfs.getFreeBlocks();
        // 计算SDCard 总容量大小字节
        nSDTotalSize = nTotalBlocks * nBlocSize;
        // 计算 SDCard 剩余大小字节
        // long nSDFreeSize = nAvailaBlock * nBlocSize;
        return nSDTotalSize;
    }

    /**
     * 获取SD存储卡剩余容量
     *
     * @return SD存储卡剩余容量(单位：字节)
     */
    public static synchronized long getFreeExternalMemorySize() {
        long nSDFreeSize = -1;
        if (!isExternalStorageAvail()) {
            return nSDFreeSize;
        }
        StatFs statfs = new StatFs(
                getExternalStorageDirectory().getPath());
        // 获取SDCard上BLOCK总数
        // long nTotalBlocks = statfs.getBlockCount();
        // 获取SDCard上每个block的SIZE
        long nBlocSize = statfs.getBlockSize();
        // 获取可供程序使用的Block的数
        long nAvailaBlock = statfs.getAvailableBlocks();
        // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
        // long nFreeBlock = statfs.getFreeBlocks();
        // 计算SDCard 总容量大小MB
        // long nSDTotalSize = nTotalBlocks * nBlocSize;
        // 计算 SDCard 剩余大小MB
        nSDFreeSize = nAvailaBlock * nBlocSize;
        return nSDFreeSize;
    }

    public static synchronized long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return (totalBlocks * blockSize);
    }

    /**
     * 获取扩展存储路径
     *
     * @return File对象类型的扩展存储路径
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 对下载的zip文件进行解压。
     *
     * @param zipFile
     * @param targetDir
     */
    public static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; // 这里缓冲区我们使用4KB,
        String strEntry; // 保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; // 缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            ZipEntry entry; // 每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    LogUtils.i("=" + strEntry);
                    File entryFile = new File(targetDir + "/" + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    public static void Unzip(String zipFile) {
        String absolutePath = new File(zipFile).getAbsolutePath();
        Unzip(zipFile, absolutePath.substring(0, absolutePath.lastIndexOf(".")));
    }
}

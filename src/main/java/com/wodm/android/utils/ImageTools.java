package com.wodm.android.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by songchenyu on 16/11/4.
 */

public class ImageTools {
    public static File getPath() {
        File filePath;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            filePath=Environment.getExternalStorageDirectory();
        }else {
            filePath=Environment.getRootDirectory();

        }
        String file=filePath+"/wodom/";
        File file1=new File(file);
        if (file1.exists()){
            return file1;
        } else {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file1;
    }
}

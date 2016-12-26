package com.wodm.android.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ATRSnail on 2016/12/23.
 */

public class PictureTools {
    String url;
//    Bitmap bitmap;
    public PictureTools(String path) {
        this.url =path;
    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 异步线程下载图片
     *
     */
//    class MyTask extends AsyncTask<String, Integer, Void> {
//
//        protected Void doInBackground(String... params) {
//            bitmap=GetImageInputStream((String)params[0]);
//            return null;
//        }
//
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//        }
//
//    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public String SavaImage(Bitmap bitmap, String path){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+System.currentTimeMillis()+".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
             fileOutputStream.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
        String str=path+"/"+System.currentTimeMillis()+".png";
        if (file.isDirectory()){
                 File files[] = file.listFiles();
                 for (int i = 0; i <files.length ; i++) {
                       File f=files[i];
                     str=f.getAbsolutePath().toString();
                 }
             }
        return  str;
    }
}

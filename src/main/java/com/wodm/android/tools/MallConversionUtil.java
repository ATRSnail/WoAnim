package com.wodm.android.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.MallNativiBean;
import com.wodm.android.view.biaoqing.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class MallConversionUtil {
    private static MallConversionUtil mFaceConversionUtil;
    /** 保存于内存中的表情HashMap */
    private HashMap<String, String> mallMap = new HashMap<String, String>();
    /** 保存于内存中的表情集合 */
    private List<MallNativiBean> emojis = new ArrayList<MallNativiBean>();

    public void getFileText(Context context) {
        ParseData(FileUtils.getMallFile(context), context);
    }
    public static MallConversionUtil getInstace() {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new MallConversionUtil();
        }
        return mFaceConversionUtil;
    }
    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @throws Exception
     */
    public void dealExpression(Context context,
                                String name, ImageView imaegeview,String url)
            throws Exception {
        if (name==null||name.equals("")){
            return;
        }
        for (int i = 0; i <emojis.size() ; i++) {
                String value = mallMap.get(name);
               if (value!=null){
                   int resId = context.getResources().getIdentifier(value, "mipmap",
                           context.getPackageName());
//                   imaegeview.setBackgroundResource(resId); //会出现重叠
                   imaegeview.setImageResource(resId);
                   return;
//                   Bitmap bitmap = BitmapFactory.decodeResource(
//                           context.getResources(), resId);
//                   imaegeview.setImageBitmap(bitmap);
               }else {
                   if (url==null||name.equals("")){
                       return;
                   }
                   Glide.with(context).load(url).asBitmap().placeholder(R.mipmap.loading).into(imaegeview);
               }

        }


    }
    /**
     * 解析字符
     *
     * @param data
     */
    private void ParseData(List<String> data, Context context) {
        if (data == null) {
            return;
        }
        MallNativiBean emojEentry;
        try {
            for (String str : data) {
                String[] text = str.split(",");
                String fileName = text[0]
                        .substring(0, text[0].lastIndexOf("."));
                mallMap.put(text[1], fileName);
                int resID = context.getResources().getIdentifier(fileName,
                        "mipmap", context.getPackageName());

                if (resID != 0) {
                    emojEentry = new MallNativiBean();
                    emojEentry.setId(resID);
                    emojEentry.setCharacter(text[1]);
                    emojEentry.setFaceName(fileName);
                    emojis.add(emojEentry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

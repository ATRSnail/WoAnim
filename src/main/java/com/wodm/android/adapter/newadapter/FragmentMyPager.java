package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

import java.util.ArrayList;


/**
 * Created by songchenyu on 16/10/21.
 */


public class FragmentMyPager extends Fragment {
    int mNum = 0;
    private int arrayGuaJian[] = {R.mipmap.guajian_huaxin, R.mipmap.guajian_bote, R.mipmap.guajian_lanse, R.mipmap.guajian_putong, R.mipmap.guajian_hama};
    private int headerGuaJian[] = {R.mipmap.touxiang_xiong, R.mipmap.touxiang_wukong, R.mipmap.touxiang_lvmaozi, R.mipmap.touxiang_yugang, R.mipmap.touxiang_zhuanshi,
            R.mipmap.touxiang_qie, R.mipmap.touxiang_baiduzi, R.mipmap.touxiang_haizei, R.mipmap.touxiang_longxia, R.mipmap.touxiang_qianshuiting, R.mipmap.touxiang_xiaohuangji};

    private String stringGuaJian[] = {"花心眼睛", "波特的眼睛", "蓝色太阳镜", "普通款式", "蛤蟆镜"};
    private String headerStrGuaJian[] = {"熊仔头像框", "悟空头像框", "绿帽头像框", "鱼缸头像框", "钻石头像框", "企鹅头像框", "白兔子头像框", "海贼头像框", "龙虾头像框", "潜水艇头像框", "小黄鸡头像框"};
    private ArrayList<int[]> imgs = new ArrayList<>();
    private ArrayList<String[]> titles = new ArrayList<>();
    private MyGridView gv_guajian;
    //    private static MyGridView gv_header;
    private Context mContext;
    private RelativeLayout ll_header;
    //    private static RelativeLayout ll_guajian;
    private static String myClickImage;
    private addClickIconListener addClickIconListener;
    //    private GuaJianAdapter guaJianAdapter;
    private TouXiangAdapter touXiangAdapter;

    public void setClickImage(String imgStr){
        if (TextUtils.isEmpty(imgStr)) return;
        myClickImage = imgStr;
        if (touXiangAdapter != null) {
            touXiangAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 不选择时
     */
    public void onUnselect() {
        System.out.println("dddd----un-" + touXiangAdapter);

        if (touXiangAdapter != null) {
            touXiangAdapter.onUnselect();
        }
    }


    public void setmNum(int position){
        this.mNum = position;
    }

    public TouXiangAdapter getTouAdapter(){
        return touXiangAdapter;
    }

//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//    }

    public void setAddClickIconListener(addClickIconListener listener) {
        addClickIconListener = listener;
    }

    public interface addClickIconListener {
        void addImage(String title, int imageRescoure, boolean isVip, int index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(com.wodm.R.layout.fragment_mypage, container, false);
        gv_guajian = (MyGridView) v.findViewById(R.id.gv_guajian);
//        gv_header= (MyGridView) v.findViewById(R.id.gv_header);
        ll_header = (RelativeLayout) v.findViewById(R.id.ll_header);
//        ll_guajian= (RelativeLayout) v.findViewById(R.id.ll_guajian);
        //      gv_guajian.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mContext = getActivity();
//        ll_header.setVisibility(View.VISIBLE);
//        ll_guajian.setVisibility(View.GONE);

        imgs.add(arrayGuaJian);
        imgs.add(headerGuaJian);
        titles.add(stringGuaJian);
        titles.add(headerStrGuaJian);


        //       guaJianAdapter = new GuaJianAdapter(gv_guajian,arrayGuaJian,stringGuaJian,myClickImage);
        touXiangAdapter = new TouXiangAdapter(gv_guajian, imgs.get(mNum), titles.get(mNum), myClickImage);
        //       gv_guajian.setAdapter(guaJianAdapter);
//        } else {
//            ll_guajian.setVisibility(View.VISIBLE);
//            ll_header.setVisibility(View.VISIBLE);
        gv_guajian.setAdapter(touXiangAdapter);
//        }
        return v;
    }

    //    class GuaJianAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
//        private String clickStr;
//        private MyGridView mGirdview;
//        private int imageRescoures[];
//        private String strRescoures[];
//
//        public GuaJianAdapter(MyGridView girdview,int imageRescoures[],String strRescoures[],String clickImage){
//            this.mGirdview=girdview;
//            this.imageRescoures=imageRescoures;
//            this.strRescoures=strRescoures;
//            this.clickStr=clickImage;
//            mGirdview.setOnItemClickListener(this);
//        }
//        @Override
//        public int getCount() {
//            return imageRescoures.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//           Holder holder=null;
//            if (convertView==null){
//                holder=new Holder();
//                convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_header,null,false);
//                holder.img_icon= (ImageView) convertView.findViewById(R.id.img_icon);
//                holder.img_guajian_kuang= (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
//                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
//                convertView.setTag(holder);
//            }else {
//                holder= (Holder) convertView.getTag();
//            }
//            if (clickStr!=null&&clickStr.equals(strRescoures[position])){
//                holder.img_guajian_kuang.setVisibility(View.VISIBLE);
//                addClickIconListener.addImage(clickStr,imageRescoures[position],false);
//            }else {
//                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
//            }
//
//            holder.img_icon.setBackgroundResource(imageRescoures[position]);
//            holder.tv_name.setText(strRescoures[position]);
//
//            return convertView;
//        }
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            clickStr=strRescoures[position];
//            addClickIconListener.addImage(clickStr,imageRescoures[position],false);
//            notifyDataSetChanged();
//            if (touXiangAdapter != null){
//                touXiangAdapter.onUnselect();
//            }
//        }
//
//        /**
//         * 不选择时
//         */
//        public void onUnselect(){
//            clickStr= null;
//            System.out.println("dddd----");
//            notifyDataSetChanged();
//        }
//
//        class Holder{
//            ImageView img_icon,img_guajian_kuang;
//            TextView tv_name;
//        }
//    }
    public class TouXiangAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        private String clickStr;
        private MyGridView mGirdview;
        private int imageRescoures[];
        private String strRescoures[];

        public TouXiangAdapter(MyGridView girdview, int imageRescoures[], String strRescoures[], String clickImage) {
            this.mGirdview = girdview;
            this.imageRescoures = imageRescoures;
            this.strRescoures = strRescoures;
            this.clickStr = clickImage;
            mGirdview.setOnItemClickListener(this);
        }

        @Override
        public int getCount() {
            return imageRescoures.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_guajian, null, false);
                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                holder.img_guajian_kuang = (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if (clickStr != null && clickStr.equals(strRescoures[position])) {
                holder.img_guajian_kuang.setVisibility(View.VISIBLE);
//              addClickIconListener.addImage(clickStr, imageRescoures[position], false,mNum);
            } else {
                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
            }
            holder.img_icon.setBackgroundResource(imageRescoures[position]);
            holder.tv_name.setText(strRescoures[position]);
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (addClickIconListener == null) return;
            clickStr = strRescoures[position];
            addClickIconListener.addImage(clickStr, imageRescoures[position], false,mNum);
            notifyDataSetChanged();
        }

        /**
         * 不选择时
         */
        public void onUnselect() {
            clickStr = null;
            notifyDataSetChanged();
        }

        class Holder {
            ImageView img_icon, img_guajian_kuang;
            TextView tv_name;
        }
    }

}


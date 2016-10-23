package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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



/**
 * Created by songchenyu on 16/10/21.
 */


public class FragmentMyPager extends Fragment
{
    int mNum=0;
    private int arrayGuaJian[]={R.mipmap.guajian_huaxin, R.mipmap.guajian_bote,R.mipmap.guajian_lanse,R.mipmap.guajian_putong,R.mipmap.guajian_hama};
    private int headerGuaJian[]={R.mipmap.touxiang_xiong, R.mipmap.touxiang_wukong,R.mipmap.touxiang_lvmaozi,R.mipmap.touxiang_yugang,R.mipmap.touxiang_zhuanshi,
            R.mipmap.touxiang_qie,R.mipmap.touxiang_baiduzi,R.mipmap.touxiang_haizei,R.mipmap.touxiang_longxia,R.mipmap.touxiang_qianshuiting,R.mipmap.touxiang_xiaohuangji};

    private String stringGuaJian[]={"花心眼睛","波特的眼睛","蓝色太阳镜","普通款式","蛤蟆镜"};
    private String headerStrGuaJian[]={"熊仔头像框","悟空头像框","绿帽头像框","鱼缸头像框","钻石头像框","企鹅头像框","白兔子头像框","海贼头像框","龙虾头像框","潜水艇头像框","小黄鸡头像框"};
    private static MyGridView gv_guajian;
    private static MyGridView gv_header;
    private static Context mContext;
    private static RelativeLayout ll_header;
    private static RelativeLayout ll_guajian;
    private static String myClickImage;
    private static addClickIconListener addClickIconListener;
    public static FragmentMyPager newInstance(int num,String clickImage)
    {
        FragmentMyPager f=new FragmentMyPager();
        Bundle args=new Bundle();
        args.putInt("num",num);
        myClickImage=clickImage;
        f.setArguments(args);
        return f;
    }
    public void setCurrentTabNum(int num){
        this.mNum=num;
        if (mNum==1){
            ll_header.setVisibility(View.GONE);
            ll_guajian.setVisibility(View.VISIBLE);
        } else {
            ll_header.setVisibility(View.VISIBLE);
            ll_guajian.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mNum=getArguments()!=null?getArguments().getInt("num"):1;
    }
    public void setAddClickIconListener(addClickIconListener listener){
        this.addClickIconListener=listener;
    }
    public interface addClickIconListener{
        public void addImage(String title,int imageRescoure);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(com.wodm.R.layout.fragment_mypage,container,false);
        gv_guajian= (MyGridView) v.findViewById(R.id.gv_guajian);
        gv_header= (MyGridView) v.findViewById(R.id.gv_header);
        ll_header= (RelativeLayout) v.findViewById(R.id.ll_header);
        ll_guajian= (RelativeLayout) v.findViewById(R.id.ll_guajian);
        gv_guajian.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mContext=getActivity();
        ll_header.setVisibility(View.VISIBLE);
        ll_guajian.setVisibility(View.GONE);
        gv_guajian.setAdapter(new GuaJianAdapter(gv_guajian,arrayGuaJian,stringGuaJian,myClickImage));
//        } else {
//            ll_guajian.setVisibility(View.VISIBLE);
//            ll_header.setVisibility(View.VISIBLE);
        gv_header.setAdapter(new TouXiangAdapter(gv_header,headerGuaJian,headerStrGuaJian,myClickImage));
//        }

        return v;
    }
    class GuaJianAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        private String clickStr;
        private MyGridView mGirdview;
        private int imageRescoures[];
        private String strRescoures[];

        public GuaJianAdapter(MyGridView girdview,int imageRescoures[],String strRescoures[],String clickImage){
            this.mGirdview=girdview;
            this.imageRescoures=imageRescoures;
            this.strRescoures=strRescoures;
            this.clickStr=clickImage;
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
           Holder holder=null;
            if (convertView==null){
                holder=new Holder();
                convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_header,null,false);
                holder.img_icon= (ImageView) convertView.findViewById(R.id.img_icon);
                holder.img_guajian_kuang= (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }
            if (clickStr!=null&&clickStr.equals(strRescoures[position])){
                holder.img_guajian_kuang.setVisibility(View.VISIBLE);
                addClickIconListener.addImage(clickStr,imageRescoures[position]);
            }else {
                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
            }

            holder.img_icon.setBackgroundResource(imageRescoures[position]);
            holder.tv_name.setText(strRescoures[position]);

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            clickStr=strRescoures[position];
            addClickIconListener.addImage(clickStr,imageRescoures[position]);
            notifyDataSetChanged();
        }

        class Holder{
            ImageView img_icon,img_guajian_kuang;
            TextView tv_name;
        }
    }
    class TouXiangAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        private String clickStr;
        private MyGridView mGirdview;
        private int imageRescoures[];
        private String strRescoures[];

        public TouXiangAdapter(MyGridView girdview,int imageRescoures[],String strRescoures[],String clickImage){
            this.mGirdview=girdview;
            this.imageRescoures=imageRescoures;
            this.strRescoures=strRescoures;
            this.clickStr=clickImage;
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
            Holder holder=null;
            if (convertView==null){
                holder=new Holder();
                convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_guajian,null,false);
                holder.img_icon= (ImageView) convertView.findViewById(R.id.img_icon);
                holder.img_guajian_kuang= (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }
            if (clickStr!=null&&clickStr.equals(strRescoures[position])){
                holder.img_guajian_kuang.setVisibility(View.VISIBLE);
                addClickIconListener.addImage(clickStr,imageRescoures[position]);
            }else {
                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
            }
            holder.img_icon.setBackgroundResource(imageRescoures[position]);
            holder.tv_name.setText(strRescoures[position]);
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            clickStr=strRescoures[position];
            addClickIconListener.addImage(clickStr,imageRescoures[position]);
            notifyDataSetChanged();
        }

        class Holder{
            ImageView img_icon,img_guajian_kuang;
            TextView tv_name;
        }
    }

}


package com.wodm.android.fragment.newfragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.JuJiAdapter;
import com.wodm.android.adapter.newadapter.JuJiNumAdapter;
import com.wodm.android.view.newview.HorizontalListView;
import com.wodm.android.view.newview.MyGridView;

/**
 * Created by songchenyu on 16/12/12.
 */

public class MuluFragment extends Fragment implements View.OnClickListener{
    private ImageView img_details_up;
    private TextView tv_jianjie_value,tv_jianjie_value2;
    private boolean isShow=false;
    private RelativeLayout rl_details_up;
    private boolean isOverFlowed;
    private HorizontalListView hor_lv;
    private MyGridView gv_adapter_juji;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.from(getActivity()).inflate(R.layout.mulu_fragment,null,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        img_details_up= (ImageView) view.findViewById(R.id.img_details_up);
        rl_details_up= (RelativeLayout) view.findViewById(R.id.rl_details_up);
        hor_lv= (HorizontalListView) view.findViewById(R.id.hor_lv);
        gv_adapter_juji= (MyGridView) view.findViewById(R.id.gv_adapter_juji);
        gv_adapter_juji.setAdapter(new JuJiNumAdapter(getActivity()));
        hor_lv.setAdapter(new JuJiAdapter(getActivity()));
        img_details_up.setOnClickListener(this);
        rl_details_up.setOnClickListener(this);
        tv_jianjie_value= (TextView) view.findViewById(R.id.tv_jianjie_value);
        tv_jianjie_value2= (TextView) view.findViewById(R.id.tv_jianjie_value2);
        isOverFlowed=isOverFlowed(tv_jianjie_value);
        if (isOverFlowed){
            img_details_up.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 判断TextView的内容宽度是否超出其可用宽度
     * @param tv
     * @return
     */
    public static boolean isOverFlowed(TextView tv) {
        int availableWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
        Paint textViewPaint = tv.getPaint();
        float textWidth = textViewPaint.measureText(tv.getText().toString());
        if (textWidth > availableWidth) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_details_up:
            case R.id.img_details_up:
                if (isOverFlowed){
                    if (!isShow){
                        tv_jianjie_value2.setVisibility(View.VISIBLE);
                        tv_jianjie_value.setVisibility(View.GONE);
                        img_details_up.setBackgroundResource(R.mipmap.img_details_up);
                        isShow=true;
                    }else {
                        tv_jianjie_value2.setVisibility(View.GONE);
                        tv_jianjie_value.setVisibility(View.VISIBLE);
                        img_details_up.setBackgroundResource(R.mipmap.img_details_down);
                        isShow=false;
                    }

                }
                break;
        }
    }
}

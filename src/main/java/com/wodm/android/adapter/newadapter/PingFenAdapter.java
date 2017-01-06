package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.newview.SendMsgActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/27.
 */

public class PingFenAdapter extends BaseAdapter{
    int [] pic=new int[]{R.mipmap.taoxin_no,R.mipmap.taoxin};
    List<Integer> list =new ArrayList<>();
    List<Boolean> click= new ArrayList<>();
    TextView textView;
    Context context;
    public static int count;
    public PingFenAdapter(SendMsgActivity sendMsgActivity, TextView textView) {
        this.context=sendMsgActivity;
        this.textView=textView;
        for (int i = 0; i <6 ; i++) {
            list.add(pic[0]);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.taoxin,null);
        final ImageView ima= (ImageView) view.findViewById(R.id.taoxin_iv);
        click.add(false);
        ima.setImageResource(list.get(position));
        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click.get(position)){
                    count++;
                    ima.setImageResource(pic[1]);
                }else {
                    count--;
                    ima.setImageResource(pic[0]);
                    textView.setTextColor(Color.parseColor("#999999"));
                }
                click.set(position,!click.get(position));
                if (count>0){
                    textView.setTextColor(Color.parseColor("#fb487f"));
                }
                textView.setText(count+"分");
            }
        });

        return view;
    }
}

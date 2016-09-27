package com.wodm.android.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wodm.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.Random;

@Layout(R.layout.activity_phone_img)
public class PhoneImgActivity extends AppActivity {

    @ViewIn(R.id.gridView)
    private GridView gridView;
    private int[] img = {};

    ImageAdapter adapter;
    int indexCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indexCheck = new Random().nextInt(img.length);
        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);
    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(PhoneImgActivity.this, R.layout.adapter_phone_img, null);
                holder.box = (CheckBox) convertView.findViewById(R.id.box);
                holder.imageView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.imageView.setImageResource(img[position]);
            holder.box.setChecked(indexCheck == position);
            final ViewHolder finalHolder = holder;
            holder.box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalHolder.box.isChecked()) {
                        indexCheck = position;
                        Toast.makeText(getApplicationContext(), "设置成功", 0).show();
                    } else {
//                        indexCheck = -1;
                    }

                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            CheckBox box;
        }
    }
}

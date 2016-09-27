package com.wodm.android.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;

    protected List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.list = list;
    }

    public CommonAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public final T getItem(int position) {
        if (list != null && list.size() > position) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder = Viewholder.getViewHolder(mContext, position,
                getLayoutId(), convertView, parent);
        setViews(holder, getItem(position));
        return holder.getConvertView();
    }

    public void notifyDataSetChanged(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    protected abstract int getLayoutId();

    protected abstract void setViews(Viewholder holder, T t);


    public static class Viewholder {

        private View mConvertView;
        private int mPosition;
        private Context mContext;
        private SparseArray<View> mViews;

        protected Viewholder(Context context, int position, int layoutId,
                           ViewGroup parent) {
            mViews = new SparseArray<View>();
            this.mPosition = position;
            this.mContext = context;
            mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent,
                    false);
            mConvertView.setTag(this);
        }

        protected static Viewholder getViewHolder(Context context, int position,
                                                  int layoutId, View convertView, ViewGroup parent) {
            if (convertView == null) {
                return new Viewholder(context, position, layoutId, parent);
            } else {
                Viewholder holder = (Viewholder) convertView.getTag();
                holder.mPosition = position;
                return holder;
            }
        }

        public View getConvertView() {
            return mConvertView;
        }

        public <T extends View> T getView(int ViewId) {
            View view = mViews.get(ViewId);
            if (view == null) {
                view = getConvertView().findViewById(ViewId);
                mViews.put(ViewId, view);
            }
            return (T) view;
        }

        public Viewholder setText(int ViewId, String data) {
            View tv = getView(ViewId);
            if (tv instanceof TextView) {
                ((TextView) tv).setText(data);
            } else if (tv instanceof Button) {
                ((Button) tv).setText(data);
            }
            return this;
        }

        public Viewholder setText(int ViewId, int resid) {
            View tv = getView(ViewId);
            if (tv instanceof TextView) {
                ((TextView) tv).setText(resid);
            } else if (tv instanceof Button) {
                ((Button) tv).setText(resid);
            }
            return this;
        }


        public Viewholder setOnClickListener(View.OnClickListener l, int... ViewId) {
            for (int id : ViewId) {
                getView(id).setOnClickListener(l);
            }
            return this;
        }

        public int getPosition() {
            return mPosition;
        }
    }

}

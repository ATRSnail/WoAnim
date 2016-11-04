package com.wodm.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.user.RecordActivity;
import com.wodm.android.ui.user.UsSetActivity;
import com.wodm.android.ui.user.UserCacheActivity;
import com.wodm.android.ui.user.UserInfoActivity;
import com.wodm.android.ui.user.UserIntegralActivity;
import com.wodm.android.ui.user.UserMessageActivity;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.Tracker;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.track.fragment.TrackFragment;

@Layout(R.layout.fragment_us)
public class UsFragment extends TrackFragment {
    private static final String TITLE = "我的界面";
    @ViewIn(R.id.user_head_imgs)
    private ImageView mUserIcon;


    @ViewIn(R.id.user_sign)
    private TextView mSignView;
    @ViewIn(R.id.user_name)
    private TextView mNameView;
    @ViewIn(R.id.layout)
    private View mUserLayout;
    @ViewIn(R.id.user_null)
    private TextView mUserNull;

    @Override
    protected void setDatas(Bundle bundle) {

        setViews(R.id.ordering_records, R.string.order_recoder, R.mipmap.dinggoujilu);
        setViews(R.id.message, R.string.my_message, R.mipmap.xiaoxi);
        setViews(R.id.my_dowm, R.string.my_dowm, R.mipmap.xiazai);
        setViews(R.id.watch_records, R.string.wathc_recoder, R.mipmap.lishijilu);
        setViews(R.id.my_collcet, R.string.collect, R.mipmap.shoucang);
        setViews(R.id.integral, R.string.my_jifen, R.mipmap.jifen);
        setViews(R.id.set_layout, R.string.set_text, R.mipmap.shezhi);

    }


    private void setViews(int layoutId, int stringRes, int imgRes) {
        View view = this.mRootView.findViewById(layoutId);
        if (R.id.ordering_records == layoutId) {
            view.setVisibility(View.GONE);
        }
        TextView tv = (TextView) view.findViewById(R.id.tv_topicname);
        ImageView iv = (ImageView) view.findViewById(R.id.image);
        tv.setText(stringRes);
        iv.setImageResource(imgRes);
        view.setOnClickListener(new OnclikListenr(stringRes));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.CURRENT_USER == null) {
            mUserLayout.setVisibility(View.GONE);
            mUserNull.setVisibility(View.VISIBLE);
            mUserIcon.setImageDrawable(getResources().getDrawable(R.mipmap.touxiang));
        } else {
            mUserNull.setVisibility(View.GONE);
            mUserLayout.setVisibility(View.VISIBLE);
            mNameView.setText(Constants.CURRENT_USER.getData().getAccount().getNickName());
            mSignView.setText(Constants.CURRENT_USER.getData().getAccount().getAutograph() + "");
            new AsyncImageLoader(getActivity(), R.mipmap.default_header, R.mipmap.default_header).display(mUserIcon, Constants.CURRENT_USER.getData().getAccount().getPortrait());
        }
    }

    class OnclikListenr implements View.OnClickListener {
        int stringRes;

        public OnclikListenr(int stringRes) {
            this.stringRes = stringRes;
        }

        @Override
        public void onClick(View v) {
            Tracker.getInstance(getActivity()).trackMethodInvoke("我的", "跳转" + getString(stringRes) + "界面");
            Intent i = new Intent(getActivity(), RecordActivity.class);
            switch (v.getId()) {
                case R.id.ordering_records:
                    i.putExtra("tid", R.id.ordering_records);
                    i.putExtra("title", R.string.order_recoder);
                    startActivity(i);
                    break;
                case R.id.message:
                    i.setClass(getActivity(),UserMessageActivity.class);
                    if(!UpdataUserInfo.isLogIn(getActivity(),true,i)){
                        Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(i);
                    break;
                case R.id.my_dowm:
                    i.setClass(getActivity(),UserCacheActivity.class);
                    if (!UpdataUserInfo.isLogIn(getActivity(), true,i)) {
                        Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(i);
                    break;
                case R.id.watch_records:
                    i.putExtra("tid", R.id.watch_records);
                    i.putExtra("title", R.string.wathc_recoder);
                    startActivity(i);
                    break;
                case R.id.my_collcet:
                    i.putExtra("tid", R.id.my_collcet);
                    i.putExtra("title", R.string.collect);
                    if(!UpdataUserInfo.isLogIn(getActivity(),true,i)){
                        Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(i);
                    break;
                case R.id.integral:
                    i.setClass(getActivity(),UserIntegralActivity.class);
                    if(!UpdataUserInfo.isLogIn(getActivity(),true,i)){
                        return;
                    }
                    startActivity(i);
                    break;
                case R.id.set_layout:
                    startActivity(new Intent(getActivity(), UsSetActivity.class));
                    break;
            }

        }
    }

    @TrackClick(value = R.id.user_info, location = TITLE, eventName = "跳转登录注册界面")
    private void clickUserInfo(View view) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), UserInfoActivity.class);
        if (UpdataUserInfo.isLogIn(getActivity(),true,intent))
            startActivity(intent);
    }

}

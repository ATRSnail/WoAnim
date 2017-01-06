package com.wodm.android.ui.newview;





import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;

import com.wodm.android.fragment.newfragment.CommentFragment;
import com.wodm.android.fragment.newfragment.MuluFragment;
import com.wodm.android.fragment.newfragment.RecommendFragment;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.test)
public class TestActivity extends AppActivity {


    @ViewIn(R.id.test)
    LinearLayout test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommentFragment  comment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resourceId",538);
        bundle.putInt("resourceType",2);
        comment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.test, comment);
//        transaction.replace(R.id.test, new RecommendFragment());
        transaction.commit();

    }
}

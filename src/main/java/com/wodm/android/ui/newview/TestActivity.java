package com.wodm.android.ui.newview;



import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.fragment.newfragment.CommentFragment;
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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
       transaction.replace(R.id.test, new CommentFragment());
        transaction.commit();

    }
}

package com.wodm.android.tools;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by songchenyu on 16/9/21.
 */

public class JianpanTools {
    //隐藏虚拟键盘
    public static void HideKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }
    //输入法是否显示着
    public static boolean KeyBoard(EditText edittext)
    {
        boolean bool = false;
        InputMethodManager imm = ( InputMethodManager ) edittext.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) )
        {
            bool = true;
        }
        return bool;

    }
}

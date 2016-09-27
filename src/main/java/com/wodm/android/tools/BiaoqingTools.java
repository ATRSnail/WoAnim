package com.wodm.android.tools;

import android.text.Editable;
import android.text.Selection;
import android.widget.EditText;

/**
 * Created by songchenyu on 16/9/19.
 */

public class BiaoqingTools {
    private static BiaoqingTools biaoqingTools=null;
    public BiaoqingTools(){

    }
    public static BiaoqingTools getInstance(){
         if (biaoqingTools==null){
             biaoqingTools=new BiaoqingTools();
         }
        return biaoqingTools;
    }
    public  void delete(EditText mCommentView) {
        int selection = mCommentView.getSelectionStart();
        String text = mCommentView.getText().toString();
        if (selection > 0) {
            String text2 = text.substring(selection - 1);
            if ("]".equals(text2)) {
                int start = text.lastIndexOf("[");
                int end = selection;
                mCommentView.getText().delete(start, end);
                return;
            }
            mCommentView.getText().delete(selection - 1, selection);
        }
    }


    public void insert(CharSequence text,EditText mCommentView) {
        int iCursorStart = Selection.getSelectionStart((mCommentView.getText()));
        int iCursorEnd = Selection.getSelectionEnd((mCommentView.getText()));
        if (iCursorStart != iCursorEnd) {
            ((Editable) mCommentView.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((mCommentView.getText()));
        ((Editable) mCommentView.getText()).insert(iCursor, text);
        mCommentView.setFocusable(true);
    }
}

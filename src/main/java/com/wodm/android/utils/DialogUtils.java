package com.wodm.android.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;

/**
 * Created by ATRSnail on 2016/11/15.
 */

public class DialogUtils extends Dialog {
    public DialogUtils(Context context) {
        super(context);
    }

    public DialogUtils(Context context, int themeResId) {
        super(context, themeResId);
    }
    public  void setDialogDismiss(){
        if (DialogUtils.this!=null&&DialogUtils.this.isShowing()){
                    dismiss();
        }
    }
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private OnKeyListener onKeyListener;
        boolean cancelable;



        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.onKeyListener = onKeyListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }



        public DialogUtils create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final DialogUtils dialog = new DialogUtils(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            if (!cancelable){
                dialog.setCanceledOnTouchOutside(false);
            }else {
                dialog.setCanceledOnTouchOutside(true);
            }

            // set the dialog title  判断title message button 为空或不为空展示的状态
            if (title != null) {
                ((TextView) layout.findViewById(R.id.title_dialog)).setText(title);

            } else {
                ((TextView) layout.findViewById(R.id.title_dialog)).setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.lineup_dialog).setVisibility(
                        View.INVISIBLE);
            }

            if (positiveButtonText == null && negativeButtonText == null) {
                layout.findViewById(R.id.linebott_dialog).setVisibility(
                        View.INVISIBLE);
            }
            if (title == null && message == null) {
                layout.findViewById(R.id.linebott_dialog).setVisibility(
                        View.GONE);
            }

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.line_dialog).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.line_dialog).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                //设置字间距
//                ((TextView) layout.findViewById(R.id.message_dialog)).setTextScaleX(1.8f);
                ((TextView) layout.findViewById(R.id.message_dialog)).setText(message);
            } else {
                if (contentView != null) {
                    // if no message set
                    // add the contentView to the dialog body
                    ((LinearLayout) layout.findViewById(R.id.content_dialog))
                            .removeAllViews();
                    ((LinearLayout) layout.findViewById(R.id.content_dialog)).addView(
                            contentView, new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                } else {
                    // if no message 、 contentView 、set
                    ((LinearLayout) layout.findViewById(R.id.content_dialog)).setVisibility(View.INVISIBLE);
                    layout.findViewById(R.id.lineup_dialog).setVisibility(
                            View.INVISIBLE);
                }
            }
            dialog.setContentView(layout);
            return dialog;
        }


    }
}

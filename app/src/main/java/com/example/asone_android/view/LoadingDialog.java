package com.example.asone_android.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.example.asone_android.R;
import com.example.asone_android.app.BaseApplication;

public class LoadingDialog extends Dialog {


    private static volatile LoadingDialog instance;

    public static LoadingDialog getInstance(){
        if (instance == null){
            synchronized (LoadingDialog.class){
                if (instance == null){
                    instance = new LoadingDialog(BaseApplication.getAppContext(),R.style.MyDialogStyle);
                }
            }
        }
        return instance;
    }


    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

        private Context context;
//        private String message;
        private boolean isShowMessage=true;
        private boolean isCancelable=true;
        private boolean isCancelOutside=true;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         * @param message
         * @return
         */

//        public Builder setMessage(String message){
//            this.message=message;
//            return this;
//        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return
         */
        public Builder setShowMessage(boolean isShowMessage){
            this.isShowMessage=isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public LoadingDialog create(){

            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            LoadingDialog loadingDailog = new LoadingDialog(context,R.style.MyDialogStyle);
            LoadingDialog dialog = new LoadingDialog(context);
//            if(isShowMessage){
//                msgText.setText(message);
//            }else{
//                msgText.setVisibility(View.GONE);
//            }
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            return  loadingDailog;

        }

        public LoadingDialog create(String s){
            LoadingDialog dialog = getInstance();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            dialog.setContentView(view);
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelOutside);
            return  dialog;

        }


    }
}

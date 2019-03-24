package com.example.asone_android.utils.version;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.asone_android.R;
import com.example.asone_android.app.Constant;
import com.example.asone_android.utils.ACache;
import com.example.asone_android.utils.FileUtils;

import static com.example.asone_android.utils.version.NotificationUtils.NOTIFICATION_ID_UPDATE;


/**
 * 说明：版本更新帮助类，负责下载apk文件并打开安装
 * 用法：直接new本类，构造方法里分别传
 * Context    Activity的context
 * String     apk下载地址
 * boolean    允许跳过本次更新
 * String     更新说明
 * 示例：普通版本更新    new VersionUpdataHelper(MainActivity.this, info.getUrl());
 * 强制版本更新    new VersionUpdataHelper(MainActivity.this, info.getUrl(), false, "");
 * 备注：运行时除了本类还需要有layout目录下的dialog_custom.xml和drawable目录下的dialog_background.xml文件
 * Created by LiuV on 2017/6/8.
 */

public class VersionUpdataHelper {
    private static final String TAG = "VersionUpdataHelper";

    private static final String DOWNLOAD_FILE_NAME = "police110.apk";

    private Context mContext;
    private String mUrl;
    private boolean mCancelable;
    private String mUpdateInfo;
    private String mPatchPath;
    private NotificationUtils util;
    private static CustomDialog mDialog;
    public VersionUpdataHelper(Activity activity, String url, String patchPath) {
        this(activity, url, true,patchPath);
    }

    public VersionUpdataHelper(Activity activity, String url, boolean cancelable, String patchPath) {
        this(activity, url, cancelable, "",patchPath);
    }

    public VersionUpdataHelper(Activity activity, String url, boolean cancelable, String info, String patchPath){
        if (activity == null || activity.isFinishing()){
            return;
        }
        mContext = activity;
        mUrl = url;
        mCancelable = cancelable;
        mUpdateInfo = info;
        mPatchPath = patchPath;
        showNewVersionDialog();
    }

    public void showNewVersionDialog() {
        util = NotificationUtils.getInstance(mContext);
        util.setProgressView(mUrl);
        checkNotice();
        DownloadHelper.get().setCancelListener(() -> {
            util.cancel(NOTIFICATION_ID_UPDATE);
            FileUtils.deleteFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + DOWNLOAD_FILE_NAME);
            ACache.get().put(Constant.VERSION_STATUS,-1);
        });
        DownloadHelper.get().downloadApk(mUrl, Environment.DIRECTORY_DOWNLOADS, DOWNLOAD_FILE_NAME, new DownloadHelper.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                util.downloadSuccess(mContext, path);
                InstallAppUtils.Companion.openFile(mContext, path);
            }

            @Override
            public void onDownloading(int progress) {
                util.notifyProgress("正在下载" + progress + "%", 100, progress);
            }

            @Override
            public void onDownloadFailed() {
                util.downloadFail();
                ACache.get().put(Constant.VERSION_STATUS,-1);
                FileUtils.deleteFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + DOWNLOAD_FILE_NAME);
            }
        });
    }
    private void checkNotice() {
        if (!NotificationManagerCompat.from(mContext).areNotificationsEnabled()) {
            CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
            builder.setMessage("检测到您没有允许通知权限，是否允许");
            builder.setPositiveButton("是", (dialog, which) -> {
                dialog.dismiss();
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                mContext.startActivity(localIntent);
            });
            builder.setNegativeButton("否", (dialog, which) -> {
                dialog.dismiss();
            });
            CustomDialog dialog = builder.create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public static class CustomDialog extends Dialog {

        private TextView mMessageTv;
        private Button mPositiveBtn;
        private Button mNegativeBtn;
        private View mButtonDividerView;

        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public CustomDialog(Context context) {
            super(context);
        }

        public CustomDialog(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_custom);
            mMessageTv = findViewById(R.id.tv_dialog_message);
            mPositiveBtn = findViewById(R.id.btn_dialog_positive);
            mNegativeBtn = findViewById(R.id.btn_dialog_negative);
            mButtonDividerView = findViewById(R.id.view_dialog_button_divider);

            if (message != null) {
                mMessageTv.setText(message);
            }
            if (positiveButtonText != null) {
                mPositiveBtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    mPositiveBtn.setOnClickListener(v -> positiveButtonClickListener.onClick(CustomDialog.this, Dialog.BUTTON_POSITIVE));
                }
            } else {
                mPositiveBtn.setVisibility(View.GONE);
                mButtonDividerView.setVisibility(View.GONE);
            }

            if (negativeButtonText != null) {
                mNegativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    mNegativeBtn.setOnClickListener(v -> negativeButtonClickListener.onClick(CustomDialog.this, Dialog.BUTTON_NEGATIVE));
                }
            } else {
                mNegativeBtn.setVisibility(View.GONE);
                mButtonDividerView.setVisibility(View.GONE);
            }

        }

        private void setMessage(String msg) {
            message = msg;
        }

        private void setPositiveButtonText(String text) {
            positiveButtonText = text;
        }

        private void setNegativeButtonText(String text) {
            negativeButtonText = text;
        }

        private void setOnNegativeListener(OnClickListener listener) {
            negativeButtonClickListener = listener;
        }

        private void setOnPositiveListener(OnClickListener listener) {
            positiveButtonClickListener = listener;
        }

        public static class Builder {
            private Context context;
            private String message;
            private String positiveButtonText;
            private String negativeButtonText;
            private OnClickListener positiveButtonClickListener;
            private OnClickListener negativeButtonClickListener;

            public Builder(Context context) {
                this.context = context;
            }

            public Builder setMessage(String message) {
                this.message = message;
                return this;
            }

            public Builder setMessage(int message) {
                this.message = context.getString(message);
                return this;
            }

            public Builder setPositiveButton(int positiveButtonText,
                                             OnClickListener listener) {
                return setPositiveButton(context.getString(positiveButtonText), listener);
            }

            public Builder setPositiveButton(String positiveButtonText,
                                             OnClickListener listener) {
                this.positiveButtonText = positiveButtonText;
                this.positiveButtonClickListener = listener;
                return this;
            }

            public Builder setNegativeButton(int negativeButtonText,
                                             OnClickListener listener) {
                return setNegativeButton(context.getString(negativeButtonText), listener);
            }

            public Builder setNegativeButton(String negativeButtonText,
                                             OnClickListener listener) {
                this.negativeButtonText = negativeButtonText;
                this.negativeButtonClickListener = listener;
                return this;
            }

            public CustomDialog create() {
                CustomDialog dialog = new CustomDialog(context);
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setMessage(message);
                dialog.setNegativeButtonText(negativeButtonText);
                dialog.setPositiveButtonText(positiveButtonText);
                dialog.setOnNegativeListener(negativeButtonClickListener);
                dialog.setOnPositiveListener(positiveButtonClickListener);
                return dialog;
            }
        }
    }
}

package com.example.asone_android.utils.version;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.example.asone_android.app.Constant;
import com.example.asone_android.utils.ACache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadHelper {
    private static final String TAG = "DownloadHelper";
    private static DownloadHelper downloadHelper;
	private final OkHttpClient okHttpClient;
	//取消
    public static boolean cancel;
    public static String mCancelUrl;
    public static OnCancelListener mOnCancelListener;

    public static DownloadHelper get() {
        if (downloadHelper == null) {
			downloadHelper = new DownloadHelper();
        }
        return downloadHelper;
    }

    private DownloadHelper() {
        okHttpClient = new OkHttpClient();
    }

    public  void setCancelListener(OnCancelListener listener){
        mOnCancelListener = listener;
    }
    public  void setCancelUrl(String url){
        mCancelUrl = url;
        cancel = true;
    }

    /**
     * 版本更新
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void downloadApk(final String url, final String saveDir, final String fileName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
                cancel = false;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int mProgress = 0;
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                if(response.code() != 200){
                    listener.onDownloadFailed();
                    cancel = false;
                    return;
                }
                //下载中
                ACache.get().put(Constant.VERSION_STATUS,0);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath,fileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        if (url.equals(mCancelUrl) && cancel) {
                            fos.flush();
                            mOnCancelListener.onCancelSuccess();
                            cancel = false;
                            return;
                        }
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        if(progress > mProgress) {
                            listener.onDownloading(progress);
                            mProgress = progress;
                        }
                    }
                    fos.flush();
                    // 下载完成
                    ACache.get().put(Constant.VERSION_STATUS,1);
                    listener.onDownloadSuccess(file.getPath());
                    cancel = false;
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
						if (is != null) {
							is.close();
						}
                    } catch (IOException e) {
                    }
                    try {
						if (fos != null) {
							fos.close();
						}
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * @param progress
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
    public interface OnCancelListener {
        /**
         * 取消下载
         */
        void onCancelSuccess();
    }
}
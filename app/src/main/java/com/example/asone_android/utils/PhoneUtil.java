package com.example.asone_android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class PhoneUtil {
    private static final String TAG = "PhoneUtil";
    public static final int CMNET = 3;
    public static final int CMWAP = 2;
    public static final int WIFI = 1;

    public static final int CALL = 0; // 呼出
    public static final int CALL_SELF = 1; // 呼入


    public static String PHONE_IMEI_VALUE = "";
    public static String PHONE_IMSI_VALUE = "";
    public static String PHONE_OS_VALUE = "";
    public static String PHONE_MODEL_VALUE = "";
    public static String PHONE_TEL_VALUE = "";
    public static String CLIENT_TYPE_VALUE = "";

    /**
     * 获取手机号，有可能取不到
     *
     * @return
     */
    public final static String getPhone(Context context) {
        if (context == null) {
            return "";
        }
        if (PHONE_TEL_VALUE != null && !PHONE_TEL_VALUE.equals("")) {
            return PHONE_TEL_VALUE;
        }
        @SuppressLint({"MissingPermission", "HardwareIds"}) String phoneNumber = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        if (phoneNumber != null && phoneNumber.equals("0")) {
            phoneNumber = "";
        }
        PHONE_TEL_VALUE = phoneNumber;
        return PHONE_TEL_VALUE;
    }

    /**
     * 获取imei
     *
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public final static String getImei(Context context) {
        if (context == null) {
            return "";
        }
        if (PHONE_IMEI_VALUE != null && !PHONE_IMEI_VALUE.equals("")) {
            return PHONE_IMEI_VALUE;
        }
        PHONE_IMEI_VALUE = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return PHONE_IMEI_VALUE;
    }

    /**
     * 获取imsi
     *
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public final static String getImsi(Context context) {
        if (context == null) {
            return "";
        }
        if (PHONE_IMSI_VALUE != null && !PHONE_IMSI_VALUE.equals("")) {
            return PHONE_IMSI_VALUE;
        }
        PHONE_IMSI_VALUE = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        return PHONE_IMSI_VALUE;
    }

    /**
     * 检查横竖屏
     *
     * @param context
     * @return true横屏，false竖屏
     * @author D.X 2014-5-13 下午1:56:06
     */
    public final static int getOrientation(Context context) {
        if (context == null) {
            return 1;
        }
        return context.getResources().getConfiguration().orientation;
//		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//			return true;
//		}else if(context.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
//			return false;
//		}
//		return false;
    }

    // 取得版本号
    public static int getVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    // 取得版本号
    public static String getVersionName(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public final static String getOs() {
        if (PHONE_OS_VALUE != null && !PHONE_OS_VALUE.equals("")) {
            return PHONE_OS_VALUE;
        }
        PHONE_OS_VALUE = "Android " + Build.VERSION.RELEASE;
        return PHONE_OS_VALUE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public final static String getModel() {
        if (PHONE_MODEL_VALUE != null && !PHONE_MODEL_VALUE.equals("")) {
            return PHONE_MODEL_VALUE;
        }
        PHONE_MODEL_VALUE = Build.MODEL;
        return PHONE_MODEL_VALUE;
    }

    /**
     * 判断是否挂载sdcard
     *
     * @return true是, false否
     */
    public final static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查sdcard的可用空间
     *
     * @param sizeMb 剩余几兆
     * @return 小于某个值返回false，如果有足够的空间，则返回true
     * @author X.W 2014-12-12 下午4:56:04
     */
    public static boolean isSDCardAvaiableSpace(int sizeMb) {
        boolean ishasSpace = false;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            if (availableSpare > sizeMb) {
                ishasSpace = true;
            }
        }
        return ishasSpace;
    }

    /**
     * 获取sdcard的根目录
     *
     * @return
     */
    public final static String getSdcardDirectory() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 获取本地ip地址
     *
     * @return
     */
    public final static String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * netType = 2 为移动网络
     * netType = 1 为WiFi网络
     * nteType = 0 为无网络
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            netType = 2;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    /**
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
     *
     * @param context
     * @return
     */
    public final static int getAPNType(Context context) {
        if (context == null) {
            return -1;
        }
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
//            Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = CMNET;
            } else {
                netType = CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public final static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth(); // 屏幕宽度（像素）
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public final static int getDisplayHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight(); // 屏幕宽度（像素）
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public final static int getDisplayWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();// metric.widthPixels;
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();// metric.heightPixels;
        return width; // 屏幕宽度（像素）
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public final static int getDisplayHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();// metric.widthPixels;
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();// metric.heightPixels;
        return height; // 屏幕宽度（像素）
    }


    public final static int getHeightPixels(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public final static int getWidthPixels(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public final static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public final static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int spTopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        Log.i(TAG, "isServiceRunning: ");
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = null;
        if (activityManager != null) {
            serviceList = activityManager.getRunningServices(30);
            if (!(serviceList.size() > 0)) {
                Log.i(TAG, "isServiceRunning: 获取服务的数量小于0");
                return false;
            }
            for (int i = 0; i < serviceList.size(); i++) {
                Log.i(TAG, "isServiceRunning: "+serviceList.get(i).service.getClassName());
                if (serviceList.get(i).service.getClassName().contains(className)) {
                    isRunning = true;
                    break;
                }
            }
            Log.i(TAG, "isServiceRunning: 撒都没有");
        }else {
            Log.i(TAG, "isServiceRunning: == null");
        }
        return isRunning;
    }

    /**
     * 手机震动提示
     */
    public static void mobileShake(Context context, int ms) {
        Object obj = context.getSystemService(Service.VIBRATOR_SERVICE);
        if (obj instanceof Vibrator) {
            ((Vibrator) obj).vibrate(ms);
        }
    }
}
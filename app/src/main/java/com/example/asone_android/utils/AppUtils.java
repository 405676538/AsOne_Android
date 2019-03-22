package com.example.asone_android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.asone_android.bean.EventBusMessage;
import com.example.asone_android.net.ApiClient;
import com.example.asone_android.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by 唐浩 on 2018/3/9.
 */

public class AppUtils {
    private static final String TAG = "AppUtils";





    public static String getDownLoadFileUrl(String fileId){
        return ApiClient.baseUrl+"/downLoadFile/"+fileId;
    }



    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void goLogin(){
        EventBus.getDefault().post(new EventBusMessage(EventBusMessage.SHOW_NO_LOGIN));
    }




    /** 登录失效功能，活的的Url时 需要的参数 **/
//
//    public static OkHttpClient getOkClient(){
//        return new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
//    }
//
//    public static Request postOk3Request(Map<String,String> map, String url){
//        Log.i(TAG, "postOk3Request: "+url);
//        FormBody.Builder builder = new FormBody.Builder();
//        for (String key : map.keySet()) {
//            builder.add(key, map.get(key));
//        }
//        RequestBody formBody = builder.build();
//        return new Request.Builder().url(url)
//                .post(formBody)
//                .build();
//    }
//
//    public static Request getOk3Request(String url){
//        Log.i(TAG, "postOk3Request: "+url);
//        Request request = new Request.Builder().url(url)
//                .get()
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .build();
//        return request;
//    }
//
//    public static Request getOk3Request(String url, String token){
//        Log.i(TAG, "postOk3Request: "+url);
//        Request request = new Request.Builder().url(url)
//                .get()
//                .addHeader(Constant.APP_TOKEN,
//                        SpHelper.getInstance().getToken())
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .build();
//        return request;
//    }

    public static LoadingDialog showCricleDialog(Context context) {
        LoadingDialog dialog = new LoadingDialog.Builder(context).create();
        dialog.show();
        return dialog;
    }

    public static LoadingDialog getCricleDialog(Context context) {
        return new LoadingDialog.Builder(context).create();
    }


    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 4根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 拨打电话
     */
    public static void toPhoto(Context context, String mPhoto) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhoto));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 获取通讯录名字和电话
     */
    public static String getContactPhone(Cursor cursor, Context context) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int nameIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    phoneNumber = numberIntercept(phoneNumber);
                    if (phoneNumber.length() >= 11) {
                        char c = phoneNumber.charAt(phoneNumber.length() - 11);
                        Log.i(TAG, "getContactPhone: " + c + "\n" + phoneNumber);
                        if ("1".equals(String.valueOf(c))) {
                            phoneNumber = phoneNumber.substring(phoneNumber.length() - 11, phoneNumber.length());
                        } else {
                            phoneNumber = "";
                            Toast.makeText(context, "手机号格式不对", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        phoneNumber = "";
                        Toast.makeText(context, "手机号格式不对", Toast.LENGTH_SHORT).show();
                    }
                    String name = phone.getString(nameIndex);
                    Log.i(TAG, "getContactPhone:   phone_type=" + phone_type);
                    Log.i(TAG, "getContactPhone:   phoneNumber=" + phoneNumber);
                    Log.i(TAG, "getContactPhone:   name=" + name);
                    result = phoneNumber + "," + name;

                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }

    /**
     * 提取字符串中的数字
     *
     * @param number
     * @return
     * @throws Exception
     */
    private static Pattern mPattern = Pattern.compile("[^0-9]");

    public static String numberIntercept(String number) {
        return mPattern.matcher(number).replaceAll("");
    }



    /**
     * 获取悬浮窗权限
     */
    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null) {
                    return false;
                }
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }


    /**
     * 打开app设置主页
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }


    /**
     * 发送短信
     */
    public static void toSms(Context context, String number) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setData(Uri.parse("smsto:" + number));
//        sendIntent.putExtra("sms_body", body);
        context.startActivity(sendIntent);

    }



    public static PackageInfo version(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 直接获取联系人列表
     */
    //读取手机里面的联系人
    public static List<Map<String, String>> getContactInfo(Activity activity) {
        //吧所有联系人填进去
        List<Map<String, String>> list = new ArrayList<>();
        ContentResolver resolver = activity.getContentResolver();
        String data = "content://com.android.contacts/raw_contacts/data";
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uridata = Uri.parse(data);
        @SuppressLint("Recycle") Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(0);
                if (contact_id != null) {
                    //具体的某一个联系人
                    Map<String, String> map = new HashMap<>();
                    Cursor datacursor = resolver.query
                            (uridata, new String[]{"data1", "mimetype"}, "contact_id=?", new String[]{contact_id}, null);
                    if (datacursor != null) {
                        while (datacursor.moveToNext()) {
                            String data1 = datacursor.getString(0);
                            String mimetype = datacursor.getString(1);
                            if ("vnd.android.cursor.item/name".equals(mimetype)) {
                                //联系人的姓名
                                map.put("name", data1);
                            } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                                //联系人的电话号码
                                map.put("phone", data1);
                            }

                        }
                    }
                    list.add(map);
                    if (datacursor != null) {
                        datacursor.close();
                    }
                }
            }
        }

        return list;
    }

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    //联系人提供者的uri
    private static Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;



    public static int getColor(int id, Context context) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * 61      * 判断当前应用在桌面是否有桌面快捷方式
     * 62      *
     * 63      * @param context
     * 64
     */




    /**    一下获取mac地址  一下获取mac地址一下获取mac地址一下获取mac地址一下获取mac地址一下获取mac地址一下获取mac地址  */

    public static String getMac(Context context) {

        String strMac = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以下");
            strMac = getLocalMacAddressFromWifiInfo(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以上7.0以下");
            strMac = getMacAddress(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("=====", "7.0以上");
            if (!TextUtils.isEmpty(getMacAddress(context))) {
                Log.e("=====", "7.0以上1");
                strMac = getMacAddress(context);
                return strMac;
            } else if (!TextUtils.isEmpty(getMachineHardwareAddress())) {
                Log.e("=====", "7.0以上2");
                strMac = getMachineHardwareAddress();
                return strMac;
            } else {
                Log.e("=====", "7.0以上3");
                strMac = getLocalMacAddressFromBusybox();
                return strMac;
            }
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 根据wifi信息获取本地mac
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        return mac;
    }

    /**
     * android 6.0及以上、7.0以下 获取mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial;
    }

    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * android 7.0及以上 （2）扫描各个网络接口获取mac地址
     *
     */
    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }


    /**
     * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
     *
     */

    /**
     * 根据busybox获取本地Mac
     *
     * @return
     */
    public static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "网络异常";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }

    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            while ((line = br.readLine()) != null
                    && line.contains(filter) == false) {
                result += line;
            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}

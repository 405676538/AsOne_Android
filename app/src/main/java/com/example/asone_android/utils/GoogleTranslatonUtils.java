package com.example.asone_android.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;


/****
 * 用这个类进行翻译（最底部使用方法）
 *
 *
 *
 * **/


public class GoogleTranslatonUtils {
    public static final String TRANSLATE_BASE_URL = "https://translate.google.cn/"; // 不需要翻墙即可使用
    //    public static final String TRANSLATE_SINGLE_URL = "https://translate.google.cn/translate_a/single?client=gtx&sl=en&tl=zh&dt=t&q=Do%20not%20work%20overtime%20tonight";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    private static String LAN_AUTO = "auto";

    /**
     * 翻译，包含http请求，需要异步，返回""则为翻译失败
     *
     * @param sourceLan 源语言，如en，自动检测为auto
     * @param targetLan 目标语言如zh
     * @param content   翻译文本
     * @return ""为翻译失败，其余成功
     */
    public void translate(Context context, String sourceLan, String targetLan, String content, TranslateCallback callback) {
        TranslateTask task = new TranslateTask(context, sourceLan, targetLan, content, callback);
        task.executeOnExecutor(Executors.newCachedThreadPool());
    }

    /**
     * 使用异步任务来翻译，翻译完成后回调callback
     */
    class TranslateTask extends AsyncTask<Void, Void, String> {
        String sourceLan;
        String targetLan;
        String content;
        Context context;
        TranslateCallback callback;

        TranslateTask(Context context, String sourceLan, String targetLan, String content, TranslateCallback callback) {
            this.context = context;
            this.content = content;
            this.callback = callback;
            this.sourceLan = sourceLan;
            this.targetLan = targetLan;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            if (content == null || content.equals("")) {
                return result;
            }
            try {
                String googleResult = "";
                // 新建一个URL对象
                URL url = new URL(getTranslateUrl(sourceLan, targetLan, content));
                // 打开一个HttpURLConnection连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // 设置连接主机超时时间
                urlConn.setConnectTimeout(5 * 1000);
                //设置从主机读取数据超时
                urlConn.setReadTimeout(5 * 1000);
                // 设置是否使用缓存  默认是true
                urlConn.setUseCaches(false);
                // 设置为Post请求
                urlConn.setRequestMethod("GET");
                //urlConn设置请求头信息
                urlConn.setRequestProperty("User-Agent", USER_AGENT);
                //设置请求中的媒体类型信息。
//            urlConn.setRequestProperty("Content-Type", "application/json");
                //设置客户端与服务连接类型
//            urlConn.addRequestProperty("Connection", "Keep-Alive");
                // 开始连接
                urlConn.connect();
                // 判断请求是否成功
                int statusCode = urlConn.getResponseCode();
                if (statusCode == 200) {
                    // 获取返回的数据
                    googleResult = streamToString(urlConn.getInputStream());
                }
                // 关闭连接
                urlConn.disconnect();

                // 处理返回结果，拼接
                JSONArray jsonArray = new JSONArray(googleResult).getJSONArray(0);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result += jsonArray.getJSONArray(i).getString(0);
                }
            } catch (Exception e) {
//            result = "翻译失败";
                e.printStackTrace();
                result = "";
            }
            Log.d("TAG", "翻译结果：" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                callback.onTranslateDone(result);
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return 字符串
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            is.close();
            byte[] byteArray = out.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }

    public void translate(Context context, String targetLan, String content, TranslateCallback callback) {
        translate(context, LAN_AUTO, targetLan, content, callback);
    }


    private static String getTranslateUrl(String sourceLan, String targetLan, String content) {
        try {
            return TRANSLATE_BASE_URL + "translate_a/single?client=gtx&sl=" + sourceLan + "&tl=" + targetLan + "&dt=t&q=" + URLEncoder.encode(content, "UTF-8");
        } catch (Exception e) {
            return TRANSLATE_BASE_URL + "translate_a/single?client=gtx&sl=" + sourceLan + "&tl=" + targetLan + "&dt=t&q=" + content;
        }
    }

    public interface TranslateCallback{
        public void onTranslateDone(String result);
    }
}



//    // 使用方法   英翻中
//    TranslateCallback translateCallback = new TranslateCallback() {
//        @Override
//        public void onTranslateDone(String result) {
//            // result是翻译结果，在这里使用翻译结果，比如使用对话框显示翻译结果
//        }
//    }
//new TranslateUtil().translate(activity, "en", "zh-cn", "待翻译内容", translateCallback);

/**
 * 第二 ， 三 个参数是语言
 * auto”:””Automatic”,
 * “af”:”Afrikaans”,
 * “sq”:”Albanian”,
 * “ar”:”Arabic”,
 * “hy”:”Armenian”,
 * “az”:”Azerbaijani”,
 * “eu”:”Basque”,
 * “be”:”Belarusian”,
 * “bn”:”Bengali”,
 * “bs”:”Bosnian”,
 * “bg”:”Bulgarian”,
 * “ca”:”Catalan”,
 * “ceb”:”Cebuano”,
 * “ny”:”Chichewa”,
 * “zh-cn”:”Chinese Simplified”,
 * “zh-tw”:”Chinese Traditional”,
 * “co”:”Corsican”,
 * “hr”:”Croatian”,
 * “cs”:”Czech”,
 * “da”:”Danish”,
 * “nl”:”Dutch”,
 * “en”:”English”,
 * “eo”:”Esperanto”,
 * “et”:”Estonian”,
 * “tl”:”Filipino”,
 * “fi”:”Finnish”,
 * “fr”:”French”,
 * “fy”:”Frisian”,
 * “gl”:”Galician”,
 * “ka”:”Georgian”,
 * “de”:”German”,
 * “el”:”Greek”,
 * “gu”:”Gujarati”,
 * “ht”:”Haitian Creole”,
 * “ha”:”Hausa”,
 * “haw”:”Hawaiian”,
 * “iw”:”Hebrew”,
 * “hi”:”Hindi”,
 * “hmn”:”Hmong”,
 * “hu”:”Hungarian”,
 * “is”:”Icelandic”,
 * “ig”:”Igbo”,
 * “id”:”Indonesian”,
 * “ga”:”Irish”,
 * “it”:”Italian”,
 * “ja”:”Japanese”,
 * “jw”:”Javanese”,
 * “kn”:”Kannada”,
 * “kk”:”Kazakh”,
 * “km”:”Khmer”,
 * “ko”:”Korean”,
 * “ku”:”Kurdish (Kurmanji)”,
 * “ky”:”Kyrgyz”,
 * “lo”:”Lao”,
 * “la”:”Latin”,
 * “lv”:”Latvian”,
 * “lt”:”Lithuanian”,
 * “lb”:”Luxembourgish”,
 * “mk”:”Macedonian”,
 * “mg”:”Malagasy”,
 * “ms”:”Malay”,
 * “ml”:”Malayalam”,
 * “mt”:”Maltese”,
 * “mi”:”Maori”,
 * “mr”:”Marathi”,
 * “mn”:”Mongolian”,
 * “my”:”Myanmar (Burmese)”,
 * “ne”:”Nepali”,
 * “no”:”Norwegian”,
 * “ps”:”Pashto”,
 * “fa”:”Persian”,
 * “pl”:”Polish”,
 * “pt”:”Portuguese”,
 * “ma”:”Punjabi”,
 * “ro”:”Romanian”,
 * “ru”:”Russian”,
 * “sm”:”Samoan”,
 * “gd”:”Scots Gaelic”,
 * “sr”:”Serbian”,
 * “st”:”Sesotho”,
 * “sn”:”Shona”,
 * “sd”:”Sindhi”,
 * “si”:”Sinhala”,
 * “sk”:”Slovak”,
 * “sl”:”Slovenian”,
 * “so”:”Somali”,
 * “es”:”Spanish”,
 * “su”:”Sudanese”,
 * “sw”:”Swahili”,
 * “sv”:”Swedish”,
 * “tg”:”Tajik”,
 * “ta”:”Tamil”,
 * “te”:”Telugu”,
 * “th”:”Thai”,
 * “tr”:”Turkish”,
 * “uk”:”Ukrainian”,
 * “ur”:”Urdu”,
 * “uz”:”Uzbek”,
 * “vi”:”Vietnamese”,
 * “cy”:”Welsh”,
 * “xh”:”Xhosa”,
 * “yi”:”Yiddish”,
 * “yo”:”Yoruba”,
 * “zu”:”Zulu”
 *
 * */

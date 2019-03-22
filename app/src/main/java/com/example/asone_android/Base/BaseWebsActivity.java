package com.example.asone_android.Base;

import android.os.Build;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.asone_android.app.Constant;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;

/**
 *
 * @author 唐浩
 * @date 2018/4/23
 */

public abstract class BaseWebsActivity extends BaseActivity {
    private AgentWeb mAgentWeb;


    @Override
    public void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishWeb();
    }

    private void finishWeb() {
        if (mAgentWeb.back()) {

        } else {
            finish();
        }
    }

    public AgentWeb initWeb(ViewGroup llMain, String url, Object JavaScriptInterfaceObject) {
//        new InsideWebChromeClient(llMain)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        AgentWebConfig.removeAllCookies();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llMain, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .additionalHttpHeader("token", "")
                .setWebViewClient(webViewClient)
                .setWebChromeClient(null)
                .createAgentWeb()
                .ready()
                .go(url);
        mAgentWeb.getJsInterfaceHolder().addJavaObject(Constant.JAVASCRIPT_ANDROID_TAG, JavaScriptInterfaceObject);
        WebSettings webSettings = mAgentWeb.getAgentWebSettings().getWebSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);//缩放
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        return mAgentWeb;

    }

    public AgentWeb initWeb(ViewGroup llMain, String url, Object JavaScriptInterfaceObject, boolean is) {
//        new InsideWebChromeClient(llMain)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        AgentWebConfig.removeAllCookies();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llMain, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .setWebViewClient(webViewClient)
                .setWebChromeClient(null)
                .createAgentWeb()
                .ready()
                .go(url);
        mAgentWeb.getJsInterfaceHolder().addJavaObject(Constant.JAVASCRIPT_ANDROID_TAG, JavaScriptInterfaceObject);
        WebSettings webSettings = mAgentWeb.getAgentWebSettings().getWebSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);//缩放
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        return mAgentWeb;

    }

    private class JavaScriptInterfaceObject {
        @JavascriptInterface
        public void setTopBarTitle(String title) {
            runOnUiThread(() -> setTopBarTitle(title));
        }


    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


}

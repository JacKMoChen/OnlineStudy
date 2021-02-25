package com.jack.common.base

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jack.common.R
import com.just.agentweb.*
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @description:WebActivity封装
 * 第三方库 https://github.com/Justson/AgentWeb
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/2/4 17:21
 */
class WebActivity : AppCompatActivity() {
    private lateinit var mAgentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(ll_webView, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(resources.getColor(R.color.teal_700))
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .interceptUnkownUrl()
            .createAgentWeb()
            .ready()
            .get()

        val url = intent.getStringExtra("webUrl")
        mAgentWeb.urlLoader.loadUrl(url)
        //开始调试
        AgentWebView.setWebContentsDebuggingEnabled(true)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            //do you work
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    companion object {
        fun openUrl(context: Context, url: String?) {
            context.startActivity(Intent(context, WebActivity::class.java).also {
                it.putExtra("webUrl", url)
            })
        }
    }

}
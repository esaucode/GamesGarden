package com.esaudev.gamesgarden.ui.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.esaudev.gamesgarden.R

class WebViewFragment : DialogFragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private var pageURL = ""
    private var pageTitle = ""
    private var flow = ""
    private var flowPaypal = "paypal"

    private val webViewDialogListener: WebViewDialogListener?
        get() = (context as? WebViewDialogListener) ?: (parentFragment as? WebViewDialogListener)

    companion object {

        const val TAG = "WebDialogFragment"

        private const val URL = "url"
        private const val PAGE_TITLE = "pageTitle"
        private const val FLOW = "flow"

        @JvmStatic
        fun newInstance(
            pageUrl: String,
            pageTitle: String,
            flow: String = ""
        ): WebViewFragment {
            val args = Bundle()
            args.putString(URL, pageUrl)
            args.putString(PAGE_TITLE, pageTitle)
            args.putString(FLOW, flow)
            val fragment = WebViewFragment()
            fragment.arguments = args
            return fragment
        }

    }

    @NonNull
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_base)
        toolbar.setNavigationIcon(R.drawable.ic_launcher_background)
        toolbar.setNavigationOnClickListener {
            dismiss()
        }

        pageURL = arguments?.getString(URL, "").toString()
        pageTitle = arguments?.getString(PAGE_TITLE, "").toString()
        flow = arguments?.getString(FLOW, "").toString()

        if (pageTitle.isEmpty())
            toolbar.title = pageURL
        else toolbar.title = pageTitle

    }

    private fun setupView(view: View) {
        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressBar)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onStart() {
        super.onStart()

        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(pageURL)
        webView.webViewClient = object : WebViewClient() {

            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                return when (flow) {
                    flowPaypal -> {
                        when (Uri.parse(url).lastPathSegment) {
                            "paypal_return" -> {
                                val token = Uri.parse(url).getQueryParameter("token")
                                val payerId = Uri.parse(url).getQueryParameter("PayerID")
                                token?.let { itToken ->
                                    payerId?.let { itPayerId ->
                                        webViewDialogListener?.getTokenAndPayerIdPaypal(
                                            itToken,
                                            itPayerId
                                        )
                                    }
                                }
                                dismiss()
                                false
                            }
                            "paypal_cancel" -> {
                                dismiss()
                                false
                            }
                            else -> {
                                view?.loadUrl(url)
                                true
                            }
                        }
                    }
                    else -> {
                        view?.loadUrl(url)
                        true
                    }
                }
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                if (::progressBar.isInitialized && progressBar.isVisible)
                    progressBar.visibility = View.GONE
            }
        }
    }

    interface WebViewDialogListener {
        fun getTokenAndPayerIdPaypal(token: String, payerId: String)
    }
}
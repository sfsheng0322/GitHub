package com.sunfusheng.github.widget.app;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunfusheng.github.R;

/**
 * @author sunfusheng on 2018/4/20.
 */
public class ContributionsWebView extends WebView {

    public ContributionsWebView(Context context) {
        this(context, null);
    }

    public ContributionsWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContributionsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.white));
        setOnLongClickListener(v -> true);

        getSettings().setDefaultFontSize(12);
        getSettings().setSupportZoom(false);
        getSettings().setDefaultTextEncodingName("UTF-8");

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                postDelayed(() -> scrollTo(Integer.MAX_VALUE, 0), 50);
            }
        });
    }

    public void loadData(String data) {
        postDelayed(() -> {
            loadDataWithBaseURL(null, data, "text/html; charset=UTF-8", null, null);
            scrollTo(Integer.MAX_VALUE, 0);
        }, 50);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, 0, clampedX, clampedY);
    }
}

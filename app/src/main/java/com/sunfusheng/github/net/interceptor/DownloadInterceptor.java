package com.sunfusheng.github.net.interceptor;

import com.sunfusheng.github.net.download.IDownloadListener;
import com.sunfusheng.github.net.download.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author sunfusheng on 2018/4/24.
 */
public class DownloadInterceptor implements Interceptor {

    private IDownloadListener downloadListener;

    public DownloadInterceptor(IDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), downloadListener))
                .build();
    }
}

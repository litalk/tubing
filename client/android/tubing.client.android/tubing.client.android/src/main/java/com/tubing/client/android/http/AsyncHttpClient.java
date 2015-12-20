package com.tubing.client.android.http;

import android.os.AsyncTask;

import java.net.URL;

public abstract class AsyncHttpClient extends AsyncTask<URL, Integer, HttpResponse> {

    private PostHttpCall _delegate;

    public AsyncHttpClient(PostHttpCall delegate) {

        _delegate = delegate;
    }

    @Override
    protected HttpResponse doInBackground(URL... urls) {

        HttpResponse ret = httpCall(urls[0]);
        _delegate.action(ret);

        return ret;
    }

    protected abstract HttpResponse httpCall(URL url);

    public interface PostHttpCall {

        void action(HttpResponse response);
    }
}

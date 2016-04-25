package com.tubing.client.android.http;

import com.tubing.client.android.common.StringUtils;

import java.net.URL;
import java.util.Map;

public class AsyncHttpClientPost extends AsyncHttpClient {

    private final String _body;
    private final Map<String, String> _headers;
    private final Map<String, String> _cookies;

    public AsyncHttpClientPost(String body,
                               Map<String, String> cookies) {

        this(body, null, cookies, null);
    }

    public AsyncHttpClientPost(String body,
                               Map<String, String> headers,
                               Map<String, String> cookies,
                               PostHttpCall delegate) {

        super(delegate);
        _body = body;
        _headers = headers;
        _cookies = cookies;
    }

    @Override
    protected HttpResponse httpCall(URL url) {

        return new HttpClient().post(url, StringUtils.toByteArray(_body), _headers, _cookies);
    }
}

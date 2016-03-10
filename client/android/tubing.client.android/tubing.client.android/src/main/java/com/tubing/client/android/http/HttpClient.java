package com.tubing.client.android.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpClient {
    
    private final Map<String, String> _cookies = new HashMap<String, String>();

    public HttpResponse get(
            URL url,
            String queryString,
            Map<String, String> headers) {

        return doHttp(Constants.GET, url.toString(), queryString, null, headers);
    }
    
    public HttpResponse post(
            URL url,
            byte[] data,
            Map<String, String> headers) {

        return doHttp(Constants.POST, url.toString(), null, data, headers);
    }

    private HttpResponse doHttp(
            String type,
            String url,
            String queryString,
            byte[] data,
            Map<String, String> headers) {

        HttpResponse ret = null;
        if (queryString != null && !"".equals(queryString)) {
            url += "?" + queryString;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(type);
            System.setProperty("http.keepAlive", "false");
            prepareHttpRequest(connection, headers, data);
            connection.connect();
            ret = retrieveHtmlResponse(connection);
            updateCookies(ret);
        } catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
        
        return ret;
    }
    
    private void prepareHttpRequest(
            HttpURLConnection connnection,
            Map<String, String> headers,
            byte[] bytes) {
        
        // set all cookies for request
        connnection.setRequestProperty(Constants.COOKIE, getCookies());
        
        setConnectionHeaders(connnection, headers);
        
        setConnectionData(connnection, bytes);
    }
    
    private void setConnectionData(HttpURLConnection connnection, byte[] bytes) {
        
        if (bytes != null && bytes.length > 0) {
            connnection.setDoOutput(true);
            try {
                OutputStream out = connnection.getOutputStream();
                out.write(bytes);
                out.flush();
                out.close();
            } catch (Throwable cause) {
                throw new RuntimeException(cause);
            }
        }
    }
    
    private void setConnectionHeaders(HttpURLConnection connnection, Map<String, String> headers) {
        
        if (headers != null) {
            Iterator<Map.Entry<String, String>> headersIterator = headers.entrySet().iterator();
            while (headersIterator.hasNext()) {
                Map.Entry<String, String> header = headersIterator.next();
                connnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
    }

    private HttpResponse retrieveHtmlResponse(HttpURLConnection connection) {

        HttpResponse ret = new HttpResponse();
        
        try {
            ret.setStatusCode(connection.getResponseCode());
            ret.setHeaders(connection.getHeaderFields());
        } catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
        
        InputStream inputStream;
        // select the source of the input bytes, first try 'regular' input
        try {
            inputStream = connection.getInputStream();
        }
        // if the connection to the server somehow failed, for example 404 or 500,
        // con.getInputStream() will throw an exception, which we'll keep.
        // we'll also store the body of the exception page, in the response data.
        catch (Throwable e) {
            inputStream = connection.getErrorStream();
            ret.setFailure(e);
        }
        
        // this takes data from the previously set stream (error or input) 
        // and stores it in a byte[] inside the response
        ByteArrayOutputStream container = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int read;
        try {
            while ((read = inputStream.read(buf, 0, 1024)) > 0) {
                container.write(buf, 0, read);
            }
            ret.setData(container.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        return ret;
    }
    
    private void updateCookies(HttpResponse response) {
        
        Iterable<String> newCookies = response.getHeaders().get(Constants.SET_COOKIE);
        if (newCookies != null) {
            for (String cookie : newCookies) {
                int equalIndex = cookie.indexOf('=');
                int semicolonIndex = cookie.indexOf(';');
                String cookieKey = cookie.substring(0, equalIndex);
                String cookieValue = cookie.substring(equalIndex + 1, semicolonIndex);
                _cookies.put(cookieKey, cookieValue);
            }
        }
    }
    
    private String getCookies() {
        
        StringBuilder ret = new StringBuilder();
        if (!_cookies.isEmpty()) {
            for (Map.Entry<String, String> entry : _cookies.entrySet()) {
                ret.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
        }
        
        return ret.toString();
    }

    private interface Constants {

        static final String GET = "GET";
        static final String POST = "POST";
        static final String PUT = "PUT";
        static final String COOKIE = "Cookie";
        static final String SET_COOKIE = "Set-Cookie";
    }
}

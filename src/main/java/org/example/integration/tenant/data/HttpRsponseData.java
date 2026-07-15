package org.example.integration.tenant.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpRsponseData {
    private final String body;
    private final int statusCode;
    private Map<String, List<String>> headers;

    public HttpRsponseData(String body, int statusCode, Map<String, List<String>> headers) {
        this.body = body;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    // Overloaded constructor for errors or when headers aren't needed
    public HttpRsponseData(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.headers = Collections.emptyMap();
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}

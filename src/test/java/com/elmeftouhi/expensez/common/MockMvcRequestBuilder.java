package com.elmeftouhi.expensez.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.join;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class MockMvcRequestBuilder {

    private final MockMvc mockMvc;
    private final String urlTemplate;
    private String body;
    private final HttpHeaders headers = new HttpHeaders();
    private final Map<String, String> pathParams = new HashMap<>();
    private final HttpMethod httpMethod;
    private final MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();

    public MockMvcRequestBuilder(MockMvc mockMvc, HttpMethod httpMethod, String urlTemplate){
        this.mockMvc = mockMvc;
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
    }

    public MockMvcRequestBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public MockMvcRequestBuilder withHeader(String name, String value){
        this.headers.set(name, value);
        return this;
    }

    public MockMvcRequestBuilder withPathParam(String name, Object value){
        this.pathParams.put(name, value == null ? null : value.toString());
        return this;
    }

    public MockMvcRequestBuilder withQueryParam(String name, Object value) {
        if (value instanceof Iterator<?> iterable) {
            this.queryParameters.set(name, join(iterable, ","));
            return this;
        }
        this.queryParameters.set(name, value == null ? null : value.toString());
        return this;
    }

    public ResultActions execute() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = buildRequestURI()
                .headers(buildHeaders())
                .params(this.queryParameters);
        if (body != null) {
            requestBuilder.contentType(APPLICATION_JSON);
            requestBuilder.content(body);
        }
        return mockMvc.perform(requestBuilder);
    }

    private HttpHeaders buildHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(this.headers);
        return httpHeaders;
    }

    private MockHttpServletRequestBuilder buildRequestURI(){
        String url = generateFullUrl();
        return MockMvcRequestBuilders.request(httpMethod, url);
    }

    private String generateFullUrl(){
        final String[] url = {this.urlTemplate};
        this.pathParams.forEach((key, value) -> url[0] = url[0].replace("{" + key + "}", value));
        return url[0];
    }
}

package com.study.test.global.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper((HttpServletRequest) request);

        long startAt = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, response);
        long endAt = System.currentTimeMillis();

        log.info("\n" +
                        "[REQUEST] {} - {} {} - {}\n" +
                        "Headers : {}\n" +
                        "Request : {}\n",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                (endAt - startAt) / 1000.0,
                getHeaders(request),
                getRequestBody(requestWrapper));
    }

    private Map getHeaders(HttpServletRequest request) {
        Map<Object, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    return " - ";
                }
            }
        }
        return " - ";
    }

}

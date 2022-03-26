package com.study.test.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    //extends OncePerRequestFilter를 하는 이유는 filter로 등록하기 위해서 상속 받는다.

    private final ObjectMapper objectMapper;
    //objectMapper를 DI하는 이유는 error가 나서 ErrorResponse를 해야할 때 json형식으로 변환해주기 위해서 사용한다.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            sendErrorMessage(response, e.getErrorCode());
        } catch (Exception e) {
            logger.error(e);
            sendErrorMessage(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendErrorMessage(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
        String errorResponseJson = objectMapper.writeValueAsString(customErrorResponse);

        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(errorResponseJson);
    }
}

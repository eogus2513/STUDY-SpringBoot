package com.study.test.domain.service;

import com.study.test.domain.controller.dto.request.SignInRequest;
import com.study.test.domain.controller.dto.request.SignUpRequest;
import com.study.test.domain.controller.dto.response.TokenResponse;
import com.study.test.domain.controller.dto.response.UserResponse;
import com.study.test.domain.domain.RefreshToken;
import com.study.test.domain.domain.User;
import com.study.test.domain.domain.repository.RefreshTokenRepository;
import com.study.test.domain.domain.repository.UserRepository;
import com.study.test.domain.exception.PasswordMisMatchException;
import com.study.test.domain.exception.RefreshTokenNotFoundException;
import com.study.test.domain.exception.UserExistsException;
import com.study.test.domain.exception.UserNotFoundException;
import com.study.test.global.security.jwt.JwtProperties;
import com.study.test.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponse getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return UserResponse.builder()
                .accountId(user.getAccountId())
                .name(user.getName())
                .build();
    }

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.findByAccountId(request.getAccountId()).isPresent()) {
            throw UserExistsException.EXCEPTION;
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    public TokenResponse signIn(SignInRequest request) {
        User user = userRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw PasswordMisMatchException.EXCEPTION;
        }

        TokenResponse tokens = createJwtToken(user.getAccountId());

        return TokenResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .build();
    }

    public TokenResponse tokenRefresh(String refreshToken) {
        String parseToken = jwtTokenProvider.parseToken(refreshToken);
        RefreshToken redisRefreshToken = refreshTokenRepository.findByToken(parseToken)
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        TokenResponse tokens = createJwtToken(redisRefreshToken.getAccountId());
        redisRefreshToken.updateToken(tokens.getRefreshToken(), jwtProperties.getRefreshExp());

        return TokenResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .build();
    }

    private TokenResponse createJwtToken(String id) {
        String accessToken = jwtTokenProvider.generateAccessToken(id);
        String refreshToken = jwtTokenProvider.generateRefreshToken(id);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}

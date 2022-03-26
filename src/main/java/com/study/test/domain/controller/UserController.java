package com.study.test.domain.controller;

import com.study.test.domain.controller.dto.request.SignInRequest;
import com.study.test.domain.controller.dto.request.SignUpRequest;
import com.study.test.domain.controller.dto.response.TokenResponse;
import com.study.test.domain.controller.dto.response.UserResponse;
import com.study.test.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponse currentUser() {
        return userService.getCurrentUser();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void singUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/token")
    public TokenResponse signIn(@RequestBody @Valid SignInRequest request) {
        return userService.signIn(request);
    }

    @PatchMapping("/token")
    public TokenResponse refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return userService.tokenRefresh(refreshToken);
    }

}

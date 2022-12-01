package com.CouponSystemStage2.CouponSystem.Controllers;

import com.CouponSystemStage2.CouponSystem.Login.LoginManager;
import com.CouponSystemStage2.CouponSystem.Token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ClientController {
    @Autowired
    protected LoginManager loginManager;
    @Autowired
    protected TokenManager tokenManager;

    abstract ResponseEntity<?> login(String email, String password) throws Exception;
}

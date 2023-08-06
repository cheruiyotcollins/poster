package com.kabianga.tp.poster.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
}

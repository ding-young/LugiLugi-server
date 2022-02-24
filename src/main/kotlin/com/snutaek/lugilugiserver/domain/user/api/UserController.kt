package com.snutaek.lugilugiserver.domain.user.api

import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.service.UserService
import com.snutaek.lugilugiserver.global.auth.JwtTokenProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/user")
class UserController (
    private val userService : UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/signup/")
    fun signup(@Valid @RequestBody signupRequest: UserDto.SignupRequest) : UserDto.SignupResponse {
        val user = userService.signup(signupRequest)
        val token = jwtTokenProvider.generateToken(user.email)
        return UserDto.SignupResponse(user.id, token)  // TODO ask front and erase token in body (add it to header)
    }

}
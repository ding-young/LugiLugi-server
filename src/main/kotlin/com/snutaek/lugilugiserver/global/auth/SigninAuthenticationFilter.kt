package com.snutaek.lugilugiserver.global.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.global.auth.model.UserPrincipal
import com.wafflestudio.seminar.global.auth.dto.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.BufferedReader
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SigninAuthenticationFilter(
    authenticationManager: AuthenticationManager?,
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    init {
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/v1/user/signin/", "POST"))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val token = jwtTokenProvider.generateToken(authResult)
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        response.addHeader("Authentication", token)
        // TODO ask front and erase token in body
        response.status = HttpServletResponse.SC_OK // haha....

        val out = response.writer
        val principal = authResult.principal as UserPrincipal
        val responseString = objectMapper.writeValueAsString(UserDto.SignupResponse(principal.user.id, token))
        out.print(responseString)
        out.flush()
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        super.unsuccessfulAuthentication(request, response, failed);
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // Parse auth request
        val parsedRequest: LoginRequest = parseRequest(request)
        val authRequest: Authentication =
            UsernamePasswordAuthenticationToken(parsedRequest.userId, parsedRequest.password)
        return authenticationManager.authenticate(authRequest)
    }

    private fun parseRequest(request: HttpServletRequest): LoginRequest {
        val reader: BufferedReader = request.reader
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(reader, LoginRequest::class.java)
    }

}

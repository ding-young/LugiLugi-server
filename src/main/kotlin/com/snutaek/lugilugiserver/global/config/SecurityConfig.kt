package com.snutaek.lugilugiserver.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.snutaek.lugilugiserver.global.auth.SigninAuthenticationFilter
import com.snutaek.lugilugiserver.global.auth.JwtAuthenticationEntryPoint
import com.snutaek.lugilugiserver.global.auth.JwtAuthenticationFilter
import com.snutaek.lugilugiserver.global.auth.JwtTokenProvider
import com.snutaek.lugilugiserver.global.auth.model.UserPrincipalDetailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper, // added for.. signin response body
    private val userPrincipalDetailService: UserPrincipalDetailService,
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
//        return Argon2PasswordEncoder()
        return BCryptPasswordEncoder()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userPrincipalDetailService)
        return provider
    }

    @Value("\${mobile}")  // TODO tmp
    lateinit var mobile: String

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowCredentials = true
        corsConfiguration.addAllowedOrigin("http://localhost:3000") // TODO front url
        corsConfiguration.addAllowedOrigin("https://d3rvdfuvmiieqv.cloudfront.net") // TODO front url
        corsConfiguration.addAllowedOrigin(mobile) // TODO front url
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.addExposedHeader("Authentication")  // for jwt token.. 근데 지금 프론트는 바디로 받음

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }

    override fun configure(http: HttpSecurity) {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .addFilter(SigninAuthenticationFilter(authenticationManager(), jwtTokenProvider, objectMapper))
            .addFilter(JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider))
            .authorizeRequests()
            .antMatchers("/api/v1/user/signin/").permitAll()  // Auth entrypoint
            .antMatchers("/ws/*").permitAll()  // ws tmp..
            .antMatchers("/ws/**").permitAll()  // ws tmp..
            .antMatchers(HttpMethod.POST, "/api/v1/user/signup/").anonymous()  // SignUp user
            .antMatchers(HttpMethod.GET, "/api/v1/user/ping/").permitAll()
            .anyRequest().authenticated()
    }

}

package com.snutaek.lugilugiserver.global.auth.model

import com.snutaek.lugilugiserver.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(val user: User) : UserDetails {
    override fun getUsername(): String {
        return user.userId
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getAuthorities(): List<GrantedAuthority> { // TODO FIX
        val authorities: List<String> = user.authorities.split(",").filter { it.isNotEmpty() }
        return authorities.map { role: String? -> SimpleGrantedAuthority(role) }
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

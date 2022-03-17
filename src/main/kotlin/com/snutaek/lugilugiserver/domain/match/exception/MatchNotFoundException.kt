package com.snutaek.lugilugiserver.domain.match.exception

import com.snutaek.lugilugiserver.global.common.exception.DataNotFoundException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class MatchNotFoundException (detail: String = "")
    : DataNotFoundException(ErrorType.MATCH_NOT_FOUND)
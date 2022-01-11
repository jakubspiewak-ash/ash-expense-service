package com.jakubspiewak.ash.expense.mongo

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TenantResolverInterceptor : HandlerInterceptor {
    companion object {
        private const val ASH_USER_ID_HEADER_NAME = "ash-user-id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userId = request.getHeader(ASH_USER_ID_HEADER_NAME)
        return userId?.let { setTenantContext(it) } ?: false
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) = DatabaseContextHolder.clear()

    private fun setTenantContext(userId: String): Boolean {
        DatabaseContextHolder.setCurrentUserId(userId)
        return true
    }
}

//package com.sudreeshya.sms.interceptor;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Enumeration;
//
///**
// * @author Manjit Shakya
// * @email manjit.shakya@f1soft.com
// */
//@Slf4j
//public class LoggerInterceptor implements HandlerInterceptor {
//
//    /**
//     * Executed before actual handler is executed
//     **/
//    @Override
//    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
//        log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + getParameters(request));
//        try {
//            log.debug("Handler: {}", new ObjectMapper().writeValueAsString(handler));
//        } catch (Exception ex) {
//        }
//        return true;
//    }
//
//    /**
//     * Executed before after handler is executed
//     **/
//    @Override
//    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
//        log.info("[postHandle][" + request + "]");
//        try {
//            log.debug("Handler: {}", new ObjectMapper().writeValueAsString(handler));
//        } catch (Exception ex) {
//        }
//    }
//
//    /**
//     * Executed after complete request is finished
//     **/
//    @Override
//    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
//        try {
//            log.debug("Handler: {}", new ObjectMapper().writeValueAsString(handler));
//        } catch (Exception e) {
//        }
//        if (ex != null)
//            ex.printStackTrace();
//        log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
//    }
//
//    private String getParameters(final HttpServletRequest request) {
//        final StringBuffer posted = new StringBuffer();
//        final Enumeration<?> e = request.getParameterNames();
//        if (e != null)
//            posted.append("?");
//        while (e != null && e.hasMoreElements()) {
//            if (posted.length() > 1)
//                posted.append("&");
//            final String curr = (String) e.nextElement();
//            posted.append(curr).append("=");
//            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
//                posted.append("*****");
//            } else {
//                posted.append(request.getParameter(curr));
//            }
//        }
//
//        final String ip = request.getHeader("X-FORWARDED-FOR");
//        final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
//        if (ipAddr != null || !ipAddr.isEmpty())
//            posted.append("&_psip=" + ipAddr);
//        return posted.toString();
//    }
//
//    private String getRemoteAddr(final HttpServletRequest request) {
//        final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
//        if (ipFromHeader != null && ipFromHeader.length() > 0) {
//            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
//            return ipFromHeader;
//        }
//        return request.getRemoteAddr();
//    }
//}
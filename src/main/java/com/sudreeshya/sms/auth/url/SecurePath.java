package com.sudreeshya.sms.auth.url;

import org.springframework.security.web.util.matcher.*;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class SecurePath {

    public static final RequestMatcher JWT_URLS = new AndRequestMatcher(
            new NegatedRequestMatcher(new AntPathRequestMatcher("/login"))
    );
}

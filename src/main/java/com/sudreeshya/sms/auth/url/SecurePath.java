package com.sudreeshya.sms.auth.url;

import org.springframework.security.web.util.matcher.*;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class SecurePath {
    public static final RequestMatcher NORMAL_AUTH_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/login")
    );

    public static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            NORMAL_AUTH_URLS
    );
    public static final RequestMatcher JWT_URLS = new AndRequestMatcher(
            new NegatedRequestMatcher(PUBLIC_URLS)
    );
}

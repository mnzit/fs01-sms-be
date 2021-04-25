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

    public static final RequestMatcher PUBLIC_UI_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/public/**"),
            new AntPathRequestMatcher("/error/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/v2/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/configuration/ui"),
            new AntPathRequestMatcher("/*.html"),
            new AntPathRequestMatcher("/**/*.html"),
            new AntPathRequestMatcher("/**/*.css"),
            new AntPathRequestMatcher("/**/*.js"),
            new AntPathRequestMatcher("/**/*.png"),
            new AntPathRequestMatcher("/**/*.ttf"),
            new AntPathRequestMatcher("/**/*.gif"),
            new AntPathRequestMatcher("/favicon.ico"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/monitoring"),
            new AntPathRequestMatcher("/banksmart"),
            new AntPathRequestMatcher("/v2/application/status"),
            new AntPathRequestMatcher("/v2/system/status"),
            new AntPathRequestMatcher("/clear/cache/**")
    );


    public static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            PUBLIC_UI_URLS,
            NORMAL_AUTH_URLS
    );
    public static final RequestMatcher JWT_URLS = new AndRequestMatcher(
            new NegatedRequestMatcher(PUBLIC_URLS)
    );
}

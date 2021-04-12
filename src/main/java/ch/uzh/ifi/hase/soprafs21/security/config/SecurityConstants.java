package ch.uzh.ifi.hase.soprafs21.security.config;

public class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final String REFRESH_SECRET = "REFRESH_SECRET_KEY";
    public static final long EXPIRATION_TIME = 900000; // 15 mins
    public static final long REFRESH_EXPIRATION_TIME = 1800000; // 30 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}

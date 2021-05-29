package ch.uzh.ifi.hase.soprafs21.security.config;

public interface SecurityConstants {

    String SECRET = "SECRET_KEY";
    String REFRESH_SECRET = "REFRESH_SECRET_KEY";
    long EXPIRATION_TIME = 900000; // 15 mins
    long REFRESH_EXPIRATION_TIME = 1800000; // 30 mins
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}

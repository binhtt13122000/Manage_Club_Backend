package binhtt.dev.manage.auth;

public class SecurityConstant {
    public static final String AUTH_LOGIN_URL = "/login";
    public static final String JWT_SECRET = "722edbb42afd6c261e6597e8f2a5045913ee2487899bf867b90b9a20c985e3c91ea801340797edc1fdd53245bafdfd31ed2adc891269d58de642285aed2390aa";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstant() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}

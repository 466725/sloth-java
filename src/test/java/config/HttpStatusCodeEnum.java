package config;

public enum HttpStatusCodeEnum {
    OK(200), BAD_REQUEST(400), NO_CONTENT(204), UNAUTHORIZED(401), NOT_FOUND(404);

    private final int code;

    private HttpStatusCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

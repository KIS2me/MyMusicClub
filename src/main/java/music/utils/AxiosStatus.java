package music.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AxiosStatus {
    NOT_LOGIN(401,"尚未登录");

    private int status;

    private String message;
}

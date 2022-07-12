package music.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import music.utils.AxiosStatus;

@Data
@AllArgsConstructor
public class MyLoginException extends RuntimeException {
    private AxiosStatus axiosStatus;
}

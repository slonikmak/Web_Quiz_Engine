package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason= "This user can't delete this quiz")
public class PermissionException extends RuntimeException {
}

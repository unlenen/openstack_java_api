package unlenen.cloud.openstack.be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Nebi
 */
@Getter
public class UnvalidCallException extends Exception {

    HttpStatus currentStatusCode;
    HttpStatus expectedStatusCode;

    public UnvalidCallException(HttpStatus currentStatusCode, HttpStatus expectedStatusCode, String message) {
        super(message);
        this.currentStatusCode = currentStatusCode;
        this.expectedStatusCode = expectedStatusCode;
    }

}

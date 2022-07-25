package unlenen.cloud.openstack.be.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 *
 * @author Nebi
 */
@Getter
public class UnvalidCallException extends Exception {

    HttpStatus currentStatusCode;
    HttpStatus expectedStatusCode;

    public UnvalidCallException(HttpStatus currentStatusCode, HttpStatus expectedStatusCode, String message) {
        super("Expected:"+expectedStatusCode+", current:"+currentStatusCode+", msg:"+message);
        this.currentStatusCode = currentStatusCode;
        this.expectedStatusCode = expectedStatusCode;
    }

}

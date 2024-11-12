package org.jpa.demo.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * The type Handled exception.
 */
public class HandledException extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus code;
    private String     message;

    /**
     * Instantiates a new Handled exception.
     *
     * @param message the message
     * @param code    the code
     */
    public HandledException(String message, HttpStatus code) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public HttpStatus getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HandledException other = (HandledException) obj;
        return code == other.code && Objects.equals(message, other.message);
    }

    @Override
    public String toString() {
        return "HandledException [code=" + code + ", message=" + message + "]";
    }

}

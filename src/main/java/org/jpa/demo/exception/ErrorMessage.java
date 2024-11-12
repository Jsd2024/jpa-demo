package org.jpa.demo.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Objects;

/**
 * The type Error message.
 */
public class ErrorMessage {

    private HttpStatus status;
    private int        statusCode;
    private Date       timestamp;
    private String     message;
    private String     description;

    /**
     * Instantiates a new Error message.
     *
     * @param status      the status
     * @param statusCode  the status code
     * @param timestamp   the timestamp
     * @param message     the message
     * @param description the description
     */
    public ErrorMessage(HttpStatus status, int statusCode, Date timestamp, String message, String description) {
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
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

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets status code.
     *
     * @param statusCode the status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, message, status, statusCode, timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ErrorMessage other = (ErrorMessage) obj;
        return Objects.equals(description, other.description) && Objects.equals(message, other.message)
            && status == other.status && statusCode == other.statusCode
            && Objects.equals(timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return "ErrorMessage [status=" + status + ", statusCode=" + statusCode + ", timestamp=" + timestamp
            + ", message=" + message + ", description=" + description + "]";
    }

}

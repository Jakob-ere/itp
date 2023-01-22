package core.rest;

/**
 * ConnectionResponse represents a HTTP response where its simplified to a
 * responseCode and message.
 *
 * @author Casper Andreassen
 */

public class ConnectionResponse {

    /**
     * ResponseCode is an integer representing a HTTP
     * status code (200, 404 etc).
     */
    private Integer responseCode;

    /**
     * responseMessage is a custom responseMessage that seeks to either describe
     * what went wrong during the HTTP request or contain the requested data.
     */
    private String responseMessage;

    /**
     * Contructor for creating a connectionresponse-class.
     * 
     * @param rCode    - responseCode
     * @param rMessage - responseMessage
     */
    public ConnectionResponse(final Integer rCode, final String rMessage) {
        this.responseCode = rCode;
        this.responseMessage = rMessage;
    }

    /**
     * @return Integer
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * @return String
     */
    public String getResponseMessage() {
        return responseMessage;
    }
}

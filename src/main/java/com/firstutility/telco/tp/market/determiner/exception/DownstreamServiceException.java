package com.firstutility.telco.tp.market.determiner.exception;

public class DownstreamServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DownstreamServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DownstreamServiceException(final String message) {
        super(message);
    }
}

package com.fissionworks.restalm.exceptions;

/**
 * Exception that represents the errors returned from the ALM rest API.
 *
 * @since 1.0.0
 *
 */
public final class AlmRestException extends RuntimeException {

	private static final long serialVersionUID = -948716533107751225L;

	private final String id;

	/**
	 * Creates a new AlmRestException with the given message. Id defaults to
	 * "AlmRestException"
	 *
	 * @param message
	 *            the message to set for the exception.
	 * @since 1.0.0
	 */
	public AlmRestException(final String message) {
		super(message);
		this.id = "AlmRestException";
	}

	/**
	 * Creates a new AlmRestException with the given message and id.
	 *
	 * @param message
	 *            the message to set for the exception.
	 * @param theId
	 *            the id to set for the exception.
	 * @since 1.0.0
	 */
	public AlmRestException(final String message, final String theId) {
		super(message);
		this.id = theId;
	}

	@Override
	public String getMessage() {
		return id + ": " + super.getMessage();
	}

}

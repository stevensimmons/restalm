package com.fissionworks.restalm.model.authentication;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * {@code Credentials} stores a username/password, and allows formatting a Basic
 * auth string using those credentials.
 *
 * @since 1.0.0
 */
public final class Credentials {

	private static final String ILLEGAL_USERNAME_CHARACTERS = "()@\\/[]':|<>+=;\",?*`%";

	private static final int MAX_PASSWORD_LENGTH = 20;

	private static final int MAX_USERNAME_LENGTH = 60;

	private final String password;

	private final String username;

	/**
	 * Creates a new credentials object with the specified username and
	 * password.
	 *
	 * @param theUsername
	 *            must be 1-20 alphanumeric characters.
	 * @param thePassword
	 *            must be 1-60 characters.
	 * @throws NullPointerException
	 *             Thrown if username and/or password is null.
	 * @throws IllegalArgumentException
	 *             Thrown if username is not 1-60 characters or contains any of
	 *             '"()@\/[]':|<>+=;",?*`%', or password is > 20 characters.
	 * @since 1.0.0
	 */
	public Credentials(final String theUsername, final String thePassword) {
		validateUsername(theUsername);
		validatePassword(thePassword);

		this.username = theUsername;
		this.password = thePassword;
	}

	/**
	 * Builds and encodes authorization request message in required format for
	 * ALM REST API.
	 *
	 * @return a string based on Base64 encoding the {@code #username} and
	 *         {@code #password} when concatenated as {@code username:password}
	 *         and prefixed with 'Basic ': example username = user and password
	 *         = pass encodes as 'Basic dXNlcjpwYXNz'
	 *
	 * @since 1.0.0
	 */
	public String getBasicAuthString() {
		return ("Basic " + Base64.encodeBase64String((this.username + ":" + this.password).getBytes())).replaceAll("\n",
				"");
	}

	@Override
	public String toString() {
		return super.toString() + " extended by: <Credentials> {\n    password=|" + password + "|,\n    username=|"
				+ username + "|\n}";
	}

	private void validatePassword(final String thePassword) {
		Validate.notNull(thePassword, "The password cannot be null");
		Validate.isTrue(thePassword.length() <= MAX_PASSWORD_LENGTH,
				"The password cannot be greater than 20 characters");
	}

	private void validateUsername(final String theUsername) {
		Validate.notBlank(theUsername, "The username cannot be null or blank");
		Validate.isTrue(
				(theUsername.length() <= MAX_USERNAME_LENGTH)
						&& StringUtils.containsNone(theUsername, ILLEGAL_USERNAME_CHARACTERS),
				"username '%s' invalid; Username must be 1- 60 characters and cannot contain any of: ()@\\/[]':|<>+=;\",?*`%",
				theUsername);
	}

}

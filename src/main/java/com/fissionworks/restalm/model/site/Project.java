package com.fissionworks.restalm.model.site;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * The Project class encapsulates the information necessary to uniquely identify
 * a project in ALM, namely the domain and project name.
 *
 * @since 1.0.0
 *
 */
public final class Project {

	private static final String ILLEGAL_DOMAIN_CHARACTERS = " =~`!@#$%^&*()+|{}[]:';\"<>?,./\\-"; // only
																									// "_"
																									// ok

	private static final String ILLEGAL_PROJECT_NAME_CHARACTERS = " =~`!@#$%^&*()+|{}[]:';\"<>?,./\\-"; // only
																										// "_"
																										// ok

	private static final int MAX_NAME_LENGTH = 30;

	private final String domain;

	private final String projectName;

	/**
	 * Creates a new Project object with the given domain and project name.
	 *
	 * @param theDomain
	 *            must be less than or equal to 30 characters, and cannot
	 *            contain any of: =~`!@#$%^& *()+| []:';\"<>?,./\\-}
	 * @param theProjectName
	 *            must be less than or equal to 30 characters, and cannot
	 *            contain any of =~`!@#$%^&*()+| []:';\"<>?,./\\-}
	 * @throws NullPointerException
	 *             thrown if domain or project name is null.
	 * @throws IllegalArgumentException
	 *             Thrown if domain or project name is greater than 30
	 *             characters, or if domain or project name contains illegal
	 *             characters.
	 * @since 1.0.0
	 */
	public Project(final String theDomain, final String theProjectName) {
		validateArguments(theDomain, theProjectName);
		this.domain = theDomain;
		this.projectName = theProjectName;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Project other = (Project) obj;

		if (!domain.equals(other.domain)) {
			return false;
		}
		if (!projectName.equals(other.projectName)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the full domain string.
	 *
	 * @return the domain string.
	 * @since 1.0.0
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * Returns the full project name.
	 *
	 * @return The project name.
	 * @since 1.0.0
	 */
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + domain.hashCode();
		result = (prime * result) + projectName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Project> {\n    domain=|").append(domain)
				.append("|,\n    projectName=|").append(projectName).append("|");
		return builder.toString();
	}

	private void validateArguments(final String theDomain, final String theProjectName) {
		Validate.notBlank(theDomain, "The domain cannot be null or blank");
		Validate.notBlank(theProjectName, "The project name cannot be null or blank");
		Validate.isTrue(theDomain.length() <= MAX_NAME_LENGTH, "The domain must be 30 characters or less");
		Validate.isTrue(theProjectName.length() <= MAX_NAME_LENGTH, "The project name must be 30 characters or less");
		Validate.isTrue(StringUtils.containsNone(theProjectName, ILLEGAL_PROJECT_NAME_CHARACTERS),
				"project name cannot contain: " + Project.ILLEGAL_PROJECT_NAME_CHARACTERS);
		Validate.isTrue(StringUtils.containsNone(theDomain, ILLEGAL_DOMAIN_CHARACTERS),
				"domain cannot contain: " + Project.ILLEGAL_DOMAIN_CHARACTERS);
	}
}

package com.fissionworks.restalm.model.site;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Represents a domain in a deployed ALM instance. Includes the projects that
 * exist within that domain.
 *
 * @since 1.0.0
 *
 */
public final class Domain {

	private static final String ILLEGAL_DOMAIN_CHARACTERS = " =~`!@#$%^&*()+|{}[]:';\"<>?,./\\-"; // only
																									// "_"
																									// ok

	private static final int MAX_NAME_LENGTH = 30;

	private final String name;

	private final Set<Project> projects = new HashSet<>();

	/**
	 * Creates a Domain object with the given name.
	 *
	 * @param theName
	 *            The name must be <= 30 characters, and cannot contain any of:
	 *            " =~`!@#$%^&*()+|{}[]:';\"<>?,./\\-"
	 * @throws NullPointerException
	 *             Thrown if the name is null.
	 * @throws IllegalArgumentException
	 *             Thrown if the name is > 30 characters, or contains any of the
	 *             illegal characters.
	 * @since 1.0.0
	 */
	public Domain(final String theName) {
		validateDomainName(theName);
		this.name = theName;
	}

	/**
	 * Add a project to this domain.
	 *
	 * @param project
	 *            The {@link Project} to add to the domain;
	 *            {@link Project#getDomain()} must be the same as the domains
	 *            name.
	 * @throws IllegalArgumentException
	 *             Thrown if the {@link Project#getDomain()} is not the same as
	 *             the domains name.
	 * @since 1.0.0
	 */
	public void addProject(final Project project) {
		if (project.getDomain().equals(name)) {
			this.projects.add(project);
		} else {
			throw new IllegalArgumentException("Can only add projects that are in the same domain");
		}
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
		final Domain other = (Domain) obj;
		if (!name.equals(other.name)) {
			return false;
		}
		if (!projects.equals(other.projects)) {
			return false;
		}
		return true;
	}

	/**
	 * Get the name of this domain.
	 *
	 * @return the domain's name.
	 * @since 1.0.0
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the set of projects that exist in this domain.
	 *
	 * @return The set of projects for this domain.
	 * @since 1.0.0
	 */
	public Set<Project> getProjects() {
		return new HashSet<>(projects);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + name.hashCode();
		result = (prime * result) + projects.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Domain> {\n    name=|").append(name)
				.append("|,\n    projects=|").append(projects).append("|");
		return builder.toString();
	}

	private void validateDomainName(final String theName) {
		Validate.notBlank(theName, "The Domain name cannot be null or blank");
		Validate.isTrue(theName.length() <= MAX_NAME_LENGTH, "The domain name must be 30 characters or less");
		Validate.isTrue(StringUtils.containsNone(theName, ILLEGAL_DOMAIN_CHARACTERS),
				"The domain name cannot contain: " + Domain.ILLEGAL_DOMAIN_CHARACTERS);
	}

}

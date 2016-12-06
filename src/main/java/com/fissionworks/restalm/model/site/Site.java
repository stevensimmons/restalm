package com.fissionworks.restalm.model.site;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * Contains information about a given deployed ALM instance, such as the domains
 * and projects within that ALM instance.
 *
 * @since 1.0.0
 */
public final class Site implements Iterable<Domain> {

	private final Set<Domain> domains = new HashSet<>();

	/**
	 * Add a new {@link Domain} to the site.
	 *
	 * @param domain
	 *            the {@link Domain} to add to the site; must not be null.
	 * @throws NullPointerException
	 *             Thrown if the {@link Domain} is null.
	 * @since 1.0.0
	 *
	 */
	public void addDomain(final Domain domain) {
		Validate.notNull(domain);
		this.domains.add(domain);
	}

	/**
	 * Does this site contain this {@link Project}?
	 *
	 * @param project
	 *            The {@link Project} to check the site for the existence of.
	 * @return True if the site contains the {@link Project}, false otherwise;
	 * @since 1.0.0
	 */
	public boolean containsProject(final Project project) {
		for (final Domain domain : domains) {
			if (domain.getProjects().contains(project)) {
				return true;
			}
		}
		return false;
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
		final Site other = (Site) obj;
		if (!domains.equals(other.domains)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the set of domain names available at this site.
	 *
	 * @return The set of all available domain names.
	 * @since 1.0.0
	 */
	public Set<String> getDomainNames() {
		final Set<String> domainNames = new HashSet<>();
		for (final Domain domain : domains) {
			domainNames.add(domain.getName());
		}
		return domainNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + domains.hashCode();
		return result;
	}

	@Override
	public Iterator<Domain> iterator() {
		return domains.iterator();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Site> {\n    domains=|").append(domains).append("|");
		return builder.toString();
	}

}

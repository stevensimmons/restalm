package com.fissionworks.restalm.model.entity.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * POJO modeled after the common entity xml returned from the ALM rest API for a
 * collection of resource entities (test, requirements, runs, etc). Used for
 * marshalling/unmarshalling purposes only.
 *
 * @since 1.0.0
 *
 */
public final class GenericEntityCollection implements Iterable<GenericEntity> {

	private final Set<GenericEntity> entities = new HashSet<>();

	private final int totalResults;

	/**
	 * Constructor to create GenericEntityCollection with the given total
	 * results size.
	 *
	 * @param totalResultSize
	 *            The total results count.
	 * @since 1.0.0
	 */
	public GenericEntityCollection(final int totalResultSize, final Set<GenericEntity> theEntities) {
		Validate.isTrue(totalResultSize >= 0, "totalResultSize must be greater than or equal to 0");
		this.totalResults = totalResultSize;
		entities.addAll(theEntities);
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
		final GenericEntityCollection other = (GenericEntityCollection) obj;
		if (!entities.equals(other.entities)) {
			return false;
		}
		if (totalResults != other.totalResults) {
			return false;
		}
		return true;
	}

	/**
	 * Get the total results size for the filter that returned this entity
	 * collection. Used for paging purposes.
	 *
	 * @return The total results size.
	 * @since 1.0.0
	 */
	public int getTotalResults() {
		return totalResults;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + entities.hashCode();
		result = (prime * result) + totalResults;
		return result;
	}

	@Override
	public Iterator<GenericEntity> iterator() {
		return entities.iterator();
	}

	/**
	 * The number of entities contained in this collection.
	 *
	 * @return The number of entities in the collection.
	 * @since 1.0.0
	 */
	public int size() {
		return this.entities.size();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <EntityCollection> {\n    entities=|").append(entities)
				.append("|,\n    totalResults=|").append(totalResults).append("|");
		return builder.toString();
	}

}

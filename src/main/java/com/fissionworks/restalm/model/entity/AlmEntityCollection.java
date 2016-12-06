package com.fissionworks.restalm.model.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Collection that contains a group of resource entities (tests, requirements,
 * etc.) from ALM.
 *
 * @param <T>
 *            The type of {@link AlmEntity} the collection contains.
 * @since 1.0.0
 */
public final class AlmEntityCollection<T extends AlmEntity> implements Iterable<T> {

	private final Set<T> entities = new HashSet<>();

	private final int totalResults;

	/**
	 * Constructor that includes the total results size.
	 *
	 * @param totalResultSize
	 *            The total result size; used for paging.
	 * @since 1.0.0
	 */
	public AlmEntityCollection(final int totalResultSize) {
		this.totalResults = totalResultSize;
	}

	/**
	 * Add an {@link AlmEntity} to the collection.
	 *
	 * @param entity
	 *            the {@link AlmEntity} to add.
	 * @since 1.0.0
	 */
	public void addEntity(final T entity) {
		entities.add(entity);
	}

	/**
	 * Does the collection contain an entity equal to this entity?
	 *
	 * @param entity
	 *            The entity to check for the presence of.
	 * @return True if an equal entity is present, false otherwise.
	 * @since 1.0.0
	 */
	public boolean contains(final T entity) {
		return this.entities.contains(entity);
	}

	/**
	 * Get the total results size that this collection is part of (for paging
	 * purposes).
	 *
	 * @return The total results size.
	 */
	public int getTotalResults() {
		return totalResults;
	}

	@Override
	public Iterator<T> iterator() {
		return entities.iterator();
	}

	/**
	 * Get the number of entities in this collection.
	 *
	 * @return The number of entities.
	 * @since 1.0.0
	 */
	public int size() {
		return entities.size();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <AlmEntityCollection> {\n    totalResults=|")
				.append(totalResults).append("|,\n    entities=|").append(entities).append("|");
		return builder.toString();
	}

}

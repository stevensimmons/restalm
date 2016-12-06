package com.fissionworks.restalm.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.fissionworks.restalm.constants.field.FieldName;

/**
 * Builder class for setting the values to be used in the Restful calls to ALM
 * ("fields", "query", "page-size", etc.)
 *
 * @since 1.0.0
 *
 */
public final class RestParameters {

	private static final int DEFAULT_PAGE_SIZE = 200;

	private final Set<String> fields = new HashSet<>();

	private int pageSize;

	private final List<String> queryStatements = new ArrayList<>();

	private int startIndex = 1;

	/**
	 * Adds a cross query statement for the given field name (i.e
	 * "test.name[value]"). The query value must be a valid format accepted by
	 * ALM (for example, values with spaces must be enclosed with single
	 * quotes).
	 *
	 * @param fieldName
	 *            The name of the field to filter on.
	 * @param queryValue
	 *            The value to query with.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters crossQueryFilter(final FieldName fieldName, final String queryValue) {
		queryStatements.add(String.format("%s[%s]", fieldName.getQualifiedName(), queryValue));
		return this;
	}

	/**
	 * Set the field names to be returned from the rest query.
	 *
	 * @param fieldNames
	 *            the field name(s) to be included in the returned entities.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters fields(final FieldName... fieldNames) {
		Validate.notNull(fieldNames, "fieldNames cannot be null");
		Validate.isTrue(fieldNames.length > 0, "fieldNames must contain at least one field name");
		for (final FieldName fieldName : fieldNames) {
			fields.add(fieldName.getName());
		}
		return this;
	}

	/**
	 * Gets the list of fields to be included in the returned entities.
	 *
	 * @return a comma seperated list of fields, or empty string if no field
	 *         filter set.
	 * @since 1.0.0
	 */
	public String getFields() {
		return fields.isEmpty() ? "" : StringUtils.join(fields, ",");
	}

	/**
	 * Gets the currently set page size value.
	 *
	 * @return The currently set page size.
	 * @since 1.0.0
	 */
	public int getPageSize() {
		return pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	/**
	 * Gets a formatted query statement string based on all queries previously
	 * set.
	 *
	 * @return A correctly formatted query statement string for use in the ALM
	 *         rest call.
	 * @since 1.0.0
	 */
	public String getQueryStatements() {
		return String.format("{%s}", StringUtils.removeEnd(StringUtils.join(queryStatements.toArray(), ";"), ";"));
	}

	/**
	 * Gets the currently set start index value.
	 *
	 * @return The currently set start index.
	 * @since 1.0.0
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * Sets the desired page size to be returned from ALM.
	 *
	 * @param thePageSize
	 *            The desired page size.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters pageSize(final int thePageSize) {
		Validate.isTrue(thePageSize > 0, "the page size must be greater than zero");
		pageSize = thePageSize;
		return this;
	}

	/**
	 * Adds a query statement for the given field name (i.e "name[value]"). The
	 * query value must be a valid format accepted by ALM (for example, values
	 * with spaces must be enclosed with single quotes).
	 *
	 * @param fieldName
	 *            The name of the field to filter on.
	 * @param queryValue
	 *            The value to query with.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters queryFilter(final FieldName fieldName, final String queryValue) {
		queryStatements.add(String.format("%s[%s]", fieldName.getName(), queryValue));
		return this;
	}

	/**
	 * adds the given related entity fields to the list of fields to be
	 * retrieved. Fields must be from entities that are valid related entities
	 * to the entity being retrieved.
	 *
	 * @param fieldNames
	 *            The names of the fields of the related entity to be retrieved.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters relatedFields(final FieldName... fieldNames) {
		Validate.notNull(fieldNames, "fieldNames cannot be null");
		Validate.isTrue(fieldNames.length > 0, "fieldNames must contain at least one field name");
		for (final FieldName fieldName : fieldNames) {
			fields.add(fieldName.getQualifiedName());
		}
		return this;
	}

	/**
	 * Sets the desired start index that will be returned from ALM for data
	 * paging.
	 *
	 * @param theStartIndex
	 *            The start index desired for the result set returned from ALM.
	 * @return returns the current instance to conform to the builder pattern.
	 * @since 1.0.0
	 */
	public RestParameters startIndex(final int theStartIndex) {
		Validate.isTrue(theStartIndex > 0, "the start index must be greater than zero");
		this.startIndex = theStartIndex;
		return this;
	}

}

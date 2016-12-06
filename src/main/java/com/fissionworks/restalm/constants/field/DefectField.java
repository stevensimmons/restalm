package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Defect objects in ALM; The ENUM is equal to the
 * default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum DefectField implements FieldName {
	/**
	 * The Assigned To field; field name is {@code owner}.
	 *
	 * @since 1.0.0
	 */
	ASSIGNED_TO("owner", "defect"),
	/**
	 * The Closing Date field; field name is {@code closing-date}.
	 *
	 * @since 1.0.0
	 */
	CLOSING_DATE("closing-date", "defect"),
	/**
	 * The Comments field; field name is {@code comments}.
	 *
	 * @since 1.0.0
	 */
	COMMENTS("dev-comments", "defect"),
	/**
	 * The Comments field; field name is {@code comments}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "defect"),
	/**
	 * The Detected By field; field name is {@code detected-by}.
	 *
	 * @since 1.0.0
	 */
	DETECTED_BY("detected-by", "defect"),
	/**
	 * The Detected In Cycle ID field; field name is {@code detected-in-rcyc}.
	 *
	 * @since 1.0.0
	 */
	DETECTED_IN_CYCLE_ID("detected-in-rcyc", "defect"),
	/**
	 * The Detected In Release ID field; field name is {@code detected-in-rel}.
	 *
	 * @since 1.0.0
	 */
	DETECTED_IN_RELEASE_ID("detected-in-rel", "defect"),
	/**
	 * The Detected On Date field; field name is {@code creation-time}.
	 *
	 * @since 1.0.0
	 */
	DETECTED_ON_DATE("creation-time", "defect"),
	/**
	 * The ID field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "defect"),
	/**
	 * The Modified field; field name is {@code last-modified}.
	 *
	 * @since 1.0.0
	 */
	MODIFIED("last-modified", "defect"),
	/**
	 * The Severity field; field name is {@code severity}.
	 *
	 * @since 1.0.0
	 */
	SEVERITY("severity", "defect"),
	/**
	 * The Status field; field name is {@code status}.
	 *
	 * @since 1.0.0
	 */
	STATUS("status", "defect"),
	/**
	 * The Summary field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	SUMMARY("name", "defect");

	private final String alias;

	private final String name;

	private DefectField(final String theName, final String theAlias) {
		this.name = theName;
		this.alias = theAlias;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQualifiedName() {
		return String.format("%s.%s", alias, name);
	}

}

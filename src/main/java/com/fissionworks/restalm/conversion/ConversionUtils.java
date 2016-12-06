package com.fissionworks.restalm.conversion;

import org.apache.commons.lang3.StringUtils;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

/**
 * Utility methods that may be useful when converting/marshalling objects.
 *
 * @since 1.0.0
 *
 */
public final class ConversionUtils {

	private ConversionUtils() {
		throw new UnsupportedOperationException("ConversionUtils should not be instantiated");
	}

	/**
	 * Removes all HTML tags/markup present in the source string. Strings with
	 * no HTML will be returned unchanged.
	 *
	 * @param sourceString
	 *            The string to strip HTML from.
	 * @return A string with all HTML stripped from it.
	 * @since 1.0.0
	 */
	public static String removeHtml(final String sourceString) {
		if (StringUtils.isNotBlank(sourceString)) {
			final TextExtractor extractor = new TextExtractor(
					new Segment(new Source(sourceString), 0, sourceString.length()));
			return extractor.toString();
		}
		return sourceString;
	}

}

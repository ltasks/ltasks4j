/*
 *  Copyright 2011 LTasks
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.ltasks.htmlfilteroptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Represents a simplified XPath query used to select elements in a HTML.
 * </p>
 * <p>
 * Examples: <br>
 * <code>//table</code> matches any <code>table</code> element; <br>
 * <code>//sup[@title]</code> matches any <code>sup</code> element that has a
 * <code>title</code> attribute; <br>
 * <code>//span[@title='editsection']</code> matches any <code>span</code>
 * element that has a <code>title</code> attribute which value is
 * <code>editsection</code>.
 * </p>
 * 
 * <p>
 * Users can create a {@link SimpleXPath} using its constructor.
 * </p>
 * 
 * The utility method {@link #parse(String)} parses a {@link String} in the
 * LTasks XPath simplified query to a list of {@link SimpleXPath}. </p> Example
 * string: <code>//table|//sup[@title]|//span[@class='editsection']</code> <br>
 * 
 * <p>
 * The {@link #toString(List)} creates a string in this format.
 * </p>
 * 
 */
public class SimpleXPath {

	/** pattern that parses a simple xpath query */
	private static final Pattern REGEX = Pattern
			.compile("//([^\\[\\|]+)(?:\\[@([^\\[\\|\\=\\']+)(?:=[\\'\\\"]([^']+)['\"])?\\])?\\|?");

	private final String elementName;
	private final String attributeName;
	private final String attributeValue;

	/**
	 * Creates one simple xpath query that matches a HTMl element, with an
	 * optional attribute name and value
	 * 
	 * @param elementName
	 *            the element to match. It can't be null.
	 * @param attributeName
	 *            an optional attribute name. It can be null only if
	 *            attributeValue is not null.
	 * @param attributeValue
	 */
	public SimpleXPath(String elementName, String attributeName,
			String attributeValue) throws IllegalArgumentException {
		super();
		if (elementName == null) {
			throw new IllegalArgumentException(
					"the elementName argument can not be null.");
		}
		this.elementName = elementName;
		this.attributeName = attributeName;
		if (attributeName == null && attributeValue != null) {
			throw new IllegalArgumentException(
					"the attributeValue argument can not be set if elementName is null.");
		}
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("//");
		if (elementName != null) {
			sb.append(elementName);
		} else {
			sb.append("*");
		}

		if (attributeName != null) {
			sb.append("[@").append(attributeName);
		}

		if (attributeValue != null) {
			sb.append("='").append(attributeValue).append("'");
		}

		if (attributeName != null) {
			sb.append("]");
		}
		return sb.toString();
	}

	public String getElementName() {
		return elementName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	/**
	 * a {@link String} in the LTasks XPath simplified query to a list of
	 * {@link SimpleXPath}. </p> Example string:
	 * <code>//table|//sup[@title]|//span[@class='editsection']</code>
	 * 
	 * @param value
	 *            the LTasks XPath simplified query
	 * @return a list of {@link SimpleXPath}
	 */
	public static List<SimpleXPath> parse(String value) {
		if (value == null) {
			return Collections.emptyList();
		}
		List<SimpleXPath> result = new ArrayList<SimpleXPath>();
		Matcher m = REGEX.matcher(value);
		while (m.find()) {
			result.add(new SimpleXPath(m.group(1), m.group(2), m.group(3)));
		}

		return Collections.unmodifiableList(result);
	}

	/**
	 * Outputs a string in the LTasks XPath simplified query
	 * 
	 * @param list
	 *            the list
	 * @return the query string
	 */
	public static String toString(List<SimpleXPath> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size() - 1; i++) {
			sb.append(list.get(i)).append("|");
		}
		sb.append(list.get(list.size() - 1));
		return sb.toString();
	}

}

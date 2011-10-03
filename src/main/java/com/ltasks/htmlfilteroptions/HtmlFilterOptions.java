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

import java.util.List;

/**
 * <p>
 * Represents the HTML filter options of LTasks. The options are:
 * </p>
 * <ul>
 * <li><b>include</b>: a list of HTML elements represented by
 * {@link SimpleXPath} that should be selected by the filter;</li>
 * <li><b>exclude</b>: a list of HTML elements represented by
 * {@link SimpleXPath} that should not be selected by the filter;</li>
 * <li><b>filter</b>: the {@link HtmlFilter} that will extract the text content
 * of a HTML.</li>
 * </ul>
 * <p>
 * For example, to extract content from Wikipedia article we should, after
 * examining the structure of the HTML:
 * <ul>
 * <li>We should include only contents inside <code>//div[@id='content']</code>,
 * so we add it to include;</li>
 * <li>We can exclude some elements, for example the <code>[edit]</code> pattern
 * and the <code>[1]</code> pattern used to refer to external links. So we set
 * exclude with <code>//span[@class='editsection']|//sup</code></li>
 * <li>Finally Wikipedia articles looks like an article, so we we set the filter
 * with {@link HtmlFilter#article}.</li>
 * </ul>
 * </p>
 */
public class HtmlFilterOptions {

	private List<SimpleXPath> include;
	private List<SimpleXPath> exclude;
	private HtmlFilter filter;

	public List<SimpleXPath> getInclude() {
		return include;
	}

	public void setInclude(List<SimpleXPath> include) {
		this.include = include;
	}

	public List<SimpleXPath> getExclude() {
		return exclude;
	}

	public void setExclude(List<SimpleXPath> exclude) {
		this.exclude = exclude;
	}

	public HtmlFilter getFilter() {
		return filter;
	}

	public void setFilter(HtmlFilter filter) {
		this.filter = filter;
	}

}

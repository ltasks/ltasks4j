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

/**
 * Enumeration that represents the built-in HTML filters available in LTask.
 */
public enum HtmlFilter {

	/**
	 * The {@link #article} filter works better with pages that looks like an
	 * article.
	 */
	article,

	/** The {@link #standard} filter is OK for most of the contents. */
	standard,

	/** The {@link #none} filter will keep all text elements. */
	none,

}

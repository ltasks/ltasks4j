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

package com.ltasks;

/**
 * The named entities types supported by LTasks. For more info check the <a href="http://ltasks.com">documentation</a>.
 *
 */
public enum NerType {

	PERSON("person"), 
	ORGANIZATION("organization"), 
	GROUP("group"), 
	PLACE("place"), 
	EVENT("event"), 
	ARTPROD("artprod"), 
	ABSTRACT("abstract"), 
	THING("thing"), 
	TIME("time"), 
	NUMERIC("numeric");
	
	private final String value;

	NerType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static NerType fromValue(String v) {
		for (NerType c : NerType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}

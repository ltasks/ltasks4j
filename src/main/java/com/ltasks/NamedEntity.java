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
 * Represents a entity found in users text
 * 
 */
public class NamedEntity {

	private String mText;
	private NerType mType;
	private int mBegin;
	private int mEnd;

	/**
	 * Creates a new object representing the entities from the text
	 * 
	 * @param aText
	 *            the entity text
	 * @param aType
	 *            the entity type
	 * @param aBegin
	 *            the begin of the entity in the normalized text
	 * @param aEnd
	 *            the end of the entity in the normalized text
	 */
	public NamedEntity(String aText, NerType aType, int aBegin, int aEnd) {
		super();
		this.mText = aText;
		this.mType = aType;
		this.mBegin = aBegin;
		this.mEnd = aEnd;
	}

	/**
	 * Gets the entity text
	 * 
	 * @return the entity text
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Gets the entity type
	 * 
	 * @return the entity type
	 */
	public NerType getType() {
		return mType;
	}

	/**
	 * Gets the begin of the entity in the normalized text
	 * 
	 * @return the begin of the entity in the normalized text
	 */
	public int getBegin() {
		return mBegin;
	}

	/**
	 * Gets the end of the entity in the normalized text
	 * 
	 * @return the end of the entity in the normalized text
	 */
	public int getEnd() {
		return mEnd;
	}

}

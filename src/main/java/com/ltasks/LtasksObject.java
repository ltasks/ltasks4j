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

import java.util.List;

/**
 * Holds a call result.
 */
public class LtasksObject {

	/** The source text */
	private final String mSourceText;

	/** Message from host. It can give more information about errors */
	private final String mMessage;

	/** If true the result was OK */
	private final boolean mIsOk;

	/** The found entities */
	private final List<NamedEntity> mNamedEntities;

	/**
	 * Create a new call result
	 * 
	 * @param aText
	 *            The source text
	 * @param aMessage
	 *            Message from host. It can give more information about errors
	 * @param aProcessedOk
	 *            If true the result was OK
	 * @param aNamedEntities
	 *            The found entities
	 */
	public LtasksObject(String aText, String aMessage, boolean aProcessedOk,
			List<NamedEntity> aNamedEntities) {
		super();
		mSourceText = aText;
		mNamedEntities = aNamedEntities;
		mMessage = aMessage;
		mIsOk = aProcessedOk;
	}

	/**
	 * Gets the source text
	 * 
	 * @return the source text or null
	 */
	public String getText() {
		return mSourceText;
	}

	/**
	 * Gets the message from host. It can give more information about errors.
	 * 
	 * @return the message or null
	 */
	public String getMessage() {
		return mMessage;
	}

	/**
	 * If true the communication to the host was ok
	 * 
	 * @return true if the communication to the host was ok
	 */
	public boolean isProcessedOk() {
		return mIsOk;
	}

	/**
	 * Gets the entities found in the text
	 * 
	 * @return the entities found in the text
	 */
	public List<NamedEntity> getNamedEntities() {
		return mNamedEntities;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Processed ok: ").append(mIsOk).append("\n");
		if (mMessage != null) {
			sb.append("Message from server: ").append(mMessage).append("\n");
		}
		if (mSourceText != null) {
			sb.append("Source text: ").append(mSourceText).append("\n");
		}
		for (NamedEntity entities : mNamedEntities) {
			sb.append("  named entity - type: ")
					.append(entities.getType().value()).append(" begin: ")
					.append(entities.getBegin()).append(" end: ")
					.append(entities.getEnd()).append("  text: ")
					.append(entities.getText()).append("\n");
		}
		return sb.toString();
	}

}

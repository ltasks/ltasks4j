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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses the XML result. It implements SAX.
 *
 */
public class ResultParser extends DefaultHandler {

	private boolean mIsTextElement = false;
	private StringBuilder mSourceTextBuilder;
	private List<NamedEntity> mNamedEntities = new ArrayList<NamedEntity>();
	private StringBuilder mMessageBuilder;
	private boolean mIsMessageElement;

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (mIsTextElement) {
			mSourceTextBuilder.append(ch, start, length);
		} else if (mIsMessageElement) {
			mMessageBuilder.append(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		if ("result".equals(qName) || "namedEntities".equals(qName)) {
			// skip parent
		} else if (qName.equals("text")) {
			this.mSourceTextBuilder = new StringBuilder();
			this.mIsTextElement = true;
		} else if (qName.equals("namedEntity")) {
			addNamedEntity(attrs);
		} else if (qName.equals("message")) {
			this.mMessageBuilder = new StringBuilder();
			this.mIsMessageElement = true;
		} else {
			throw new IllegalArgumentException("Element '" + qName
					+ "' is not allowed here");
		}
	}

	private void addNamedEntity(Attributes attrs) {
		String text = null;
		NerType type = null;
		int begin = 0, end = 0;
		for (int i = 0; i < attrs.getLength(); i++) {
			if ("text".equals(attrs.getQName(i))) {
				text = attrs.getValue(i);
			} else if ("type".equals(attrs.getQName(i))) {
				type = NerType.fromValue(attrs.getValue(i));
			} else if ("begin".equals(attrs.getQName(i))) {
				begin = Integer.parseInt(attrs.getValue(i));
			} else if ("end".equals(attrs.getQName(i))) {
				end = Integer.parseInt(attrs.getValue(i));
			}
		}
		mNamedEntities.add(new NamedEntity(text, type, begin, end));
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("text")) {
			this.mIsTextElement = false;
		} else if (qName.equals("message")) {
			this.mIsMessageElement = false;
		}
	}

	private LtasksObject getObject(boolean aIsOK) {
		String text = null;
		String message = null;

		if (mSourceTextBuilder != null) {
			text = mSourceTextBuilder.toString();
		}

		if (mMessageBuilder != null) {
			message = mMessageBuilder.toString();
		}
		return new LtasksObject(text, message, aIsOK,
				Collections.unmodifiableList(mNamedEntities));
	}

	public static LtasksObject parse(InputStream aXML, boolean aIsOk)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ResultParser handler = new ResultParser();
		parser.parse(aXML, handler);
		return handler.getObject(aIsOk);
	}

}

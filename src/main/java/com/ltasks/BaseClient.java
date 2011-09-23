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
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.xml.sax.SAXException;

/**
 * BaseClient implements common methods that should be used by different tasks.
 */
public abstract class BaseClient {

	/** The user API KEY */
	private final String mApiKey;

	/** The http client */
	private HttpClient client;

	private boolean mIsIncludeSource;

	/**
	 * Creates a new BaseClient.
	 * 
	 * @param aApiKey
	 *            the user api key
	 * @throws IllegalArgumentException
	 *             the api key does not conform with the standard
	 *             representation.
	 */
	public BaseClient(String aApiKey) throws IllegalArgumentException {
		this(aApiKey, true);
	}

	/**
	 * Creates a new BaseClient.
	 * 
	 * @param aApiKey
	 *            the user api key
	 * @param aIsIncludeSource
	 *            if to include the source text
	 * @throws IllegalArgumentException
	 *             the api key does not conform with the standard
	 *             representation.
	 */
	public BaseClient(String aApiKey, boolean aIsIncludeSource)
			throws IllegalArgumentException {
		validateApiKey(aApiKey);
		mApiKey = aApiKey;
		client = new HttpClient();
		client.getParams().setParameter("http.useragent", "ltasks4j");
		mIsIncludeSource = aIsIncludeSource;
	}

	/**
	 * This method validates the api key by trying to parse it using
	 * {@link UUID#fromString(String)}. If it fails it throws an exception.
	 * 
	 * @param aApiKey
	 *            the user api key
	 * @throws IllegalArgumentException
	 *             the api key does not conform with the standard
	 *             representation.
	 */
	private void validateApiKey(String aApiKey) throws IllegalArgumentException {
		UUID.fromString(aApiKey);
	}

	/**
	 * The resource URL.
	 */
	protected abstract String getResourceUrl();

	/**
	 * Post the data to the remote resource.
	 * 
	 * @param data
	 *            the data to post
	 * @return the returned object
	 * @throws HttpException
	 *             Got a protocol error.
	 * @throws IOException
	 *             Failed to communicate or to read the result.
	 * @throws IllegalArgumentException
	 *             The data received from server was invalid.
	 */
	protected LtasksObject post(NameValuePair data) throws HttpException,
			IOException, IllegalArgumentException {
		PostMethod method = new PostMethod(getResourceUrl());

		// Set input content type
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		// Set response/output format
		method.setRequestHeader("Accept", "application/xml");

		NameValuePair[] body = new NameValuePair[] {
				data,
				new NameValuePair("apikey", mApiKey),
				new NameValuePair("includeSourceText",
						Boolean.toString(mIsIncludeSource)) };

		method.setRequestBody(body);

		boolean processedOk = false;
		int code = client.executeMethod(method);
		if (code == 200) {
			processedOk = true;
		}
		InputStream is = method.getResponseBodyAsStream();
		LtasksObject result = null;
		if (is != null) {
			try {
				result = ResultParser.parse(method.getResponseBodyAsStream(),
						processedOk);
			} catch (ParserConfigurationException e) {
				throw new IllegalArgumentException(
						"Got an invalid response from server.", e);
			} catch (SAXException e) {
				throw new IllegalArgumentException(
						"Got an invalid response from server.", e);
			}
		} else {
			result = new LtasksObject(null, "Failed to process request. Code: "
					+ code, false, null);
		}
		return result;
	}
}

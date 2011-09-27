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
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.xml.sax.SAXException;

public class LtasksNameFinderClient extends BaseClient {

	/** the NER resource URL */
	private static final String RESOURCE = "http://api.ltasks.com/app/v0b/ner";

	/**
	 * Creates a new Name Finder client. By default will not include text
	 * source, and communication not gzipped.
	 * 
	 * @param aApiKey
	 *            the user api key
	 * @throws IllegalArgumentException
	 *             the api key does not conform with the standard
	 *             representation.
	 */
	public LtasksNameFinderClient(String aApiKey)
			throws IllegalArgumentException {
		super(aApiKey);
	}

	/**
	 * Creates a new Name Finder client.
	 * 
	 * @param aApiKey
	 *            the user api key
	 * @param aIsIncludeSourceText
	 *            if true will include the normalized text to the response.
	 *            Default is true.
	 * @param aIsGZipContentEncoding
	 *            if true the request and response will be gzipped
	 * @throws IllegalArgumentException
	 *             the api key does not conform with the standard
	 *             representation.
	 */
	public LtasksNameFinderClient(String aApiKey, boolean aIsIncludeSourceText,
			boolean aIsGZipContentEncoding) throws IllegalArgumentException {
		super(aApiKey, aIsIncludeSourceText, aIsGZipContentEncoding);
	}

	@Override
	protected String getResourceUrl() {
		return RESOURCE;
	}

	/**
	 * Annotate a normalized text from a URL
	 * 
	 * @param aUrl
	 *            the URL
	 * @return the annotation object
	 * @throws HttpException
	 *             Got a protocol error.
	 * @throws IOException
	 *             Failed to communicate or to read the result.
	 * @throws IllegalArgumentException
	 *             The data received from server was invalid.
	 */
	public LtasksObject processUrl(URL aUrl) throws HttpException, IOException,
			IllegalArgumentException {
		return post(new NameValuePair("url", aUrl.toString()));
	}

	/**
	 * Annotate a normalized text from a HTML
	 * 
	 * @param aHtml
	 *            the HTML
	 * @return the annotation object
	 * @throws HttpException
	 *             Got a protocol error.
	 * @throws IOException
	 *             Failed to communicate or to read the result.
	 * @throws IllegalArgumentException
	 *             The data received from server was invalid.
	 */
	public LtasksObject processHtml(String aHtml) throws HttpException,
			IOException {
		return post(new NameValuePair("html", aHtml));
	}

	/**
	 * Annotate a text
	 * 
	 * @param aText
	 *            the text to annotate
	 * @return the annotation object
	 * @throws HttpException
	 *             Got a protocol error.
	 * @throws IOException
	 *             Failed to communicate or to read the result.
	 * @throws IllegalArgumentException
	 *             The data received from server was invalid.
	 */
	public LtasksObject processText(String aText) throws HttpException,
			IOException {
		return post(new NameValuePair("text", aText));
	}

	public static void main(String[] args) throws HttpException, IOException,
			ParserConfigurationException, SAXException, InterruptedException {

		while(true) {
		// just a sample usage...

		System.out.println("Initializing client...");
		LtasksNameFinderClient client = new LtasksNameFinderClient(
				"b2c4cf5c-52d3-4fef-ac9b-67dbe6b5e52d", true, true); // the
																		// apikey
																		// from
		// documentation.

		System.out.println("Client started. Will do some annotation.");

		String data = "Ele se encontrará com José em Brasília.";
		System.out.println("Will annotate the text: " + data);
		LtasksObject result = client.processText(data);
		System.out.println("Text annotated. The result is:");
		System.out.println(result);
		
		  data = "http://pt.wikipedia.org/wiki/Cazuza";
		  System.out.println("Will annotate a URL: " + data); result =
		  client.processUrl(new URL(data));
		  System.out.println("URL annotated. The result is:");
		  System.out.println(result);

		data = "<html><p>Ele se encontrará com José em Brasília.</p></html>";
		System.out.println("Will annotate a HTML: " + data);
		result = client.processHtml(data);
		System.out.println("HTML annotated. The result is:");
		System.out.println(result);

		System.out.println("Vamos tentar acessar os resultados");

		if (result.isProcessedOk()) {
			System.out.println("Foi possivel anotar o texto.");
			if (result.getMessage() != null) { // o servidor enviu uma mensagem
				System.out.println("Mensagem do servidor: "
						+ result.getMessage());
			}
			if (result.getSourceText() != null) {
				System.out.println("Texto fonte normalizado: "
						+ result.getSourceText());
			}
			if (result.getNamedEntities() != null) {
				for (NamedEntity entity : result.getNamedEntities()) {
					System.out.println("  tipo: " + entity.getType().value()
							+ " inicio: " + entity.getBegin() + " fim: "
							+ entity.getEnd() + " texto: " + entity.getText());
				}
			}
		} else {
			System.out
					.println("Houve um erro! Vamos tentar obter a mensagem de erro.");
			System.out.println("Mensagem do servidor: " + result.getMessage());
		}
		
		client.processUrl(new URL("http://pttttt.wikipeeeeedia.org/wiki/Cazuza"));
		Thread.sleep(20000);
		}
		 
	}
}

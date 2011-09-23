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

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ResultParserTest {

	@Test
	public void testResultParser() throws IOException,
			ParserConfigurationException, SAXException {
		LtasksObject result = ResultParser.parse(getClass().getClassLoader()
				.getResourceAsStream("com/ltasks/sample1.xml"), true);

		assertEquals("Ele se encontrará com José em Brasília.",
				result.getText());
		assertEquals(2, result.getNamedEntities().size());
		
		assertTrue(result.isProcessedOk());

		NamedEntity e0 = result.getNamedEntities().get(0);
		NamedEntity e1 = result.getNamedEntities().get(1);

		assertEquals("José", e0.getText());
		assertEquals(NerType.PERSON, e0.getType());
		assertEquals(22, e0.getBegin());
		assertEquals(26, e0.getEnd());

		assertEquals("Brasília", e1.getText());
		assertEquals(NerType.PLACE, e1.getType());
		assertEquals(30, e1.getBegin());
		assertEquals(38, e1.getEnd());
	}

	@Test
	public void testNoText() throws IOException, ParserConfigurationException,
			SAXException {
		LtasksObject result = ResultParser.parse(getClass().getClassLoader()
				.getResourceAsStream("com/ltasks/sampleNoText.xml"), true);

		assertNull(result.getText());
		assertEquals(2, result.getNamedEntities().size());

		NamedEntity e0 = result.getNamedEntities().get(0);
		NamedEntity e1 = result.getNamedEntities().get(1);

		assertEquals("José", e0.getText());
		assertEquals(NerType.PERSON, e0.getType());
		assertEquals(22, e0.getBegin());
		assertEquals(26, e0.getEnd());

		assertEquals("Brasília", e1.getText());
		assertEquals(NerType.PLACE, e1.getType());
		assertEquals(30, e1.getBegin());
		assertEquals(38, e1.getEnd());
	}

	@Test
	public void testNoEntities() throws IOException,
			ParserConfigurationException, SAXException {
		LtasksObject result = ResultParser.parse(getClass().getClassLoader()
				.getResourceAsStream("com/ltasks/sampleNoEntities.xml"), true);

		assertEquals("Teste", result.getText());
		assertEquals(0, result.getNamedEntities().size());

	}
	
	@Test
	public void testError() throws IOException,
			ParserConfigurationException, SAXException {
		LtasksObject result = ResultParser.parse(getClass().getClassLoader()
				.getResourceAsStream("com/ltasks/error.xml"), false);

		assertNull(result.getText());
		assertNotNull(result.getMessage());
		assertEquals(0, result.getNamedEntities().size());

		assertFalse(result.isProcessedOk());
	}
}

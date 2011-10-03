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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SimpleXPathTest {
	
	private static final String SIMPLE = "//title";
	private static final String ARG = "//title[@lang]";
	private static final String VAL = "//title[@lang='eng']";
	private static final String AND = "|";

	@Test
	public void testParserOne() {
		List<SimpleXPath> m = SimpleXPath.parse(SIMPLE);
		
		assertEquals(1, m.size());
		assertMatcher("title", null, null, m.get(0));
		
		m = SimpleXPath.parse(ARG);
		
		assertEquals(1, m.size());
		assertMatcher("title", "lang", null, m.get(0));
		
		m = SimpleXPath.parse(VAL);
		
		assertEquals(1, m.size());
		assertMatcher("title", "lang", "eng", m.get(0));
	}
	
	@Test
	public void testParserThree() {
		List<SimpleXPath> m = SimpleXPath.parse(SIMPLE + AND + ARG + AND + VAL);
		
		assertEquals(3, m.size());
		assertMatcher("title", null, null, m.get(0));
		
		assertMatcher("title", "lang", null, m.get(1));
		
		assertMatcher("title", "lang", "eng", m.get(2));
	}
	

	
	@Test
	public void testParserNull() {
		List<SimpleXPath> m = SimpleXPath.parse(null);
		
		assertEquals(0, m.size());
	}
	
	@Test
	public void testParserEmpty() {
		List<SimpleXPath> m = SimpleXPath.parse("");
		
		assertEquals(0, m.size());
	}
	
	@Test
	public void testParserInvalid1() {
		List<SimpleXPath> m = SimpleXPath.parse("xx");
		
		assertEquals(0, m.size());
	}
	
	@Test
	public void testParserInvalid2() {
		List<SimpleXPath> m = SimpleXPath.parse(SIMPLE + ARG);
		
		assertEquals(1, m.size());
	}
	
	@Test
	public void testCorrectCreation() {
		assertMatcher("title", null, null, new SimpleXPath("title", null, null));
		
		assertMatcher("title", "lang", null, new SimpleXPath("title", "lang", null));
		
		assertMatcher("title", "lang", "eng", new SimpleXPath("title", "lang", "eng"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIncorrectCreation1() {
		new SimpleXPath(null, "lang", null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIncorrectCreation2() {
		new SimpleXPath("title", null, "eng");
	}
	
	private void assertMatcher(String element, String arg, String val,
			SimpleXPath m) {
		assertEquals(element, m.getElementName());
		assertEquals(arg, m.getAttributeName());
		assertEquals(val, m.getAttributeValue());
	}

}

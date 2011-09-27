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

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.util.EncodingUtil;

public class GZipPostMethod extends PostMethod{
	
    private boolean mIsGzip;

	public GZipPostMethod(String uri, boolean aIsGzip) {
        super(uri);
        mIsGzip = aIsGzip;
    }
	
	@Override
	protected byte[] generateRequestBody() {
		return super.generateRequestBody();
	};
	
	@Override
	protected RequestEntity generateRequestEntity() {
		if(mIsGzip) {
			try {
            String contentStr = EncodingUtil.formUrlEncode(getParameters(), getRequestCharSet());
            
            ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
            originalContent.write(EncodingUtil.getAsciiBytes(contentStr));
     
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //ChunkedOutputStream chunkedOut = new ChunkedOutputStream(baos);
            GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
     
            originalContent.writeTo(gzipOut);
     
            gzipOut.finish();
            byte[] content = baos.toByteArray();
     
            ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
                content,
                FORM_URL_ENCODED_CONTENT_TYPE
            );
            return entity;
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
		} else {
			return super.generateRequestEntity();
		}
	}
	

}

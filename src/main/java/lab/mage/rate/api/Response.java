/**
 * Copyright 2013 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package lab.mage.rate.api;

import java.util.Map;

public class Response {

    private final int status;
    private Map<String, String> headerParams;
    private final String json;

    public Response(final int status, final Map<String, String> headerParams, final String json) {
        super();
        this.status = status;
        this.headerParams = headerParams;
        this.json = json;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public String getJson() {
        return json;
    }
}

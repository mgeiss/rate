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

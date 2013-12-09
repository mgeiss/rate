package lab.mage.rate.api;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public static class Builder {
        private Type type;
        private Map<String, String> queryParams;
        private Map<String, String> headerParams;
        private String json;

        private Builder(Type type) {
            super();
            this.type = type;
        }

        public Builder addQueryParam(String key, String value) {
            if (this.queryParams == null) {
                this.queryParams = new HashMap<>();
            }
            this.queryParams.put(key, value);
            return this;
        }

        public Builder addHeaderParam(String key, String value) {
            if (this.headerParams == null) {
                this.headerParams = new HashMap<>();
            }
            this.headerParams.put(key, value);
            return this;
        }

        public Builder json(String json) {
            this.json = json;
            return this;
        }

        public Request build() {
            return new Request(this.type, this.queryParams, this.headerParams, this.json);
        }
    }

    public enum Type {
        GET,
        POST,
        PUT,
        DELETE
    }

    private final Type type;
    private final Map<String, String> queryParams;
    private final Map<String, String> headerParams;
    private final String json;

    private Request(final Type type, final Map<String, String> queryParams, final Map<String, String> headerParams,
                    final String json) {
        super();
        this.type = type;
        this.queryParams = queryParams;
        this.headerParams = headerParams;
        this.json = json;
    }

    public static Builder type(final Type type) {
        return new Builder(type);
    }

    public Type getType() {
        return type;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public String getJson() {
        return json;
    }
}

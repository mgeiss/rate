package lab.mage.rate.api;

public class Response {

    private final int status;
    private final String json;

    public Response(final int status, final String json) {
        this.status = status;
        this.json = json;
    }

    public int getStatus() {
        return status;
    }

    public String getJson() {
        return json;
    }
}

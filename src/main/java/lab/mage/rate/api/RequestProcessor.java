package lab.mage.rate.api;

public interface RequestProcessor {
    public Response call(String resourcePath, Request request);
}

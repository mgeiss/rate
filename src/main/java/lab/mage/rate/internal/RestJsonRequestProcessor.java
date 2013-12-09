package lab.mage.rate.internal;

import lab.mage.rate.api.Request;
import lab.mage.rate.api.RequestProcessor;
import lab.mage.rate.api.Response;
import lab.mage.rate.internal.util.JDBCConnectionProvider;
import lab.mage.rate.internal.util.StringUtil;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RestJsonRequestProcessor implements RequestProcessor {

    private final int testScenarioId;
    private final String host;
    private final String path;

    private Client client;
    private WebTarget target;

    public RestJsonRequestProcessor(final int testScenarioId, final String host, final String path) {
        super();
        this.testScenarioId = testScenarioId;
        this.host = host;
        this.path = path;

        this.initialize();
    }

    @Override
    public Response call(final String resourcePath, final Request request) {
        final long started = System.currentTimeMillis();
        Response response = null;
        switch (request.getType()) {
            case GET:
                response = this.get(resourcePath, request);
                this.log(resourcePath, request, response, (System.currentTimeMillis() - started));
                return response;
            case POST:
                response = this.post(resourcePath, request);
                this.log(resourcePath, request, response, (System.currentTimeMillis() - started));
                return response;
            case PUT:
                response = this.put(resourcePath, request);
                this.log(resourcePath, request, response, (System.currentTimeMillis() - started));
                return response;
            case DELETE:
                response = this.delete(resourcePath, request);
                this.log(resourcePath, request, response, (System.currentTimeMillis() - started));
                return response;
            default:
                throw new IllegalArgumentException();
        }
    }

    private Response get(final String resourcePath, final Request request) {
        WebTarget currentTarget = this.target;
        if (resourcePath != null) {
            currentTarget = currentTarget.path(resourcePath);
        }

        if (request.getQueryParams() != null) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                currentTarget = currentTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        final Invocation.Builder invocationBuilder = currentTarget.request();

        if (request.getHeaderParams() != null) {
            for (Map.Entry<String, String> entry : request.getHeaderParams().entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        final javax.ws.rs.core.Response serverResponse = invocationBuilder.get();

        String json = null;
        if (serverResponse.getStatus() == 200) {
            try {
                json = new Scanner((InputStream) serverResponse.getEntity(), StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
            } catch (Throwable th) {}
        }

        if (serverResponse.getHeaders() != null) {
            final HashMap<String, String> headerParams = new HashMap<>();
            for (Map.Entry<String, List<Object>> entry : serverResponse.getHeaders().entrySet()) {
                headerParams.put(entry.getKey(), StringUtil.join(entry.getValue(), ","));
            }
        }

        return new Response(serverResponse.getStatus(), null, json);
    }

    private Response post(final String resourcePath, final Request request) {
        WebTarget currentTarget = this.target;
        if (resourcePath != null) {
            currentTarget = currentTarget.path(resourcePath);
        }

        if (request.getQueryParams() != null) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                currentTarget = currentTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        final Invocation.Builder invocationBuilder = currentTarget.request(MediaType.APPLICATION_JSON);

        if (request.getHeaderParams() != null) {
            for (Map.Entry<String, String> entry : request.getHeaderParams().entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        final javax.ws.rs.core.Response serverResponse = invocationBuilder.post(Entity.json(request.getJson()));

        String json = null;
        if ((serverResponse.getStatus() == 200 ||serverResponse.getStatus() == 201) && serverResponse.getEntity() != null) {
            try {
                json = new Scanner((InputStream) serverResponse.getEntity(), StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
            } catch (Throwable th) {}
        }

        HashMap<String, String> headerParams = null;
        if (serverResponse.getHeaders() != null) {
            headerParams  = new HashMap<>();
            for (Map.Entry<String, List<Object>> entry : serverResponse.getHeaders().entrySet()) {
                headerParams.put(entry.getKey(), StringUtil.join(entry.getValue(), ","));
            }
        }

        return new Response(serverResponse.getStatus(), headerParams, json);
    }

    private Response put(final String resourcePath, final Request request) {
        WebTarget currentTarget = this.target;
        if (resourcePath != null) {
            currentTarget = currentTarget.path(resourcePath);
        }

        if (request.getQueryParams() != null) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                currentTarget = currentTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        final Invocation.Builder invocationBuilder = currentTarget.request(MediaType.APPLICATION_JSON);

        if (request.getHeaderParams() != null) {
            for (Map.Entry<String, String> entry : request.getHeaderParams().entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        final javax.ws.rs.core.Response serverResponse = invocationBuilder.put(Entity.json(request.getJson()));

        String json = null;
        if (serverResponse.getStatus() == 200 && serverResponse.getEntity() != null) {
            try {
                json = new Scanner((InputStream) serverResponse.getEntity(), StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
            } catch (Throwable th) {}
        }

        HashMap<String, String> headerParams = null;
        if (serverResponse.getHeaders() != null) {
            headerParams  = new HashMap<>();
            for (Map.Entry<String, List<Object>> entry : serverResponse.getHeaders().entrySet()) {
                headerParams.put(entry.getKey(), StringUtil.join(entry.getValue(), ","));
            }
        }

        return new Response(serverResponse.getStatus(), headerParams, json);
    }

    private Response delete(final String resourcePath, final Request request) {
        WebTarget currentTarget = this.target;
        if (resourcePath != null) {
            currentTarget = currentTarget.path(resourcePath);
        }

        if (request.getQueryParams() != null) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                currentTarget = currentTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        final Invocation.Builder invocationBuilder = currentTarget.request();

        if (request.getHeaderParams() != null) {
            for (Map.Entry<String, String> entry : request.getHeaderParams().entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        final javax.ws.rs.core.Response serverResponse = invocationBuilder.delete();

        HashMap<String, String> headerParams = null;
        if (serverResponse.getHeaders() != null) {
            headerParams  = new HashMap<>();
            for (Map.Entry<String, List<Object>> entry : serverResponse.getHeaders().entrySet()) {
                headerParams.put(entry.getKey(), StringUtil.join(entry.getValue(), ","));
            }
        }

        return new Response(serverResponse.getStatus(), headerParams, null);
    }

    private void initialize() {
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(this.host).path(this.path);
    }

    private void log(String resourcePath, Request request, Response response, long duration) {
        final String insertStatement =
                "insert into request_log (test_scenario_id, robot_name, sub_resource, request_type, send_bytes, " +
                        "received_bytes, status, duration, created) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            final Connection connection = JDBCConnectionProvider.create();

            final PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, this.testScenarioId);
            preparedStatement.setString(2, Thread.currentThread().getName());
            preparedStatement.setString(3, resourcePath);
            preparedStatement.setString(4, request.getType().name());
            preparedStatement.setInt(5, (request.getJson() != null ? request.getJson().getBytes(StandardCharsets.UTF_8).length : 0));
            preparedStatement.setInt(6, (response.getJson() != null ? response.getJson().getBytes(StandardCharsets.UTF_8).length : 0));
            preparedStatement.setInt(7, response.getStatus());
            preparedStatement.setLong(8, duration);
            preparedStatement.setDate(9, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

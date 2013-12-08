package lab.mage.rate.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class NewsServer {

    public NewsServer() {
        super();
    }

    public static void main(String[] args) throws Exception {

        // configure resource
        final ResourceConfig resourceConfig = new ResourceConfig();

        // set news service
        final NewsService newsService = new NewsService();
        resourceConfig.register(newsService);

        // set Jackson as JSON provider
        final ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(jsonMapper);
        resourceConfig.register(provider);

        // create Grizzly instance and add handler
        final HttpHandler handler = ContainerFactory.createContainer(GrizzlyHttpContainer.class, resourceConfig);
        final URI uri = new URI("http://localhost:4711/");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri);
        final ServerConfiguration config = server.getServerConfiguration();
        config.addHttpHandler(handler, "/api");

        // start
        server.start();
        System.in.read();
    }
}

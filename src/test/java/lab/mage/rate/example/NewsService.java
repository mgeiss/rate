package lab.mage.rate.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/news")
public class NewsService {

    private Map<Long, News> newsCache;
    private AtomicLong idGenerator = new AtomicLong(1L);

    public NewsService() {
        super();
        this.initialize();
    }

    @Path("/")
    @GET
    @Consumes({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNews() {
        final ArrayList<News> news = new ArrayList<>(this.newsCache.values());
        return Response.ok(news).build();
    }

    @Path("/{id}")
    @GET
    @Consumes({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNews(@PathParam("id") final long id) {
        final News news = this.newsCache.get(id);
        if (news == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(news).build();
    }

    @Path("/")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createNews(final News news) {
        if (news.getId() > 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        news.setId(this.idGenerator.getAndIncrement());
        this.newsCache.put(news.getId(), news);
        return Response.created(URI.create("news/" + news.getId())).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateNews(@PathParam("id")final long id, final News news) {
        if (id != news.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        this.newsCache.put(news.getId(), news);
        return Response.ok().build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    public Response deleteNews(@PathParam("id")final long id) {
        this.newsCache.remove(id);
        return Response.ok().build();
    }

    private void initialize() {
        this.newsCache = Collections.synchronizedMap(new HashMap<Long, News>());
        this.createFixtures();
    }

    private void createFixtures() {
        for (int i = 0; i < 10; i++) {
            final News news = new News();
            news.setId(this.idGenerator.getAndIncrement());
            news.setTopic("Topic " + i);
            news.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.");
            news.setTimeStamp(new Date());

            this.newsCache.put(news.getId(), news);
        }
    }
}

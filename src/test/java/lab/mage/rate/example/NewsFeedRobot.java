package lab.mage.rate.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lab.mage.rate.api.Request;
import lab.mage.rate.api.RequestProcessor;
import lab.mage.rate.api.Response;
import lab.mage.rate.api.Robot;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewsFeedRobot implements Robot {

    private final ObjectMapper mapper = new ObjectMapper();

    public NewsFeedRobot() {
        super();
    }

    @Override
    public void start(long lifetime, RequestProcessor requestProcessor) {
        final long callADay = System.currentTimeMillis() + lifetime;

        while (callADay > System.currentTimeMillis()) {
            final Response responseGetAll = requestProcessor.call("news", Request.type(Request.Type.GET).build());
            List<News> foundNews = null;
            try {
                foundNews = this.mapper.readValue(responseGetAll.getJson(), TypeFactory.defaultInstance().constructCollectionType(List.class, News.class));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2L));
            } catch (InterruptedException e) {
                // do nothing
            }

            final Response responseNews = requestProcessor.call("news/" + (foundNews != null ? (foundNews.size() / 3) : 3), Request.type(Request.Type.GET).build());

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(10L));
            } catch (InterruptedException e) {
                // do nothing
            }

            final News news = new News();
            news.setTopic(Thread.currentThread().getName());
            news.setTimeStamp(new Date(System.currentTimeMillis()));
            news.setText("No news are good news!");

            try {
                final Response responseCreated = requestProcessor.call("news", Request.type(Request.Type.POST).json(mapper.writeValueAsString(news)).build());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
            } catch (InterruptedException e) {
                // do nothing
            }

            if (foundNews != null) {
                try {
                    final News news2update = foundNews.get(foundNews.size() - 1);
                    news2update.setText("Here are some good news!");
                    final Response responseUpdated = requestProcessor.call("news/" + news2update.getId(), Request.type(Request.Type.PUT).json(mapper.writeValueAsString(news2update)).build());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(7L));
            } catch (InterruptedException e) {
                // do nothing
            }

            if (foundNews != null && foundNews.size() > 200) {
                final News news2delete = foundNews.get(foundNews.size() / 2);
                final Response responseDeleted = requestProcessor.call("news/" + news2delete.getId(), Request.type(Request.Type.DELETE).build());
            }

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(3L));
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
}

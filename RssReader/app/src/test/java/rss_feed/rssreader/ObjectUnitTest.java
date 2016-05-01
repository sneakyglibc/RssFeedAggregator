package rss_feed.rssreader;

import org.junit.Test;

import rss_feed.rssreader.Data.Flux;
import rss_feed.rssreader.Data.FluxNode;
import rss_feed.rssreader.Data.Item;
import rss_feed.rssreader.Data.ItemNode;
import rss_feed.rssreader.Data.User;

/**
 * Created by Phil on 01/05/2016.
 */
public class ObjectUnitTest {
    @Test
    public void item_isCorrect() throws Exception {
        Item item = new Item("http://www.test.com", "Test");
    }

    @Test
    public void itemNode_isCorrect() throws Exception {
        ItemNode itemNode = new ItemNode("http://www.test.com", "Test");
    }

    @Test
    public void flux_isCorrect() throws Exception {
        Flux flux = new Flux("http://www.test.com", "Test");
    }

    @Test
    public void fluxNode_isCorrect() throws Exception {
        FluxNode flux = new FluxNode("http://www.test.com", "Test");
    }

    @Test
    public void user_isCorrect() throws Exception {
        User user = new User("test", "UserTest");
    }

}

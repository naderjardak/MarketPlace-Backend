package tn.workbot.coco_marketplace.Api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ScraperEssence {
    public String scrapePage(String url) throws Exception {
        // Fetch the web page using Jsoup
        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Element cell = table.select("tr").get(1).select("td").get(0);
        // Return the scraped data
        return String.format(cell.text());
    }

}

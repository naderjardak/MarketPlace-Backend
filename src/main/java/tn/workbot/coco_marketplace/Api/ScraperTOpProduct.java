package tn.workbot.coco_marketplace.Api;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScraperTOpProduct {
    public Map<String, Map<String, String>> getProducts(String categoryUrl) throws IOException {
        Map<String, Map<String, String>> products = new HashMap<>();
        String url = categoryUrl;
        Document doc = Jsoup.connect(url).get();
        Elements productCards = doc.select(".v2-listing-card__info");
        for (Element card : productCards) {
            String id = String.valueOf(card.select(".v2-listing-card__title"));
            String name = card.select(".v2-listing-card__title").text();
            String price = card.select(".v2-listing-card__price").text();
            String imageUrl = card.select(".v2-listing-card__image img").attr("src");
            Map<String, String> productData = new HashMap<>();
            productData.put("name", name);
            productData.put("price", price);
            productData.put("image-url", imageUrl);
            products.put(id, productData);
        }
        return products;
    }

    public void exportToXsl(Map<String, Map<String, String>> products, String filename) throws IOException {
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<products>\n");
        for (Map.Entry<String, Map<String, String>> entry : products.entrySet()) {
            writer.write("  <product id=\"" + entry.getKey() + "\">\n");
            Map<String, String> productData = entry.getValue();
            for (Map.Entry<String, String> dataEntry : productData.entrySet()) {
                writer.write("    <" + dataEntry.getKey() + ">" + dataEntry.getValue() + "</" + dataEntry.getKey() + ">\n");
            }
            writer.write("  </product>\n");
        }
        writer.write("</products>");
        writer.close();
    }

}

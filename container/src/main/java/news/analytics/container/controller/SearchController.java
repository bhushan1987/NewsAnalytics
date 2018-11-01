package news.analytics.container.controller;

import news.analytics.container.core.SolrSearchClient;
import news.analytics.container.core.TrendGenerator;
import news.analytics.model.search.SearchQuery;
import news.analytics.model.search.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private TrendGenerator trendGenerator;

    @Autowired
    private SolrSearchClient solrSearchClient;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "static/index.html";
    }

    @GetMapping()
    @RequestMapping(value = "/protected/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SearchResult> search(@RequestBody SearchQuery searchQuery, Integer limit) throws IOException, SolrServerException {
        List<SearchResult> search = solrSearchClient.search(searchQuery, 250);
        return search;
    }

    @GetMapping()
    @RequestMapping(value = "/protected/trends", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Short> getTrends(@RequestBody SearchQuery searchQuery) throws IllegalAccessException, SQLException, InstantiationException, IOException {
        Map<String, Short> stringIntegerMap = null;
        stringIntegerMap = trendGenerator.generateTrend(searchQuery.getDateFrom(), searchQuery.getDateTo());
        stringIntegerMap.remove(""); // remove entry of empty string
        return stringIntegerMap;
    }
}

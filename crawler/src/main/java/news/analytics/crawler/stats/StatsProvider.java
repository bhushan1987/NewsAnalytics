package news.analytics.crawler.stats;

import news.analytics.dao.connection.DataSource;
import news.analytics.dao.core.GenericDao;
import news.analytics.dao.query.PredicateClause;
import news.analytics.dao.query.PredicateOperator;
import news.analytics.model.news.RawNews;
import news.analytics.model.news.Seed;
import news.analytics.model.news.TransformedNews;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides crawl statistics from crawl db based on the given predicate.
 */
public class StatsProvider {

    private DataSource dataSource;

    public StatsProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public String getStats() throws SQLException, IllegalAccessException, IOException, InstantiationException {
        StringBuilder sb = new StringBuilder();
        Connection connection = dataSource.getConnection();
        sb.append(getStatsForSeedTable(connection));
        sb.append(getStatsForRawNewsTable(connection));
        sb.append(getStatsForTransformedNewsTable(connection));
        connection.close();
        return sb.toString();
    }

    private String getStatsForSeedTable(Connection connection) throws SQLException, IOException, InstantiationException, IllegalAccessException {
        StringBuilder sb = new StringBuilder("Seed").append("\n********************************\n");
        GenericDao<Seed> genericDao = new GenericDao<>(Seed.class);
        PredicateClause predicateClause = new PredicateClause("FETCH_STATUS", PredicateOperator.EQUALS, "FETCHED");
        Long fetched = genericDao.count(connection, predicateClause);

        predicateClause = new PredicateClause("FETCH_STATUS", PredicateOperator.EQUALS, "UNFETCHED");
        Long unFetched = genericDao.count(connection, predicateClause);

        predicateClause = new PredicateClause("FETCH_STATUS", PredicateOperator.EQUALS, "SERVER_ERROR");
        Long serverError = genericDao.count(connection, predicateClause);

        predicateClause = new PredicateClause("FETCH_STATUS", PredicateOperator.EQUALS, "CLIENT_ERROR");
        Long clientError = genericDao.count(connection, predicateClause);

        sb.append("UNFETCHED:").append(unFetched).append("\n");
        sb.append("FETCHED:").append(fetched).append("\n");
        sb.append("SERVER_ERROR:").append(serverError).append("\n");
        sb.append("CLIENT_ERROR:").append(clientError).append("\n");
        return sb.append("\n").toString();
    }

    private String getStatsForRawNewsTable(Connection connection) throws SQLException, IOException, InstantiationException, IllegalAccessException {
        StringBuilder sb = new StringBuilder("RawNews").append("\n********************************\n");
        GenericDao<RawNews> genericDao = new GenericDao<>(RawNews.class);
        PredicateClause predicateClause = new PredicateClause("PROCESS_STATUS", PredicateOperator.EQUALS, "RAW_NEWS_PROCESSED");

        Long processed = genericDao.count(connection, predicateClause);

        predicateClause = new PredicateClause("PROCESS_STATUS", PredicateOperator.EQUALS, "RAW_NEWS_UNPROCESSED");
        Long unProcessed = genericDao.count(connection, predicateClause);

        sb.append("RAW_NEWS_PROCESSED:").append(processed).append("\n");
        sb.append("RAW_NEWS_UNPROCESSED:").append(unProcessed).append("\n");
        return sb.append("\n").toString();
    }

    private String getStatsForTransformedNewsTable(Connection connection) throws SQLException, IOException, InstantiationException, IllegalAccessException {
        StringBuilder sb = new StringBuilder("TransformedNews").append("\n********************************\n");
        GenericDao<TransformedNews> genericDao = new GenericDao<>(TransformedNews.class);

        PredicateClause predicateClause = new PredicateClause("PROCESS_STATUS", PredicateOperator.EQUALS, "TRANSFORMED_NEWS_NOT_ANALYZED");
        Long processed = genericDao.count(connection, predicateClause);

        predicateClause = new PredicateClause("PROCESS_STATUS", PredicateOperator.EQUALS, "TRANSFORMED_NEWS_ANALYZED");
        Long unProcessed = genericDao.count(connection, predicateClause);

        sb.append("TRANSFORMED_NEWS_NOT_ANALYZED:").append(processed).append("\n");
        sb.append("TRANSFORMED_NEWS_ANALYZED:").append(unProcessed).append("\n");
        return sb.append("\n").toString();
    }
}

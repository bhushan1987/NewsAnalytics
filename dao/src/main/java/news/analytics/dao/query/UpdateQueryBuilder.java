package news.analytics.dao.query;

import news.analytics.modelinfo.ModelInfo;

public class UpdateQueryBuilder extends AbstractQueryBuilder {
    public UpdateQueryBuilder(ModelInfo modelInfo) {
        super(modelInfo);
    }

    public String getQueryString(PredicateClause predicateClause) {
        return null;
    }

    public String getQueryString() {
        return null;
    }
}

package news.analytics.model;

import news.analytics.model.annotations.ConstraintType;
import news.analytics.model.annotations.DBColumn;
import news.analytics.model.annotations.DBConstraint;
import news.analytics.model.annotations.DBTable;
import news.analytics.model.constants.DataType;

@DBTable(mappedTable = "RAW_NEWS")
public class RawNews extends NewsEntity {
    private static final long serialVersionUID = 1112178891279811171L;

    @DBColumn(column = "ID", dataType = DataType.LONG, primaryKey = true, constraints = @DBConstraint(constraintType = ConstraintType.PRIMARY_KEY, constraintName = "ID_PK_RAW_NEWS"))
    private Long id;

    @DBColumn(column = "URI", dataType = DataType.VARCHAR, nullable = false, constraints = @DBConstraint(constraintType = ConstraintType.UNIQUE, constraintName = "URI_UNIQUE_RAW_NEWS"))
    private String uri;

    @DBColumn(column = "NEWS_AGENCY", dataType = DataType.VARCHAR, nullable = false)
    private String newsAgency;

    @DBColumn(column = "RAW_CONTENT", dataType = DataType.CLOB)
    private String rawContent;

    @DBColumn(column = "PROCESS_STATUS", dataType = DataType.VARCHAR, nullable = false)
    private String processStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNewsAgency() {
        return newsAgency;
    }

    public void setNewsAgency(String newsAgency) {
        this.newsAgency = newsAgency;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
}

package news.analytics.model.news;

import news.analytics.model.annotations.*;
import news.analytics.model.constants.DataType;

import java.util.Set;

/**
 * Represents a news after first step of transformation is done on RawNews.<br>
 * Contains information extracted only from RawNews.
 *
 */
@DBTable(mappedTable = "TRANSFORMED_NEWS")
public class TransformedNews extends NewsEntity {
    private static final long serialVersionUID = 1223217431627688173L;
    public TransformedNews() {
    }

    @DBColumn(column = "ID", dataType = DataType.LONG, primaryKey = true, constraints = @DBConstraint(constraintType = ConstraintType.PRIMARY_KEY, constraintName = "ID_PK_TRANSFORMED_NEWS"))
    private Long id;

    @DBColumn(column = "URI", dataType = DataType.VARCHAR, nullable = false, constraints = @DBConstraint(constraintType = ConstraintType.UNIQUE, constraintName = "URI_UNIQUE_TRANSFORMED_NEWS"))
    private String uri;

    @DBColumn(column = "NEWS_AGENCY", dataType = DataType.VARCHAR, nullable = false)
    private String newsAgency;

    @DBColumn(column = "CHARSET", dataType = DataType.VARCHAR, nullable = false)
    private String charset;

    /** Section of the news - international, society, sports, politics, etc*/
    @DBColumn(column = "SECTION", dataType = DataType.VARCHAR, nullable = false)
    private String section;

    @DBColumn(column = "TITLE", dataType = DataType.VARCHAR, nullable = false) // size 500
    private String title;

    @DBColumn(column = "CONTENT", dataType = DataType.CLOB)
    private String content;

    @DBColumn(column = "KEYWORDS", dataType = DataType.VARCHAR)
    @DataConverter("news.analytics.dao.core.StringToSetConverter")
    private Set<String> keywords;

    @DBColumn(column = "H1", dataType = DataType.VARCHAR) // size 500
    private String h1;

    @DBColumn(column = "H2", dataType = DataType.VARCHAR) // size 500
    private String h2;

    /** Snippet of news article */
    @DBColumn(column = "SNIPPET", dataType = DataType.VARCHAR) // size 1000
    private String description;

    @DBColumn(column = "PUBLISH_DATE", dataType = DataType.LONG)
    private Long publishDate;

    @DBColumn(column = "MODIFIED_DATE", dataType = DataType.LONG)
    private Long modifiedDate;

    @DBColumn(column = "CREATION_DATE", dataType = DataType.LONG)
    private Long creationDate;

    @DBColumn(column = "AUTHOR", dataType = DataType.VARCHAR)
    private String author;

    /** Plain text of the news html page */
    @DBColumn(column = "PLAIN_TEXT", dataType = DataType.VARCHAR)
    private String plainText;

    @DBColumn(column = "PROCESS_STATUS", dataType = DataType.VARCHAR, nullable = false)
    private String processStatus;

    /** Section of the news - international, society, sports, politics, etc*/
    @DBColumn(column = "CITY", dataType = DataType.VARCHAR, nullable = false)
    private String city;

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Long publishDate) {
        this.publishDate = publishDate;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}

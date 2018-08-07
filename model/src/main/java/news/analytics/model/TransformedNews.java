package news.analytics.model;

import news.analytics.model.annotations.ConstraintType;
import news.analytics.model.annotations.DBColumn;
import news.analytics.model.annotations.DBConstraint;
import news.analytics.model.annotations.DBTable;
import news.analytics.model.constants.DataType;

/**
 * Represents a news after first step of transformation is done on RawNews
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
    @DBColumn(column = "CHARSET", dataType = DataType.VARCHAR, nullable = false)
    private String section;

    @DBColumn(column = "TITLE", dataType = DataType.VARCHAR, nullable = false) // size 500
    private String title;

    @DBColumn(column = "CONTENT", dataType = DataType.CLOB)
    private String content;

    @DBColumn(column = "KEYWORDS", dataType = DataType.CLOB)
    private String keywords; // store them as json?

    @DBColumn(column = "H1", dataType = DataType.VARCHAR) // size 500
    private String h1;

    @DBColumn(column = "H2", dataType = DataType.VARCHAR) // size 500
    private String h2;

    /** Tags from the meta elements in html */
    @DBColumn(column = "TAGS", dataType = DataType.VARCHAR) // size 1000
    private String defaultTags;

    /** Tags extracted by the pipeline by analyzing the content */
    @DBColumn(column = "EXTRACTED_TAGS", dataType = DataType.VARCHAR) // size 1000
    private String extractedTags;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
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

    public String getDefaultTags() {
        return defaultTags;
    }

    public void setDefaultTags(String defaultTags) {
        this.defaultTags = defaultTags;
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

    public String getExtractedTags() {
        return extractedTags;
    }

    public void setExtractedTags(String extractedTags) {
        this.extractedTags = extractedTags;
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
}

CREATE TABLE "SEEDS"
(
"ID" BIGINT PRIMARY KEY,
"URI" VARCHAR2(500 CHAR) NOT NULL,
"FETCH_STATUS" VARCHAR2(250 CHAR),
"HTTP_CODE" INT
);

CREATE TABLE "RAW_NEWS"
(
"ID" BIGINT PRIMARY KEY,
"URI" VARCHAR2(500 CHAR) NOT NULL,
"NEWS_AGENCY" VARCHAR2(250 CHAR),
"RAW_CONTENT" CLOB,
"PROCESS_STATUS" VARCHAR
);

CREATE TABLE "TRANSFORMED_NEWS"
(
"ID" BIGINT PRIMARY KEY,
"URI" VARCHAR2(500 CHAR) NOT NULL,
"NEWS_AGENCY" VARCHAR2(250 CHAR)NOT NULL,
"CHARSET" VARCHAR2(100 CHAR),
"SECTION" VARCHAR2(500 CHAR),
"TITLE" VARCHAR2(1000 CHAR) NOT NULL,
"CONTENT" CLOB,
"KEYWORDS" VARCHAR2(1000 CHAR),
"H1" VARCHAR2(1000 CHAR),
"H2" VARCHAR2(1000 CHAR),
"SNIPPET" VARCHAR2(1000 CHAR),
"PUBLISH_DATE" BIGINT,
"MODIFIED_DATE" BIGINT,
"CREATION_DATE" BIGINT,
"AUTHOR" VARCHAR2(500 CHAR),
"PLAIN_TEXT" CLOB
);
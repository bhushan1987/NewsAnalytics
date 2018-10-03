package news.analytics.pipeline.analyze;

import news.analytics.model.news.AnalyzedNews;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;



public class TagGenerator extends StopwordAnalyzerBase {

    Set<String> stopWords;
    private CharArraySet charArraySet;
    private static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

    public TagGenerator(Set<String> stopWords) {
        this.stopWords = stopWords;
        charArraySet = new CharArraySet(350, true);
        for(String stopWord : stopWords) {
            charArraySet.add(stopWord);
        }
    }

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        src.setMaxTokenLength(DEFAULT_MAX_TOKEN_LENGTH);
        TokenStream tok = new StandardFilter(src);
        tok = new LowerCaseFilter(tok);
        tok = new StopFilter(tok, charArraySet);
        return new Analyzer.TokenStreamComponents(src, tok) {
            @Override
            protected void setReader(final Reader reader) {
                src.setMaxTokenLength(DEFAULT_MAX_TOKEN_LENGTH);
                super.setReader(reader);
            }
        };
    }

    /**
     * Generates tags from the given text. Top 3 tags are returned which will be considered as the 'ExtractedTags' inside AnalyzedNews.
     * @param text
     * @return Top 3 tags
     * @throws IOException
     */
    public Set<String> generateTags(String text) throws IOException {
        Map<String, Integer> tags = new TreeMap();
        Set<String> nGrams = new TreeSet<String>();
        String textWithoutStopWords = removeStopWords(text);
        // TODO replace all the marathi numbers

        StringReader reader = new StringReader(text);
        TokenStream tokenStream = tokenStream("content", reader);
        ShingleFilter theFilter = new ShingleFilter(tokenStream); // Construct a ShingleFilter with default shingle size: 2
        theFilter.setOutputUnigrams(true);
        theFilter.setMaxShingleSize(4);

        CharTermAttribute charTermAttribute = theFilter.addAttribute(CharTermAttribute.class);
        theFilter.reset();

        while (theFilter.incrementToken()) {
            String bigram = charTermAttribute.toString();
            // dont add tokens with _ char or having length 1
            if(!bigram.startsWith("_") && !bigram.endsWith("_") && bigram.length() != 1)
                nGrams.add(bigram);
        }
        theFilter.end();
        theFilter.close();

        for(String nGram : nGrams) {
            if(nGram.length() == 1) {
                continue;
            }

            int i = StringUtils.countMatches(text, nGram);

            if(nGram.contains(" ")) { // space means more than one word - nGram
                if(i >= 2) {
                    tags.put(nGram, i);
                }
            } else {
                if(i >= 5) {
                    tags.put(nGram, i);
                }
            }
        }
        tags = optimizeTags(tags);
        tags = sortDescByValue(tags);
        return getTopFive(tags);
    }

    private String removeStopWords(String text) {
        String textWithoutStopWords = null;
        for(String stopWord : stopWords) {
            textWithoutStopWords = text.replaceAll(" " + stopWord + " ", "").replaceAll(" " + stopWord + ".", "");
        }

        return textWithoutStopWords;
    }

    private Set<String> getTopFive(Map<String, Integer> tags) {
        Set<String> toReturn = new TreeSet<>();
        for(Map.Entry<String, Integer> entry : tags.entrySet()) {
            toReturn.add(entry.getKey());
            if(toReturn.size() == 5) {
                break;
            }
        }
        return toReturn;
    }

    public void generateTags(AnalyzedNews analyzedNews) throws IOException {
        Set<String> secondaryTags = generateTags(analyzedNews.getContent());
        analyzedNews.setSecondaryTags(secondaryTags);

        // generated tags are from keywords from the source, then such tags are considered as the primary tags
        Set<String> primaryTags = new TreeSet<>();
        Set<String> keywords = analyzedNews.getKeywords();
        if(keywords != null) {
            for(String tag : secondaryTags) {
                if(keywords.contains(tag)) {
                    primaryTags.add(tag);
                }
            }
        } else {
            // if keywords are null, then keywords = secondary = primary
            analyzedNews.setKeywords(secondaryTags);
        }

        if(primaryTags.size() == 0) {
            if(secondaryTags.size() == 0) {
                analyzedNews.setPrimaryTags(analyzedNews.getKeywords());
            } else {
                analyzedNews.setPrimaryTags(secondaryTags);
            }
        } else {
            analyzedNews.setPrimaryTags(primaryTags);
        }
    }


    public <K, V extends Comparable<? super V>> Map<K, V> sortDescByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public Map<String, Integer> optimizeTags(Map<String, Integer> tags) {

        String tagStr = tags.toString();
        Set<String> tagsToRemove = new TreeSet<>();
        for(String tag : tags.keySet()) {

            /*Doesnt work for marathi numbers
            if (NumberUtils.isNumber(tag)) {
            if(tag.matches("[१-९]+")){
                tagsItr.remove();
                continue;
            }*/

            // Remove tags which are covered in other tags
            if (StringUtils.countMatches(tagStr, tag) > 1) {
                tagsToRemove.add(tag);
            }
        }
        for(String keyToRemove : tagsToRemove) {
            tags.remove(keyToRemove);
        }

        return tags;
    }

}

package news.analytics.pipeline.analyze;

import news.analytics.model.news.AnalyzedNews;
import news.analytics.pipeline.utils.PipelineUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class SentimentAnalyzer {
    private Set<String> positive;
    private Set<String> negative;
    private Set<String> neutral;
    private Set<String> adverbs;
    private Set<String> stopwords;
    private Set<String> stopKeywords;
    private Set<String> adverbWithPositive;
    private Set<String> adverbWithNegative;
    private Set<String> adverbWithNeutral;

    public SentimentAnalyzer() throws IOException {
        loadDictionaries();
    }

    public Set<String> getStopwords() {
        return stopwords;
    }

    // Future reference - Analyzer will be biased towards the words in given dictionary
    /*
    public SentimentAnalyzer(Dictionary baisedTowards) throws IOException {

    }*/

    public float generateSentimentScore(AnalyzedNews analyzedNews) {
        String title = removeStopWords(analyzedNews.getTitle());
        String h1 = removeStopWords(analyzedNews.getH1());
        String text = removeStopWords(analyzedNews.getContent());
        String h2 = "";
        if(analyzedNews.getH2() != null) {
            h2 = removeStopWords(analyzedNews.getH2());
        }

        Float titleScore = process(title);
        Float h1Score = process(h1);
        Float h2Score = process(h2);

        String[] sentences = text.split("\\.");
        Float textScore = 0F;
        for(String sentence : sentences ) {
            if(!sentence.trim().equalsIgnoreCase("")) {
                textScore += process(sentence.trim()) / sentence.split(" ").length;
            }
        }

        Float sum = (titleScore + h1Score + textScore+ h2Score);

        // normalize using VADER normalization formula -> ( x / sqrt (x^2 + 15) )
        return (float) (sum / Math.sqrt(sum * sum + 15));
    }

    private Float process(String line) {

        // Maintain separate counts of Positive and Negative words
        Float positiveScore = 0F;
        Float negativeScore = 0F;
        if (line == null || line.trim() == "") {
            return 0F;
        }
        // If title/h1 contains question mark/exclamation mark
        if (line.contains("?")) {
            Float score = process(line.substring(0, line.indexOf("?")));
            if(score > 0)
                negativeScore++;
            else if (score < 0) {
                positiveScore++;
            }
        }

        if (line.contains("!")) {
            Float score = process(line.substring(0, line.indexOf("!")));
            if(score > 0)
                positiveScore ++;
            else if (score < 0) {
                negativeScore++;
            }
        }

        // Adjective followed by a noun/verb -> = +2
        for (String positiveWord : positive) {
            if (line.contains(positiveWord)) {
                int i = StringUtils.countMatches(line, positiveWord);
                positiveScore += i;
            }
        }

        for (String toSearch : adverbWithPositive) {
            if (line.contains(toSearch)) {
                int i = StringUtils.countMatches(line, toSearch);
                positiveScore += (2 * i);
            }
        }

        for (String negativeWord : negative) {
            if (line.contains(negativeWord)) {
                int i = StringUtils.countMatches(line, negativeWord);
                negativeScore += i;
            }
        }
        for (String toSearch : adverbWithNegative) {
            if (line.contains(toSearch)) {
                int i = StringUtils.countMatches(line, toSearch);
                negativeScore += (2 * i);
            }
        }

        // for the combination of adverb + neutral =+1
        for (String toSearch : adverbWithNeutral) {
            if (line.contains(toSearch)) {
                int i = StringUtils.countMatches(line, toSearch);
                positiveScore += 1;
            }
        }

        // Avg Score = (Positive - Negative)
        return (positiveScore - negativeScore);
    }

    private String removeStopWords(String text) {
        for(String stopWord : stopwords){
            text = text.replaceAll(" " + stopWord + " ", " ");
            text = text.replaceAll(" " + stopWord + "\\.", " "); // for stopword + full stop
            text = text.replaceAll(" " + stopWord + ",", " ");
            text = text.replaceAll("[(0-9)*(०-९)*]", "");
        }
        return text;
    }

    private void loadDictionaries() throws IOException {
        positive = PipelineUtils.loadDictionaryFile("positive.txt");
        negative = PipelineUtils.loadDictionaryFile("negative.txt");
        neutral = PipelineUtils.loadDictionaryFile("neutral.txt");
        adverbs = PipelineUtils.loadDictionaryFile("marathi_adverbs.txt");
        stopwords = PipelineUtils.loadDictionaryFile("stopwords.txt");
        stopKeywords = PipelineUtils.loadDictionaryFile("stop_keywords.txt");
        adverbWithPositive = attachAdverb("positive");
        adverbWithNegative = attachAdverb("negative");
        adverbWithNeutral = attachAdverb("neutral");
    }

    private Set<String> attachAdverb(String wordDictionaryType) {
        Set<String> words = new TreeSet<String>();
        if (wordDictionaryType.equals("positive")) {
            for (String word : positive) {
                for (String adverb : adverbs) {
                    words.add(adverb + " " + word);
                }
            }
        } else if (wordDictionaryType.equals("negative")) {
            for (String word : negative) {
                for (String adverb : adverbs) {
                    words.add(adverb + " " + word);
                }
            }
        } else if (wordDictionaryType.equals("neutral")) {
            for (String word : neutral) {
                for (String adverb : adverbs) {
                    words.add(adverb + " " + word);
                }
            }
        }
        return words;
    }

    public Set<String> getStopKeywords() {
        return stopKeywords;
    }
}

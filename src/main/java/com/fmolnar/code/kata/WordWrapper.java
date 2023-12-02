package com.fmolnar.code.kata;

public class WordWrapper {

    public static final String BREAK = "\n";
    private final int nbColumn;

    private WordWrapper(int nbColumn) {
        this.nbColumn = nbColumn;
    }

    public static String wrap(String sentence, int nbColumn) {
        return new WordWrapper(nbColumn).wrap(sentence);
    }

    private String wrap(String sentence) {
        if (isShortEnough(sentence)) {
            return sentence;
        }

        String leftSentence = sentence.substring(0, nbColumn);
        String rightSentence = sentence.substring(nbColumn);

        if (rightSentence.startsWith(" ")) {
            return joinTwoStrings(leftSentence, wrap(rightSentence.substring(1), nbColumn));
        }

        int indexLast = leftSentence.lastIndexOf(" ");
        if (indexLast != -1) {
            leftSentence = sentence.substring(0, indexLast);
            rightSentence = sentence.substring(indexLast);
            if (rightSentence.startsWith(" ")) {
                return joinTwoStrings(leftSentence, wrap(rightSentence.substring(1), nbColumn));
            }
        }

        return joinTwoStrings(leftSentence, wrap(rightSentence, nbColumn));
    }

    private String joinTwoStrings(String leftSentence, String wrap) {
        return leftSentence + BREAK + wrap;
    }

    private boolean isShortEnough(String sentence) {
        return sentence == null || sentence.length() <= nbColumn;
    }
}

package com.example.dictionary;

import java.util.List;

public class WordResponse {
    public String word;
    public List<Meaning> meanings;

    public static class Meaning {
        public String partOfSpeech;
        public List<Definition> definitions;

        public static class Definition {
            public String definition;
        }
    }
}

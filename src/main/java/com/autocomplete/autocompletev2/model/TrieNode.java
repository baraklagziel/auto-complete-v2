package com.autocomplete.autocompletev2.model;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isWordEnd;

    public TrieNode() {
        children = new HashMap<>();
        isWordEnd = false;
    }
}


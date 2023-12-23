package com.autocomplete.autocompletev2.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class Trie {
    private final TrieNode root = new TrieNode();

    // Inserts a word into the trie in lowercase.
    public void insert(String word) {
        TrieNode current = root;
        word = word.toLowerCase(); // Convert word to lowercase
        for (char l : word.toCharArray()) {
            current = current.children.computeIfAbsent(l, c -> new TrieNode());
        }
        current.isWordEnd = true;
    }

    // Finds all words in trie that start with the given prefix in lowercase.
    public List<String> findAllWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode current = root;
        prefix = prefix.toLowerCase(); // Convert prefix to lowercase
        for (char ch : prefix.toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return results;
            }
            current = node;
        }
        findAllWordsFromNode(current, prefix, results);
        return results;
    }

    // Helper method to recursively find all words from a given node.
    private void findAllWordsFromNode(TrieNode node, String prefix, List<String> results) {
        if (node.isWordEnd) {
            results.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> child : node.children.entrySet()) {
            findAllWordsFromNode(child.getValue(), prefix + child.getKey(), results);
        }
    }
}
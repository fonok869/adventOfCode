package com.fmolnar.code.year2022.day13;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Day13Utils {

    static Boolean compareNodes(JsonNode n1, JsonNode n2) {

        if (n1.isNumber() && n2.isNumber()) {
            return n1 == n2 ? null : n1.shortValue() < n2.shortValue();
        }

        JsonNode leftNode = n1.deepCopy();
        JsonNode rightNode = n2.deepCopy();

        if (!leftNode.isArray()){
            leftNode = new ObjectMapper().createArrayNode().add(leftNode);
        }

        if (!rightNode.isArray()) {
            rightNode = new ObjectMapper().createArrayNode().add(rightNode);
        }

        for (int k = 0; k < Math.min(leftNode.size(), rightNode.size()); k++) {
            Boolean result = compareNodes(leftNode.get(k), rightNode.get(k));
            if (result != null) {
                return result;
            }
        }

        if (leftNode.size() < rightNode.size()) {
            return true;
        }

        if (leftNode.size() > rightNode.size()) {
            return false;
        }

        return null;
    }
}

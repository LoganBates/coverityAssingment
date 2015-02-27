package com.loganabates.calculator;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * A special node subclass designed to handle let expressions. Contains the functions necessary to parse and replace
 * variable names and values.
 */
public class LetNode extends Node {

    /**
     * Calls the superclass constructor.
     */
    public LetNode() {
        super();
    }

    @Override
    public int getValue() {
        return 0;
    }

    /**
     * This function is used to help mark where the 3-tuple argument of a let function should be split. It replaces all
     * separating "," characters with "#" which are then used to split the string in the resolveLet() function.
     * @param tuple A 3-tuple to have it's splitting comas marked.
     * @return A 3-tuple with "#" indicating where it should be split.
     */
    public static String markSplits(String tuple) {
        int state = 0;
        char[] tupleArray = tuple.toCharArray();
        for (int i = 1; i < tuple.length() - 1; i++) {
            if (tupleArray[i] == '(') {
                state++;
            } else if (tupleArray[i] == ')') {
                state--;
            } else if (tupleArray[i] == ',' && state == 0) {
                tupleArray[i] = '#';
            }
        }
        return new String(tupleArray).substring(1, tuple.length() - 1);
    }

    /**
     * This function will take the body of a let expression (its arguments) and return the internal node with the
     * desired substitutions in place.
     * @param input The inner arguments of a let expression.
     * @return The resultant expression with the let values substituted in.
     */
    public static String resolveLet(String input) {
        String[] letArray = markSplits(input).split("#");
        if (letArray[1].contains("(") && ExprTree.parseString(letArray[1]).get(0).equals("let")) {
            letArray[1] = resolveLet(ExprTree.parseString(letArray[1]).get(1));
        }
        return replaceStr(letArray[2], letArray[0], letArray[1]);
    }

    /**
     * A function that, given an input string, will tokenize the string in order to find the replaceable variables.
     * The second argument is the string to be replaced and the third argument is what you wish to replace it with.
     * @param input The string to be tokenized.
     * @param target - The string to be replaced in the input.
     * @param replacement - The replacement of the target
     * @return A tokenized input with the replacement character/string
     */
    public static String replaceStr(String input, String target, String replacement) {
        String delims = "(),";
        ArrayList<String> stringArray = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(input, delims, true);

        while (st.hasMoreElements()) {
            stringArray.add((String) st.nextElement());
        }

        for (String s : stringArray) {
            if (s.equals(target)) {
                sb.append(replacement);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
package calculator;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class LetNode extends Node {

    public LetNode() {
        super();
    }

    @Override
    public int getValue() {
        return 0;
    }

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

    public static String resolveLet(String input) {
        String[] letArray = markSplits(input).split("#");
        if (letArray[1].contains("(") && ExprTree.parseString(letArray[1]).get(0).equals("let")) {
            letArray[1] = resolveLet(ExprTree.parseString(letArray[1]).get(1));
        }
        return replaceChar(letArray[2], letArray[0], letArray[1]);
    }

    public static String replaceChar(String input, String target, String replacement) {
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
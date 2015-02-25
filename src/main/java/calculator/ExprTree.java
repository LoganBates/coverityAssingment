package calculator;
import java.util.ArrayList;
import calculator.ArithNode.Operator;

public class ExprTree {
    private static Node root;

    public static void main(String args[]) {
        ExprTree tree = new ExprTree(args[0]);
        System.out.println(tree.getRoot().getValue());
    }

    public ExprTree(String inExpr) {
        root = buildTree(inExpr);
    }

    public Node getRoot() { return root; }

    /***
     * This function-given a calculator string input-will return an ArrayList of
     * two strings,where the first element (output.get(0)) is the first expressive
     * statement and the second element (output.get(1)), is the expression
     * contained within the first expression. This is used to parse the input into
     * different nodes on the expression tree.
     * function
     * @param input A simple calculator expression in String format
     * @return An ArrayList containing two elements: The first being the expression
     * 		   term on the left of the parenthesis and the second being what is contained
     * 		   within he parenthesis.
     */
    public static ArrayList<String> parseString(String input) {
        ArrayList<String> output = new ArrayList<>();
        int x = input.indexOf("(");
        output.add(input.substring(0, x));
        output.add(input.substring(x, input.length()));
        return output;
    }

    public static int getSplitTupleIndex(String tuple) {
        int state = 0;
        int tupleSplitIndex = 0;
        //ignore first and last parenthesis
        for (int i = 1; i < tuple.length()-1; i++) {
            if (tuple.toCharArray()[i] == '(') {
                state++;
            } else if (tuple.toCharArray()[i] == ')') {
                state--;
            } else if (tuple.toCharArray()[i] == ',' && state == 0) {
                tupleSplitIndex = i;
                break;
            }
        }
        return tupleSplitIndex;
    }

    public static String getLeftNode(String input) {
        String returnString;
        int splitIndex = getSplitTupleIndex(input);
        returnString = (input.substring(1, splitIndex));
        return returnString;
    }

    public static String getRightNode(String input) {
        String returnString;
        int splitIndex = getSplitTupleIndex(input);
        returnString = (input.substring(splitIndex+1, input.length()-1));
        return returnString;
    }

    /**
     * Checks if the inputed string can be converted into a valid integer.
     * @param input - A string that may contain only numerics/alphabets or a combination of the two.
     * @return - True if can be converted into valid int. False if not.
     */
    public static boolean isNumber(String input) {
        try {
            int i = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Node buildTree(String inExpr) {
        inExpr = inExpr.replaceAll("\\s+","").toLowerCase();
        if (isNumber(inExpr)) {
            return new TermNode(inExpr);
        } else {
            String nodeType = parseString(inExpr).get(0);
            String innerArgs = parseString(inExpr).get(1);
            switch (nodeType) {
                case "mult":
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.MULT);
                case "div":
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.DIV);
                case "add":
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.ADD);
                case "subt":
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.SUBT);
                case "let":
                    return new LetNode(inExpr);
                default:
                        System.out.println("Invalid Node Type: " + inExpr);
            }
            return null;
        }
    }
}

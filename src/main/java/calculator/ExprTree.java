package calculator;

import calculator.ArithNode.Operator;

import java.util.ArrayList;

/**
 * The ExprTree class holds methods that are used to build an expression tree. The main function is called here. A tree
 * is built with the buildTree(String input) function.
 */
public class ExprTree {
    private static Node m_root;

    /**
     * Builds a new tree by passing the arguments into the constructor. The output sum is printed to the console.
     * @param args The input string to be analyzed by the calculator.
     */
    public static void main(String args[]) {
        ExprTree tree = new ExprTree(args[0]);
        System.out.println(tree.getRoot().getValue());
    }

    /**
     * Generates a tree by taking in the input string and passing it into the build tree function. The root node of the
     * tree is stored in the tree's root variable.
     * @param inExpr String to be analyzed by the calculator.
     */
    public ExprTree(String inExpr) {
        m_root = buildTree(inExpr);
    }

    /**
     * Getter for the expression tree's root node.
     * @return The root of the tree.
     */
    public Node getRoot() { return m_root; }

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

    /**
     * The function takes in a tuple and returns the position of where the two values can be separated. This is a helper
     * function in order for getLeftNode(String input) or getRightNode(String input) to know where to parse a tuple in
     * order to extract left and ride nodes.
     * @param tuple A tuple of variables (as a string)
     * @return The numerical position of where the tuple should be split.
     */
    public static int getSplitTupleIndex(String tuple) {
        int state = 0;
        int tupleSplitIndex = 0;
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

    /**
     * Given a string tuple, this function will return the left value from the tuple.
     * @param input An input to be parsed into a left and ride node.
     * @return A string to be stored at the left node of the caller parent node.
     */
    public static String getLeftNode(String input) {
        String returnString;
        int splitIndex = getSplitTupleIndex(input);
        returnString = (input.substring(1, splitIndex));
        return returnString;
    }

    /**
     * Given a string tuple, this function will return the right value from the tuple.
     * @param input An input to be parsed into a left and ride node.
     * @return A string to be stored at the left node of the caller parent node.
     */
    public static String getRightNode(String input) {
        String returnString;
        int splitIndex = getSplitTupleIndex(input);
        returnString = (input.substring(splitIndex+1, input.length()-1));
        return returnString;
    }

    /**
     * Checks if the input string can be converted into a valid integer.
     * @param input - A string to be checked it it represents a number.
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

    /**
     * A function that returns the root node of tree that is built using the input string expression. The function
     * recurses until it reaches a terminal node, which is checked with the isNumber(String expr) function. An enum is
     * passed to each node in order to distinguish its behavior.
     * @param inExpr A string to be analyzed by the calculator.
     * @return The root node of the tree that is built from the function.
     */
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
                    return buildTree(LetNode.resolveLet(innerArgs));
                default:
                        System.out.println("Invalid Node Type: " + inExpr);
            }
            return null;
        }
    }
}

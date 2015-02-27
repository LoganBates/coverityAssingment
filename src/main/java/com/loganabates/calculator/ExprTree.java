package com.loganabates.calculator;

import com.loganabates.calculator.ArithNode.Operator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ExprTree class holds methods that are used to build an expression tree. The main function is called here. A tree
 * is built with the buildTree(String input) function.
 */
public class ExprTree {
    private static Node m_root;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    /**
     * Builds a new tree by passing the arguments into the constructor. The output sum is printed to the console.
     * @param args The input string to be analyzed by the calculator.
     */
    public static void main(String args[]) {

        //Create Log Files here
        try {
            CalcLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        setLoggerLevel(args[1]);
        LOGGER.info("Query: " + args[0]);
        ExprTree tree = new ExprTree(args[0]);
        System.out.println(tree.getRoot().getValue());
        LOGGER.info("Successful run with result: " + tree.getRoot().getValue());
    }

    private static void setLoggerLevel(String flag) {
        if (flag.equals("-debug")) LOGGER.setLevel(Level.CONFIG);
        else if (flag.equals("-info")) LOGGER.setLevel(Level.INFO);
        else if (flag.equals("-error")) LOGGER.setLevel(Level.SEVERE);
        else if (flag.equals("-all")) LOGGER.setLevel(Level.ALL);
        else {
            LOGGER.setLevel(Level.OFF);
            System.out.println("Invalid Logging level, defaulting to OFF");
        }

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
        LOGGER.config("Calling parseString function.");
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
        LOGGER.config("Calling getSplitTupleIndex function.");
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
     * Given a string 2-tuple, this function will return the left value from the tuple.
     * @param input An input to be parsed into a left and ride node.
     * @return A string to be stored at the left node of the caller parent node.
     */
    public static String getLeftNode(String input) {
        LOGGER.config("Calling getLeftNode function.");
        try {
            String returnString;
            int splitIndex = getSplitTupleIndex(input);
            returnString = (input.substring(1, splitIndex));
            return returnString;
        } catch (NullPointerException e) {
            LOGGER.severe("Invalid tuple passed in. Please rerun with valid tuple.");
            return null;
        }
    }

    /**
     * Given a string 2-tuple, this function will return the right value from the tuple.
     * @param input An input to be parsed into a left and ride node.
     * @return A string to be stored at the left node of the caller parent node.
     */
    public static String getRightNode(String input) {
        LOGGER.config("Calling getRightNode function.");
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
            LOGGER.config(input + " is not a number. Returning false.");
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
        // Eliminates white spaces
        inExpr = inExpr.replaceAll("\\s+","").toLowerCase();
        if (isNumber(inExpr)) {
            LOGGER.config("Reached terminal node and returning integer value for: " + inExpr);
            return new TermNode(inExpr);
        } else {
            String nodeType = parseString(inExpr).get(0);
            LOGGER.config("Node Type passed in: " + nodeType);
            String innerArgs = parseString(inExpr).get(1);
            LOGGER.config("Arguments for node type: " + innerArgs);
            switch (nodeType) {
                case "mult":
                    LOGGER.config("Reached mult node for: " + inExpr + ". Recursing down left and right nodes.");
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.MULT);
                case "div":
                    LOGGER.config("Reached div node for: " + inExpr + ". Recursing down left and right nodes.");
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.DIV);
                case "add":
                    LOGGER.config("Reached add node for: " + inExpr + ". Recursing down left and right nodes.");
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.ADD);
                case "subt":
                    LOGGER.config("Reached subt node for: " + inExpr + ". Recursing down left and right nodes.");
                    return new ArithNode(buildTree(getLeftNode(innerArgs)), buildTree(getRightNode(innerArgs)), Operator.SUBT);
                case "let":
                    LOGGER.config("Reached let node for: " + inExpr + ". Resolving let node in resolveLet function, passing in: " + innerArgs);
                    LOGGER.config("Calling getRightNode function.");
                    return buildTree(LetNode.resolveLet(innerArgs));
                default:
                    LOGGER.severe("Invalid Node Type with input: " + inExpr);
            }
            return null;
        }
    }


}

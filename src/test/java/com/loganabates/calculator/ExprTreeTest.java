package com.loganabates.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExprTreeTest {
    ExprTree tester = new ExprTree("add(2,3)");
    @Test
    public void testMain() throws Exception {
    }

    @Test
    public void testGetSplitTupleIndex() throws Exception {
        assertEquals("Calling getSplitTupleIndex('(2343,431)') should return 5", 5, tester.getSplitTupleIndex("(2343,431)"));
    }

    @Test
    public void testGetLeftNode() throws Exception {
        assertEquals("Calling getLeftNode() on (2,4) should return 2", "2", tester.getLeftNode("(2,4"));
        assertEquals("Calling getLeftNode() on (mult(24,5), 3) should return mult(24,5)", "mult(24,5)", tester.getLeftNode("(mult(24,5), 3)"));
    }

    @Test
    public void testGetRightNode() throws Exception {
        assertEquals("Calling getRightNode() on (2,4) should return 4", "4", tester.getRightNode("(2,4)"));
        assertEquals("Calling getRightNode() on (mult(24,5), 3) should return 3", "3", tester.getRightNode("(mult(24,5), 3)"));
    }

    @Test
    public void testIsNumber() throws Exception {
        assertEquals("Calling isNumber() on 'sausage' should return false", false, tester.isNumber("sausage"));
        assertEquals("Calling isNumber() on '34654654', should return true", true, tester.isNumber("34654654"));
    }

    public void testBuildTree() throws Exception {
    }
}
package calculator;

/**
 * ArithNode class that extends superclass Node. Constructs nodes that have arithmetic properties. (Multiply, Divide, Add,
 * etc....)
 */
class ArithNode extends Node {
	private Node m_left;
	private Node m_right; 
	private Operator m_typ;

    /**
     * An Enum that directs the behavior of a node.
     */
	public enum Operator {
		ADD,
		SUBT,
		DIV,
		MULT
    }

    /**
     * Constructor for an Arithmetic node. Each node is classified by an enum, which leads to a different evaluation in
     * the getValue() function. The left and right children nodes are passed in as arguments.
     * @param left The left child node of the arithmetic parent node.
     * @param right The right child node of the arithmetic parent node.
     * @param type An enum value which changes determines how the arithmetic node is treated. Use DIV,MULT,ADD, or SUBT.
     */
	public ArithNode(Node left, Node right, Operator type) {
		super();
		this.m_left = left;
		this.m_right = right;
		this.m_typ = type;
	}

    /**
     * A function that analyzes the value of children nodes based on the type of the parent node. For example, a MULT
     * parent node will multiply the values of the two children.
     * @return An integer value of the evaluated nodes.
     */
	@Override
	public int getValue() {
		switch (this.m_typ) {
			case MULT: return m_left.getValue() * m_right.getValue();
			case DIV: return m_left.getValue() / m_right.getValue();
			case ADD: return m_left.getValue() + m_right.getValue();
			case SUBT: return m_left.getValue() - m_right.getValue();
		}
		return 0;
	}

}

package calculator;

/**
 * A node subclass that represents a node that contains a terminal expression (i.e. a numerical value) and cannot be
 * further broken down into children nodes. The only function here returns the numerical value stored at the node.
 */
public class TermNode extends Node {
	private String m_value;
	
	public TermNode(String val) {
		 super();
		 this.m_value = val;
	}

    /**
     * Returns the numerical value of the terminal node.
     * @return Numerical value of the node.
     */
	@Override
	public int getValue() { return Integer.parseInt(m_value); }
}

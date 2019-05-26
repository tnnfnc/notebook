package it.tnnfnc.mynotebook;

/**
 * Acceptor interface allowing a book and its elements being visited by a
 * visitor.
 * 
 * @author FT
 *
 */
public interface I_Acceptor {

	/**
	 * Doing an action hook. It is called every time the acceptor object is
	 * visited.
	 * 
	 * @param v
	 *            the visitor.
	 */
	void accept(I_BookVisitor v);

}

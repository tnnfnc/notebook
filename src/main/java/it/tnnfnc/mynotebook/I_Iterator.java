package it.tnnfnc.mynotebook;

/**
 * Iterate over a book's elements.
 * 
 * @author FT
 *
 */
public interface I_Iterator<E> {
	/**
	 * There is another element.
	 * 
	 * @return true if there is a next.
	 */
	public boolean hasNext();

	/**
	 * Return the next element.
	 * 
	 * @return the next element.
	 */
	public E next();
}

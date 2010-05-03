package it.sauronsoftware.junique;

/**
 * This exception is thrown when an ID cannot be locked since it is currently
 * already locked by another JUnique caller.
 */
public class AlreadyLockedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID already locked.
	 */
	private String id;

	/**
	 * It builds the exception.
	 * 
	 * @param id
	 *            The ID already locked.
	 */
	AlreadyLockedException(String id) {
		super("Lock for ID \"" + id + "\" has already been taken");
		this.id = id;
	}

	/**
	 * It returns the ID already locked.
	 * 
	 * @return The ID already locked.
	 */
	public String getID() {
		return id;
	}

}

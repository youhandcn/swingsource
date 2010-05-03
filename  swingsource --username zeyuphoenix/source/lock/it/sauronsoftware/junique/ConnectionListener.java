package it.sauronsoftware.junique;

/**
 * This interface is used by {@link Connection} class to notify connection
 * related events.
 */
public interface ConnectionListener {

	/**
	 * This method is called when an incoming message is received.
	 * 
	 * @param connection
	 *            The source connection.
	 * @param message
	 *            The message received.
	 * @return An optional response (may be null).
	 */
	public String messageReceived(Connection connection, String message);

	/**
	 * This method is called to notify that the connection with the remote side
	 * has been closed.
	 * 
	 * @param connection
	 *            The source connection.
	 */
	public void connectionClosed(Connection connection);

}

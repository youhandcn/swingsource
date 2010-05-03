package it.sauronsoftware.junique;

/**
 * This interface describes how to handle messages received through an ID lock
 * channel.
 */
public interface MessageHandler {

	/**
	 * This method is called to request a message handling operation.
	 * 
	 * @param message
	 *            The incoming message.
	 * @return An optional response (may be null).
	 */
	public String handle(String message);

}

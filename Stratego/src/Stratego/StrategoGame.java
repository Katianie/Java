package Stratego;
/**
 * This interface provides a means for an application to
 * provide a custom response for game events triggered 
 * inside the game board manager and its panel and event
 * handlers. For example, the main application using these
 * classes can have a custom response for the game being
 * over.
 * 
 * @author McKilla Gorilla
 * @author Eddie O'Hagan
 */
public interface StrategoGame 
{
	public void gameOver();

}

package Stratego;

/**
 * This class represents an error condition where
 * someone attempts to create a grid using illegal
 * grid dimension, which could mean negative values
 * for the columns and rows, or where the calculated
 * cell sizes would differ from the piece sizes to
 * be fit inside the grid.
 * 
 * @author Eddie O'Hagan
 */
public class IllegalGridDimensionException extends Exception
{
	public IllegalGridDimensionException(String message)
	{
		super(message);
	}
}

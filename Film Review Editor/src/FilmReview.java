import java.io.File;
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * We'll use the following eight Genres in our reviews.
 * @author Eddie O'Hagan
 */
enum Genre { Action, Comedy, Documentary, Drama, Family, Fantasy, Horror, SciFi}; 

/**
 * List of different movie ratings
 */
enum Rating { G, PG, PG13, R, NC17, NR}

/**
 * This class represents a film review. It doesn't have much functionality, and
 * so mostly serves as a data store for a review. 
 * 
 * @author Eddie O'Hagan
 */
public class FilmReview implements Serializable
{	
	private String myFilmTitle;
	private int myYearReleased;
	private Genre myGenre;
	private Rating myRating;
	private String myReviewerName;
	private GregorianCalendar myDateOfReview;
	private int myNumStars;
	private String myReview;

	public FilmReview(String initFilmTitle, int initYearReleased, Genre initGenre, String initReviewerName, GregorianCalendar initDateOfReview, int initNumStars, String initReview, Rating initRating)
	{
		myFilmTitle = initFilmTitle;
		myYearReleased = initYearReleased;
		myGenre = initGenre;
		myRating = initRating;
		myReviewerName = initReviewerName;
		myDateOfReview = initDateOfReview;
		myNumStars = initNumStars;
		myReview = initReview;
	}

	// ACCESSOR METHODS
	public String getFilmTitle() 		
	{ 
		return myFilmTitle; 	
	}
	
	public int getYearReleased() 	
	{ 
		return myYearReleased;	
	}
	
	public Genre getGenre()
	{ 
		return myGenre;
	}
	
	public String getReviewerName()	
	{ 
		return myReviewerName;	
	}
	
	public GregorianCalendar getDateOfReview()
	{ 
		return myDateOfReview;	
	}
	
	public int getNumStars()
	{ 
		return myNumStars;
	}
	
	public String getReview()
	{ 
		return myReview;
	}
	
	public Rating getRating()
	{
		return myRating;
	}

	// MUTATOR METHODS
	public void setFilmTitle(String initFilmTitle)
	{
		myFilmTitle = initFilmTitle;
	}
	
	public void setYearReleased(int initYearReleased)
	{
		myYearReleased = initYearReleased;
	}
	
	public void setGenre(Genre initGenre)
	{
		myGenre = initGenre;
	}
	
	public void setReviewerName(String initReviewerName)
	{
		myReviewerName = initReviewerName;
	}
	
	public void setDateOfReview(GregorianCalendar initDateOfReview)
	{
		myDateOfReview = initDateOfReview;
	}
	
	public void setNumStars(int initNumStars)
	{
		myNumStars = initNumStars;
	}
	
	public void setReview(String initReview)
	{
		myReview = initReview;
	}
	
	public void setRating(Rating initRating)
	{
		myRating = initRating;
	}

	// FOR COMPARING LIKE OBJECTS
	public boolean equals(Object otherFilmReview)
	{
		return toString().equals(otherFilmReview.toString());
	}

	// FOR GETTING A TEXTUAL DESCRIPTIONOF THIS OBJECT
	public String toString()
	{
		return myFilmTitle + " (" + myYearReleased + ")";
	}
}
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;


public class FilmReviewEntryDialog extends JDialog
{
	//Dialog Controls
	private JLabel myFilmTitleLabel;
	private JTextField myFilmTitleTextField;
	private JLabel myYearReleasedLabel;
	private JLabel myYearReleasedDisplayLabel;
	private JLabel myGenreLabel;
	private JComboBox myGenreComboBox;
	private JLabel myReviewerNameLabel;
	private JTextField myReviewerNameTextField;
	private JLabel myReviewDateLabel;
	private JLabel myDateOfReviewLabel;
	private JLabel myRatingLabel;
	private JComboBox myRatingComboBox;
	private JLabel myNumStarsLabel;
	private JComboBox myNumStarsComboBox;
	private JTextArea myReviewTextArea;
	private JScrollPane myReviewScrollPane;
	private JButton myOkButton;
	private JButton myCancelButton;
	
	//These are Needed to Update the Frame
	private FilmReviewArchiver myMainGUI;
	private FilmReview myActiveReview;
	private int myYear;
	private int myMaxStars;
	
	//Event Handlers
	private OkHandler myOkHandler;
	private CancelHandler myCancelHandler;
	
	public FilmReviewEntryDialog(FilmReviewArchiver mainGUI, int year, int maxStars)
	{
		super(mainGUI);
		myMainGUI = mainGUI;
		myActiveReview = null;
		myYear = year;
		myMaxStars = maxStars;
		layoutGUI();
		
	}
	
	public FilmReviewEntryDialog(FilmReviewArchiver mainGUI, FilmReview activeReview, int maxStars)
	{
		super(mainGUI);
		myMainGUI = mainGUI;
		myActiveReview = activeReview;
		myYear = activeReview.getYearReleased();
		myMaxStars = maxStars;
		layoutGUI();
		
	}
	
	private void layoutGUI()
	{
		GridBagLayout gbl = new GridBagLayout();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		DefaultComboBoxModel numStarsComboBoxModel;
		Integer[] stars = new Integer[myMaxStars];
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		
		this.setLayout(gbl);
		
		myFilmTitleLabel = new JLabel("Film Title: ");
		myFilmTitleTextField = new JTextField(10);
		myYearReleasedLabel = new JLabel("Year Released: ");
		myYearReleasedDisplayLabel = new JLabel("" + myYear);
		myGenreLabel = new JLabel("Genre");
		myGenreComboBox = new JComboBox(Genre.values());
		myReviewerNameLabel = new JLabel("Reviewer: ");
		myReviewerNameTextField = new JTextField(10);
		myReviewDateLabel = new JLabel("Review Date: ");
		myDateOfReviewLabel = new JLabel("Date of Review: ");
		myRatingLabel = new JLabel("Rating:");
		myRatingComboBox = new JComboBox(Rating.values());
		myNumStarsLabel = new JLabel("Number of Stars: ");
		myNumStarsComboBox = new JComboBox();
		myReviewTextArea = new JTextArea(10, 30);
		myReviewTextArea.setWrapStyleWord(true);
		myReviewTextArea.setLineWrap(true);
		myReviewScrollPane = new JScrollPane(myReviewTextArea);
		myOkButton = new JButton("Ok");
		myOkHandler = new OkHandler();
		myCancelButton = new JButton("Cancel");
		myCancelHandler = new CancelHandler();
		
		addJComponentToContainerUsingGBL(myFilmTitleLabel, this, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(myFilmTitleTextField, this, 1, 0, 3, 1);
		addJComponentToContainerUsingGBL(myYearReleasedLabel, this, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(myYearReleasedDisplayLabel, this, 1, 1, 3, 1);
		addJComponentToContainerUsingGBL(myGenreLabel, this, 0, 2, 1, 1);
		addJComponentToContainerUsingGBL(myGenreComboBox, this, 1, 2, 3, 1);
		addJComponentToContainerUsingGBL(myReviewerNameLabel, this, 0, 3, 1, 1);
		addJComponentToContainerUsingGBL(myReviewerNameTextField, this, 1, 3, 3, 1);
		addJComponentToContainerUsingGBL(myReviewDateLabel, this, 0, 4, 1, 1);
		addJComponentToContainerUsingGBL(myDateOfReviewLabel, this, 1, 4, 3, 1);
		addJComponentToContainerUsingGBL(myRatingLabel, this, 0, 5, 1, 1);
		addJComponentToContainerUsingGBL(myRatingComboBox, this, 1, 5, 3, 1);
		addJComponentToContainerUsingGBL(myNumStarsLabel, this, 0, 6, 1, 1);
		addJComponentToContainerUsingGBL(myNumStarsComboBox, this, 1, 6, 3, 1);
		addJComponentToContainerUsingGBL(myReviewScrollPane, this, 0, 7, 4, 1);
		addJComponentToContainerUsingGBL(myOkButton, this, 1, 8, 1, 1);
		addJComponentToContainerUsingGBL(myCancelButton, this, 2, 8, 1, 1);
		
		//Registers the button listeners
		myOkButton.addActionListener(myOkHandler);
		myCancelButton.addActionListener(myCancelHandler);
		
		this.pack();
		this.setModal(true);
		width = this.getWidth();
		height = this.getHeight();
		x = (screenWidth - width) / 2;
		y = (screenHeight - height) / 2;
		this.setLocation(x, y);
		
		if(myActiveReview != null)
		{
			int index = -1;
			int ratingIndex = -1;
			Genre[] genres = Genre.values();
			Rating[] ratings = Rating.values();
			DateFormat df = DateFormat.getDateInstance();
			String dateText = df.format(myActiveReview.getDateOfReview().getTime());
			
			myFilmTitleTextField.setText(myActiveReview.getFilmTitle());
			myReviewerNameTextField.setText(myActiveReview.getReviewerName());
			myYearReleasedDisplayLabel.setText("" + myActiveReview.getYearReleased());
			
			for(int i = 0; (i < genres.length) && (index < 0); i++)
			{
				if(genres[i].equals(myActiveReview.getGenre()))
				{
					index = i;
				}
			}
			
			for(int i = 0; (i < ratings.length) && (ratingIndex < 0); i++)
			{
				if(ratings[i].equals(myActiveReview.getRating()))
				{
					ratingIndex = i;
				}
			}
			
			myGenreComboBox.setSelectedIndex(index);
			myRatingComboBox.setSelectedIndex(ratingIndex);
			myReviewerNameTextField.setText(myActiveReview.getReviewerName());
			myDateOfReviewLabel.setText(dateText);
			myReviewTextArea.setText(myActiveReview.getReview());
		}
		
		for(int i = 0; i < myMaxStars; i++)
		{
			stars[i] = i+1;
		}
		numStarsComboBoxModel = new DefaultComboBoxModel(stars);
		myNumStarsComboBox.setModel(numStarsComboBoxModel);
		
		if(myActiveReview != null)
		{
			myNumStarsComboBox.setSelectedItem(myActiveReview.getNumStars());
		}
		
	}
	
	public void addJComponentToContainerUsingGBL(JComponent component, Container contaner, int x, int y, int width, int height)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		contaner.add(component, gbc);
		
	}
	
	//Handles when user clicks Ok button
	protected class OkHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			//Get all the data from the controls and make sure the user
			//dident enter any illigal values
			GregorianCalendar dateOfReview = new GregorianCalendar();
			String title = myFilmTitleTextField.getText();
			String yearReleasedText = myYearReleasedDisplayLabel.getText();
			String reviewer;
			String review = myReviewTextArea.getText();
			Genre genre = (Genre)myGenreComboBox.getSelectedItem();
			Rating rating = (Rating)myRatingComboBox.getSelectedItem();
			int stars;
			int yearReleased;
			
			
			if(title.length() == 0)
			{
				JOptionPane.showMessageDialog(	FilmReviewEntryDialog.this,
						"You Must Enter a Film Title",
						"Error - Film Title Missing",
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			reviewer = myReviewerNameTextField.getText();
			if(reviewer.length() == 0)
			{
				JOptionPane.showMessageDialog(	FilmReviewEntryDialog.this,
						"You Must Enter a"+ reviewer+ "Name",
						"Error - Reviewer Name Missing",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try
			{
				yearReleased = Integer.parseInt(yearReleasedText);
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(	FilmReviewEntryDialog.this,
						"Invalid Data for year Released",
						"Error - Invalid Year Released",
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			stars = (Integer)myNumStarsComboBox.getSelectedItem();
			if(myActiveReview == null)
			{
				myActiveReview = new FilmReview(title,
												yearReleased,
												genre,
												reviewer,
												dateOfReview,
												stars,
												review,
												rating);
				
				myMainGUI.addReview(myActiveReview);
			}
			else
			{
				myActiveReview.setFilmTitle(title);
				myActiveReview.setYearReleased(yearReleased);
				myActiveReview.setGenre(genre);
				myActiveReview.setReviewerName(reviewer);
				myActiveReview.setDateOfReview(dateOfReview);
				myActiveReview.setNumStars(stars);
				myActiveReview.setReview(review);
				myActiveReview.setRating(rating);
			}
			
			setVisible(false);
		}
	}
	
	//Handles when user clicks Cancel button
	protected class CancelHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			setVisible(false);
		}
	}
	
	public static void main(String[] args)
	{
		FilmReviewEntryDialog theEntryDialog = new FilmReviewEntryDialog(null, 1997, 5);
		theEntryDialog.setVisible(true);
	}
	
}

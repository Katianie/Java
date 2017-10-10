import java.io.File;

import javax.swing.filechooser.FileFilter;


public class FRAFileFilter extends FileFilter
{
	public boolean accept(File file) 
	{
		return file.getName().endsWith(".fra");
	}

	public String getDescription() 
	{
		return ".fra";
	}
	

}

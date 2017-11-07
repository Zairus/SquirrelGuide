package zairus.squirrelguide;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class SGConfig
{
	public static Configuration configuration;
	
	public static boolean guideActive = true;
	
	public static void init(File cFile)
	{
		configuration = new Configuration(cFile);
		
		configuration.load();
		
		guideActive = configuration.getBoolean("guideActive", "MAIN", guideActive, "Sets wether output guide text is enabled or not");
		
		configuration.save();
	}
	
	public static void setActive(boolean active)
	{
		File cFile = new File("config" + File.separator + SGConstants.MOD_ID + ".cfg");
		
		configuration = new Configuration(cFile);
		
		configuration.load();
		
		Property prop = configuration.get("MAIN", "guideActive", guideActive);
		
		prop.set(active);
		guideActive = active;
		
		configuration.save();
	}
}

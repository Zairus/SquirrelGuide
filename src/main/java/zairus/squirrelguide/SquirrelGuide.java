package zairus.squirrelguide;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import zairus.squirrelguide.command.CommandSGOff;
import zairus.squirrelguide.command.CommandSGOn;
import zairus.squirrelguide.event.SGEventHandler;

@Mod(modid = SGConstants.MOD_ID, name = SGConstants.MOD_ID, version = SGConstants.MOD_VERSION)
public class SquirrelGuide
{
	@Mod.Instance(SGConstants.MOD_ID)
	public static SquirrelGuide instance;
	
	@SidedProxy(clientSide = SGConstants.CLIENT_PROXY, serverSide = SGConstants.COMMON_PROXY)
	public static SGProxy proxy;
	
	private static Logger logger;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		SGConfig.init(event.getSuggestedConfigurationFile());
		SquirrelGuide.proxy.preInit(event);
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
		SquirrelGuide.proxy.init(event);
		
		SGEventHandler eventHandler = new SGEventHandler();	
		
		MinecraftForge.EVENT_BUS.register(eventHandler);
    }
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		SquirrelGuide.proxy.postInit(event);
	}
	
	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSGOn());
		event.registerServerCommand(new CommandSGOff());
	}
	
	public static void logInfo(String message)
	{
		logger.info(message);
	}
}

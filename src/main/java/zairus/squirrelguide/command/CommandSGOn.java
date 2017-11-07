package zairus.squirrelguide.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import zairus.squirrelguide.SGConfig;

public class CommandSGOn extends CommandBase
{
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override
	public String getName()
	{
		return "sg_on";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		SGConfig.setActive(true);
		notifyCommandListener(sender, this, "Activated Squirrel Guide.", new Object[0]);
	}
}

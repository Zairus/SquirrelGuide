package zairus.squirrelguide.event;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import zairus.squirrelguide.SGConfig;
import zairus.squirrelguide.SGConstants;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = SGConstants.MOD_ID)
public class SGEventHandler
{
	private String lastName = "";
	
	@SubscribeEvent
	public void gameTick(TickEvent e)
	{
		if (SGConfig.guideActive && e.side == Side.CLIENT)
		{
			Minecraft mc = Minecraft.getMinecraft();
			World world = mc.world;
			RayTraceResult rtr = mc.objectMouseOver;
			
			if (world != null && rtr != null && rtr.typeOfHit == RayTraceResult.Type.BLOCK && rtr.getBlockPos() != null)
			{
				Block block = world.getBlockState(rtr.getBlockPos()).getBlock();
				
				String say = block.getLocalizedName();
				
				say = I18n.translateToFallback(say);
				
				if (I18n.canTranslate(say))
					say = I18n.translateToLocal(say);
				
				if (say.contains("bed"))
					say = "Bed";
				
				if (!say.contains(".") && say != lastName)
				{
					lastName = say;
					
					ITextComponent itextcomponent = new TextComponentTranslation(lastName);
					//ITextComponent itextcomponent1 = new TextComponentString("nar");
					//mc.world.getMinecraftServer().sendMessage(itextcomponent);
					//itextcomponent.appendSibling(itextcomponent1);
					//mc.player.sendChatMessage(say);
					//mc.player.sendStatusMessage(itextcomponent, true);
					//mc.player.sendMessage(itextcomponent);
					//mc.ingameGUI.getChatGUI().printChatMessage(itextcomponent);
					
					//Narrator narrator = Narrator.getNarrator();
					//narrator.clear();
					NarratorChatListener.INSTANCE.clear();
					mc.ingameGUI.addChatMessage(ChatType.SYSTEM, itextcomponent);
					//narrator.say(say);
				}
				else
				{
					lastName = say;
				}
			}
		}
	}
}

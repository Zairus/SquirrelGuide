package zairus.squirrelguide.event;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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
			EntityPlayer player = mc.player;
			
			if (world == null)
				return;
			
			if (player == null)
				return;
			
			float f = player.rotationPitch;
			float f1 = player.rotationYaw;
			double d0 = player.posX;
			double d1 = player.posY + (double)player.getEyeHeight();
			double d2 = player.posZ;
			Vec3d vec3d = new Vec3d(d0, d1, d2);
			float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
			float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
			float f4 = -MathHelper.cos(-f * 0.017453292F);
			float f5 = MathHelper.sin(-f * 0.017453292F);
			float f6 = f3 * f4;
			float f7 = f2 * f4;
			double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
			Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
			
			boolean stopOnLiquid = true;
			boolean ignoreBlockWithoutBoundingBox = false;
			boolean returnLastUncollidableBlock = false;
			
			RayTraceResult rtr = world.rayTraceBlocks(vec3d, vec3d1, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock); // mc.player.rayTrace(5.0D, 0.0F);
			RayTraceResult rtr_ent = mc.objectMouseOver;
			
			String say = "";
			ITextComponent itextcomponent = null;
			
			if (rtr != null)
			{
				if (rtr.typeOfHit == RayTraceResult.Type.BLOCK && rtr.getBlockPos() != null)
				{
					IBlockState blockState = world.getBlockState(rtr.getBlockPos());
					Block block = blockState.getBlock();
					
					ItemStack stack = new ItemStack(Item.getItemFromBlock(block), 1, block.getMetaFromState(blockState));
					
					List<String> dataList = stack.getTooltip((EntityPlayer)null, ITooltipFlag.TooltipFlags.NORMAL);
					
					if (dataList.size() > 0 )
					{
						say = dataList.get(0);
						
						if (say.length() > 1 && say.endsWith("r"))
						{
							say = say.substring(0, say.length() - 2);
						}
					}
					else
					{
						say = Item.getItemFromBlock(block).getItemStackDisplayName(new ItemStack(block)); //block.getLocalizedName();
						
						say = I18n.translateToFallback(say);
						
						if (I18n.canTranslate(say))
							say = I18n.translateToLocal(say);
					}
					
					if (blockState.getMaterial() == Material.WATER)
						say = "Water";
					
					if (blockState.getMaterial() == Material.LAVA)
						say = "Lava";
					
					if (block.getLocalizedName().contains("bed"))
						say = "Bed";
					
					itextcomponent = new TextComponentTranslation(say);
				}
			}
			
			if (rtr_ent != null)
			{
				if (rtr_ent.typeOfHit == RayTraceResult.Type.ENTITY && rtr_ent.entityHit != null)
				{
					Entity entity = rtr_ent.entityHit;
					
					itextcomponent = entity.getDisplayName();
					say = itextcomponent.getFormattedText();
				}
			}
			
			if (itextcomponent != null && !say.equals(lastName))
			{
				lastName = say;
				
				//ITextComponent itextcomponent = new TextComponentTranslation(lastName);
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
			}
			
			rtr = null;
			rtr_ent = null;
		}
	}
}

package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellPartPickupBlock extends SpellPart
{
    private final float AuraUse = 20f;

    @Override
    public String getSpellName()
    {
        return "RTUZVBUZTDR";
    }

    @Override
    public String getSpellId()
    {
        return "pickup_block";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getBlockLength();
        if(nr > 0)
        {
            Block block;
            BlockPos blockPos;
            IBlockState blockState;
            float blockHardness;
            EntityPlayer player = environ.getCaster();
            Vec3d castPos = environ.getCastPosition();
            float auraAdd;
            World world = player.getEntityWorld();

            for(int i = 0; i < nr; i++)
            {
                blockPos = childValues[0].getBlock(i);
                if(blockPos == null || world.isAirBlock(blockPos))
                    continue;
                blockState = world.getBlockState(blockPos);
                block = blockState.getBlock();
                blockHardness = block.getBlockHardness(blockState, world,blockPos);

                if(block.getHarvestLevel(blockState) <= environ.strength)
                {
                    auraAdd = (float) castPos.distanceTo(new Vec3d(blockPos));
                    if (environ.useAura((int) ((AuraUse + auraAdd) * blockHardness), environ.strength))
                    {

                        if (!world.isRemote && !world.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
                        {
                            java.util.List<ItemStack> items = block.getDrops(world, blockPos, blockState, 0);
                            float chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, world, blockPos, blockState, 0, 1.0f, false, player);

                            for (ItemStack item : items)
                            {
                                if (world.rand.nextFloat() <= chance)
                                {
                                    EntityItem entityItem = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), item);
                                    entityItem.setNoPickupDelay();

                                    int hook = net.minecraftforge.event.ForgeEventFactory.onItemPickup(entityItem, player, item);
                                    if (hook < 0) continue;
                                    if (player.inventory.addItemStackToInventory(item))
                                    {
                                        player.onItemPickup(entityItem, item.stackSize);

                                        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerItemPickupEvent(player, entityItem);
                                        world.playSound((EntityPlayer)null, entityItem.posX, entityItem.posY, entityItem.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                                    }
                                    if (item.stackSize > 0)
                                        block.spawnAsEntity(world, blockPos, item);
                                }
                            }
                            world.destroyBlock(blockPos, false);
                        }

                    }
                    else
                    {
                        throw new MissingAuraException(this);
                    }
                }
            }
        }
        return new SpellPartValue();
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.BLOCK;
        }
        return super.getChildType(childId);
    }
}

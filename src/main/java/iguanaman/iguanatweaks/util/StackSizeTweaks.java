package iguanaman.iguanatweaks.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import iguanaman.iguanatweaks.IguanaTweaks;
import iguanaman.iguanatweaks.config.IguanaConfig;
import iguanaman.iguanatweaks.config.IguanaWeightsConfig;

public class StackSizeTweaks {

    public static void init() {
        // BLOCKS
        if(IguanaConfig.blockStackSizeDividerMax > 1) {
            IguanaTweaks.log.info("Reducing block stack sizes");
            for(Object obj : Block.blockRegistry) {
                if(obj instanceof Block) {
                    Block block = (Block)obj;
                    Item item = null;
                    ItemStack stack = null;
                    try {
                        item = Item.getItemFromBlock(block);
                        stack = new ItemStack(item);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    if(item != null) {
                        float blockWeight = (float)IguanaWeightsConfig.getBlockWeight(block, false);

                        int size = 0;

                        if(blockWeight > 0d) {
                            size = Math.round((float)item.getItemStackLimit(stack) / ((float)IguanaConfig.blockStackSizeDividerMax * blockWeight));
                            if(size > item.getItemStackLimit(stack) / IguanaConfig.blockStackSizeDividerMin)
                                size = item.getItemStackLimit(stack) / IguanaConfig.blockStackSizeDividerMin;
                        } else {
                            size = Math.round((float)item.getItemStackLimit(stack) / (float)IguanaConfig.blockStackSizeDividerMin);
                        }

                        if(size < 1)
                            size = 1;
                        if(size > 64)
                            size = 64;
                        if(size < item.getItemStackLimit(stack)) {
                            if(IguanaConfig.logStackSizeChanges)
                                IguanaTweaks.log.info("Reducing stack size of block " + item.getUnlocalizedName() + " to " + size);
                            item.setMaxStackSize(size);
                        }
                    }
                }
            }
        }

        // ITEMS
        if(IguanaConfig.itemStackSizeDivider > 1) {
            IguanaTweaks.log.info("Reducing item stack sizes");
            for(Object obj : Item.itemRegistry) {
                if(obj instanceof Item) {
                    Item item = (Item)obj;
                    if(item != null) {
                        ItemStack stack = new ItemStack(item);
                        int size = item.getItemStackLimit(stack) / IguanaConfig.itemStackSizeDivider;
                        if(size < 1)
                            size = 1;
                        if(size > 64)
                            size = 64;
                        if(size < item.getItemStackLimit(stack)) {
                            if(IguanaConfig.logStackSizeChanges)
                                IguanaTweaks.log.info("Reducing stack size of item " + item.getUnlocalizedName() + " to " + size);
                            item.setMaxStackSize(size);
                        }
                    }
                }
            }
        }
    }

}
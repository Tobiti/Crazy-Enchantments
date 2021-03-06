package me.badbones69.crazyenchantments.api.objects;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockProcessInfo {
    
    private ItemStack item;
    private Block block;
    private List<ItemStack> drops;
    
    public BlockProcessInfo(ItemStack item, Block block) {
        this.item = item;
        this.block = block;
        this.drops = new ArrayList<>(block.getDrops(item));
    }
    
    public ItemStack getItem() {
        return item;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public List<ItemStack> getDrops() {
        return drops;
    }
    
}
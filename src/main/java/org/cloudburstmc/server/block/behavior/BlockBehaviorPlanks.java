package org.cloudburstmc.server.block.behavior;

import org.cloudburstmc.server.item.ItemTool;
import org.cloudburstmc.server.utils.BlockColor;

public class BlockBehaviorPlanks extends BlockBehaviorSolid {

    @Override
    public float getHardness() {
        return 2;
    }

    @Override
    public float getResistance() {
        return 15;
    }

    @Override
    public int getBurnChance() {
        return 5;
    }

    @Override
    public int getBurnAbility() {
        return 20;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public BlockColor getColor() {
        switch (getMeta() & 0x07) {
            default:
            case OAK:
                return BlockColor.WOOD_BLOCK_COLOR;
            case SPRUCE:
                return BlockColor.SPRUCE_BLOCK_COLOR;
            case BIRCH:
                return BlockColor.SAND_BLOCK_COLOR;
            case JUNGLE:
                return BlockColor.DIRT_BLOCK_COLOR;
            case ACACIA:
                return BlockColor.ORANGE_BLOCK_COLOR;
            case DARK_OAK:
                return BlockColor.BROWN_BLOCK_COLOR;
        }
    }
}
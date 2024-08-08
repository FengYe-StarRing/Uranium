package com.github.fengye.starring.uranium.api.event.game;

import com.github.fengye.starring.uranium.api.event.CancellableEvent;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BlockBBEvent extends CancellableEvent {
    private Block block;
    private BlockPos pos;
    private AxisAlignedBB axisAlignedBB;

    public BlockBBEvent(Block block,BlockPos pos,AxisAlignedBB axisAlignedBB) {
        this.block = block;
        this.pos = pos;
        this.axisAlignedBB = axisAlignedBB;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }
}

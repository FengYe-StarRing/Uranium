package com.github.fengye.starring.uranium.utils;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.impl.BlockBBEvent;
import com.github.fengye.starring.uranium.api.event.impl.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.listenable.module.impl.misc.Protocol;
import com.github.fengye.starring.uranium.utils.packet.UseUtils;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.theWorld;

public class ProtocolUtils implements Listenable {
    private static Protocol protocol = null;

    public static boolean isV189() {
        return getVersion() == ViaMCP.NATIVE_VERSION;
    }

    public static int getVersion() {
        return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
    }

    @Override
    public boolean handleEvents() {
        return canFix();
    }

    public static Versions fixToVersion() {
        if(!canFix()) {
            return Versions.v1_8;
        }
        return Versions.valueOf(Protocol.modeValue.getAsString());
    }

    @EventHandle(priority = Priority.MINIMUM)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet == null || event.isCancelled()) {
            return;
        }
        if(packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08 = (C08PacketPlayerBlockPlacement) packet;
            if(thanV1_9(fixToVersion())) {
                c08.facingX /= 16F;
                c08.facingY /= 16F;
                c08.facingZ /= 16F;
                if(UseUtils.isBlockPacket(c08)) {
                    try {
                        PacketWrapper useItem = PacketWrapper.create(29,null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                        useItem.write(Type.VAR_INT, 1);
                        useItem.sendToServer(Protocol1_8To1_9.class);
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    private static boolean canFix() {
        if(protocol == null) {
            protocol = (Protocol) Client.instance.moduleManager.getModule(Protocol.class);
        }
        return protocol.isEnabled();
    }

    public static boolean thanV1_9(Versions versions) {
        return versions.getVersion() >= Versions.v1_9.getVersion();
    }

    public static boolean thanV1_8(Versions versions) {
        return versions.getVersion() >= Versions.v1_8.getVersion();
    }

    @EventHandle
    private void onBlockBB(BlockBBEvent event) {
        if(theWorld == null) {
            return;
        }
        Block block = event.getBlock();
        BlockPos pos = event.getPos();
        IBlockState state = theWorld.getBlockState(pos);
        if (state.getBlock() != block) {
            return;
        }
        if (block instanceof BlockLilyPad) {
            if(thanV1_9(fixToVersion())) {
                // 1.9及以上
                event.setAxisAlignedBB(new AxisAlignedBB((double)pos.getX() + 0.0625D, (double)pos.getY() + 0.0D, (double)pos.getZ() + 0.0625D, (double)pos.getX() + 0.9375D, (double)pos.getY() + 0.09375D, (double)pos.getZ() + 0.9375D));
            }
        }
    }

    public enum Versions {
        v1_8(47,"1.8.x"),
        v1_9(107,"1.9");

        private final int VERSION;
        private final String NAME;

        Versions(int version,String name) {
            VERSION = version;
            NAME = name;
        }

        public int getVersion() {
            return VERSION;
        }

        public String getName() {
            return NAME;
        }
    }
}

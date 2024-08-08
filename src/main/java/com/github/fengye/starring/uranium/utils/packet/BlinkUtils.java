package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.game.packet.PacketEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketState;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.util.ArrayList;
import java.util.List;

public class BlinkUtils extends PacketUtils {
    private static final List<Data> dataList = new ArrayList<>();

    private static int getNewID() {
        return dataList.isEmpty() ? 0 : dataList.get(dataList.size() - 1).getId() + 1;
    }

    public static List<Data> getDataList() {
        return dataList;
    }

    private static Data getData(int id) {
        for (Data data : dataList) {
            if(data.id == id) {
                return data;
            }
        }
        return null;
    }

    private static Data getData(Integer id) {
        return getData(id.intValue());
    }

    public static void blink(List<Integer> ids) {
        for (Integer id : ids) {
            if(id == -1) {
                continue;
            }
            Data data = getData(id);
            if(data == null) {
                return;
            }
            Packet<?> packet = data.packet;
            switch (data.getState()) {
                case Send:
                    sendPacketNoEvent(packet);
                    break;
                case Recieve: {
                    ((Packet<INetHandlerPlayClient>)packet).processPacket(netHandler);
                    break;
                }
            }
            deleteData(data);
            data.blinked = true;
        }
        ids.clear();
        gc();
    }

    public static void deleteData(int id) {
        for (Data data : dataList) {
            if(data.id == id) {
                data.deleted = true;
            }
        }
    }

    public static void deleteData(Data data) {
        data.deleted = true;
    }

    public static void gc() {
        List<Integer> indexList = new ArrayList<>();
        int index = 0;
        for (Data data : dataList) {
            if(data.deleted) {
                indexList.add(index);
            }
            index++;
        }
        index = 0;
        for (Integer i : indexList) {
            int i1 = i - index;
            if(dataList.get(i1).deleted) {
                dataList.remove(i1);
                index++;
            }
        }
    }

    public static int collect(PacketEvent event) {
        if(event.isCancelled()) {
            return -1;
        }
        event.cancelEvent();
        return collect(event.getPacket(),event.getState());
    }

    public static int collect(Packet<?> packet,PacketState state) {
        Data data = new Data(getNewID(),packet,state);
        for (Data data1 : dataList) {
            if(data.packet.equals(data1.packet)) {
                if(data1.blinked) {
                    return -1;
                }
                return data1.id;
            }
        }
        dataList.add(data);
        return data.id;
    }

    public static class Data {
        private final int id;
        private Packet<?> packet;
        private PacketState state;
        private boolean deleted = false;
        private boolean blinked = false;

        public Data(int id,Packet<?> packet,PacketState state) {
            this.id = id;
            this.packet = packet;
            this.state = state;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(Packet<?> packet) {
            this.packet = packet;
        }

        public PacketState getState() {
            return state;
        }

        public void setState(PacketState state) {
            this.state = state;
        }

        public int getId() {
            return id;
        }
    }
}

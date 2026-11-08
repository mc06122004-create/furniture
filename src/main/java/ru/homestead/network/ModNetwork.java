package ru.homestead.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.homestead.Homestead;

public final class ModNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Homestead.MOD_ID, "main"))
            .networkProtocolVersion(() -> PROTOCOL)
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .simpleChannel();

    private static int id = 0;

    private ModNetwork() {}

    public static void register() {
        CHANNEL.messageBuilder(MediaActionPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(MediaActionPacket::decode)
                .encoder(MediaActionPacket::encode)
                .consumerMainThread(MediaActionPacket::handle)
                .add();
    }
}

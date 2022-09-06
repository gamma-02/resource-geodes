package gamma02.resourcegeodes.forge;

import dev.architectury.platform.forge.EventBuses;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ResourceGeodes.MOD_ID)
public class ResourceGeodesForge {
    public ResourceGeodesForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ResourceGeodes.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ResourceGeodes.init();
    }


}
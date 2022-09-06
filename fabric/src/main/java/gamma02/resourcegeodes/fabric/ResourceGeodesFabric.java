package gamma02.resourcegeodes.fabric;

import gamma02.resourcegeodes.ResourceGeodes;
import net.fabricmc.api.ModInitializer;

public class ResourceGeodesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceGeodes.init();
    }
}
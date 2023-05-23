package gamma02.resourcegeodes.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import static gamma02.resourcegeodes.ResourceGeodes.*;

public class RenderTypeStuffPlatformEditionImpl {

    @Environment(EnvType.CLIENT)
    public static void moreRenderTypeStuffAgh(){
        RenderType cutout = RenderType.cutout();

        ItemBlockRenderTypes.TYPE_BY_BLOCK.put(SMALL_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.TYPE_BY_BLOCK.put(MEDIUM_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.TYPE_BY_BLOCK.put(LARGE_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.TYPE_BY_BLOCK.put(DIAMOND_CLUSTER.get(), cutout);

    }
}

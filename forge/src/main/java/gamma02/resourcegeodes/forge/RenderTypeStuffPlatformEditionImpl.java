package gamma02.resourcegeodes.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import static gamma02.resourcegeodes.ResourceGeodes.*;

public class RenderTypeStuffPlatformEditionImpl {

    public static void moreRenderTypeStuffAgh() {
        RenderType cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(SMALL_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MEDIUM_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(LARGE_DIAMOND_BUD.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(DIAMOND_CLUSTER.get(), cutout);
    }
}

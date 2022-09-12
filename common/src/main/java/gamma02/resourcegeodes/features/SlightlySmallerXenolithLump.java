package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SlightlySmallerXenolithLump extends XenolithLumpFeature{
    public SlightlySmallerXenolithLump(Codec<NoneFeatureConfiguration> codec) {

        super(codec);
        MAX_RADIUS = 30;
        MIN_RADIUS = 25;
    }
}

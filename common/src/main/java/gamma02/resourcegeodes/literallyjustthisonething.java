package gamma02.resourcegeodes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public interface literallyjustthisonething {
    default boolean placeXenolithLump(BlockPos geodePos, FeaturePlaceContext<NoneFeatureConfiguration> ctx){
        return false;
    }
}

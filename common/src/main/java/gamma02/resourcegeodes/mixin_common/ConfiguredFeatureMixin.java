package gamma02.resourcegeodes.mixin_common;

import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.features.XenolithGeodeFeature;
import gamma02.resourcegeodes.features.XenolithLumpFeature;
import gamma02.resourcegeodes.literallyjustthisonething;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ConfiguredFeature.class)
public class ConfiguredFeatureMixin<FC extends FeatureConfiguration, F extends Feature<FC>> implements literallyjustthisonething {

    ThreadLocal<BlockPos> geodePos = ThreadLocal.withInitial(() -> BlockPos.ZERO);



    public boolean placeXenolithLump(BlockPos geodePos, FeaturePlaceContext<NoneFeatureConfiguration> ctx){
        this.geodePos.set(geodePos);
        return GeodesWG.CONFIGURED_XENOLITH_LUMP.value().place(ctx.level(), ctx.chunkGenerator(), ctx.random(), ctx.origin());
    }
}

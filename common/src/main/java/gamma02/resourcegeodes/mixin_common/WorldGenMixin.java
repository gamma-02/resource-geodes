package gamma02.resourcegeodes.mixin_common;

import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.features.Metaballs2D;
import gamma02.resourcegeodes.features.XenolithGeodeFeature;
import gamma02.resourcegeodes.features.XenolithLumpFeature;
import gamma02.resourcegeodes.worldGenRegionAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;

import static gamma02.resourcegeodes.features.XenolithGeodeFeature.distanceFrom;

@Mixin(WorldGenRegion.class)
public abstract class WorldGenMixin implements worldGenRegionAccessor {
    @Shadow @Final private int writeRadiusCutoff;

    @Override
    public int getWriteRadiusCutoff() {
        return this.writeRadiusCutoff;
    }


    //    private boolean recursiveStuff(Metaballs2D noise, int size, int iter, BlockPos geodePos, RandomSource random){
//        int x = random.nextIntBetweenInclusive(-size, size);
//        int z = random.nextIntBetweenInclusive(-size, size);
//
//        if(noise.inside(x, z)){
//            BlockPos pos = new BlockPos(x, geodePos.getY(), z).offset(geodePos.getX(), 5, geodePos.getZ());
//            if(distanceFrom(geodePos, pos) > 10) {
////                return GeodesWG.CONFIGURED_XENOLITH_LUMP.value().placeXenolithLump(geodePos, new FeaturePlaceContext<>(Optional.empty(), this.getLevel(), this.getLevel().getChunkSource().getGenerator(), random, pos, FeatureConfiguration.NONE));
//
//            }
//        }
//        iter++;
//
//        return recursiveStuff(noise, size, iter, geodePos, random);
//
//    }
}

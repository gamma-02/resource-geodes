package gamma02.resourcegeodes.mixin_common;

import gamma02.resourcegeodes.worldGenRegionAccessor;
import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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

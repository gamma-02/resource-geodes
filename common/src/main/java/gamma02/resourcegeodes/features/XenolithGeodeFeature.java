package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import org.jetbrains.annotations.NotNull;

public class XenolithGeodeFeature extends GeodeFeature {
    public XenolithGeodeFeature(Codec<GeodeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<GeodeConfiguration> featurePlaceContext) {

        int chance = 100;
        if(featurePlaceContext.random().nextIntBetweenInclusive(1, chance) == 1) {

            System.out.println("ORIGIN: " + featurePlaceContext.origin());

            final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            int size = featurePlaceContext.random().nextIntBetweenInclusive(25, 35);

            final Metaballs2D noise = new Metaballs2D(RandomSource.create(), 2, 5, 0.2f * size, size*2, size * 0.8f);

            for (int x = -size; x <= size; x++) {
                for (int z = -size; z <= size; z++) {

                    if (noise.inside(x, z)) {
                        mutablePos.setWithOffset(featurePlaceContext.origin().offset((size / featurePlaceContext.random().nextIntBetweenInclusive(4, 7)), 0, -(size / featurePlaceContext.random().nextIntBetweenInclusive(4, 7))), x, 3, z);
//                    this.setBlock(featurePlaceContext.level(), mutablePos, Blocks.AIR.defaultBlockState());
                        if (featurePlaceContext.random().nextIntBetweenInclusive(1, 60) == 10) {//gotta make it nice and rare
                            BlockPos lumpOrigin = new BlockPos(mutablePos);
                            placeXenolithLump(featurePlaceContext, featurePlaceContext.origin(), lumpOrigin, distanceFrom(featurePlaceContext.origin(), lumpOrigin));
                        }
                    }
                }
            }


            if (super.place(featurePlaceContext)) {
                System.out.println("PLACED XENOLITH: " + featurePlaceContext.origin());
                return true;
            }
            return false;
        }
        return false;
    }


    public void placeXenolithLump(FeaturePlaceContext<GeodeConfiguration> ctx, BlockPos geodeOrigin, BlockPos lumpOrigin, double distance){
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int size = Mth.floor((distance*-0.02857142856)+4);
        if(size < 0){//return if it is too far away, otherwise continue
            return;
        }
        final Metaballs3D noise = new Metaballs3D(RandomSource.create(), 2, 4, 0.12f * size, 0.3f * size,  size*0.2f);

        for (int x = -size; x <= size; x++)
        {
            for (int y = -size; y <= size; y++)
            {
                for (int z = -size; z <= size; z++)
                {
                    if (noise.inside(x, y, z))
                    {
                        mutablePos.setWithOffset(lumpOrigin, x, y, z);
                        setBlock(ctx.level(), mutablePos, ResourceGeodes.ROUGH_XENOLITH.get().defaultBlockState());
                    }
                }
            }
        }


    }
    //use the Pythagorean Theorm to calculate the distance between an origin blockpos and another blockpos
    public static double distanceFrom(BlockPos origin, BlockPos other){
        double distance1x = (origin.getX() - other.getX())*(origin.getX() - other.getX());//get the distance between x and square
        double distance1y = (origin.getY() - other.getY())*(origin.getY() - other.getY());// same for y
        double distance1z = (origin.getZ() - other.getZ())*(origin.getZ() - other.getZ());//and z

        return Math.sqrt(distance1x + distance1y + distance1z);//take the root of them all
    }
}

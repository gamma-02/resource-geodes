package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static gamma02.resourcegeodes.ResourceGeodes.GEODES;

public class XenolithGeodeFeature extends GeodeFeature {
    public static int MUTABLE_MAGIC_DETECTION_NUMBER = Integer.MAX_VALUE-1885399096;
    public static int z = 0;
    public static int currentX = 0;
    public static final int MAGIC_DETECTION_MUMBER = Integer.MAX_VALUE-1885399096;

    public FeaturePlaceContext<GeodeConfiguration> context;
    public int size = 0;

    public XenolithGeodeFeature(Codec<GeodeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<GeodeConfiguration> featurePlaceContext) {
        boolean bl = super.place(featurePlaceContext);
        System.out.println("PLACED DIAMOND GEODE: " + featurePlaceContext.origin());

        this.context = featurePlaceContext;


        if(ResourceGeodes.isPlaceCommandRegistered){
            ResourceGeodes.isPlaceCommandRegistered = false;
        }

        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        this.size = featurePlaceContext.random().nextIntBetweenInclusive(25, 45);

        final Metaballs2D noise = new Metaballs2D(RandomSource.create(), 2, 5, 0.2f * size, size*2, size * 0.8f);
        List<BlockPos> options = new ArrayList<>();
//        for (int x = -size; x <= size; x++) {
//            for (int z = -size; z <= size; z++) {
//
//                if (noise.inside(x, z)) {
//                    mutablePos.setWithOffset(featurePlaceContext.origin().offset((size / featurePlaceContext.random().nextIntBetweenInclusive(4, 7)), 0, -(size / featurePlaceContext.random().nextIntBetweenInclusive(4, 7))), x, 3, z);
////                        featurePlaceContext.level().getLevel().setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2);
//                    if (featurePlaceContext.random().nextIntBetweenInclusive(1, 60) == 10) {//gotta make it nice and rare
//                        BlockPos lumpOrigin = new BlockPos(mutablePos);
//                        placeXenolithLump(featurePlaceContext, featurePlaceContext.origin(), lumpOrigin, distanceFrom(featurePlaceContext.origin(), lumpOrigin));
//                    }
//                }
//            }
//        }
        ArrayList<BlockPos> toSet = new ArrayList<>();
//        MUTABLE_MAGIC_DETECTION_NUMBER += featurePlaceContext.origin().getY();
//        z = featurePlaceContext.origin().getZ();
//        currentX = featurePlaceContext.origin().getX();
//        setBlock(featurePlaceContext.level(), featurePlaceContext.origin().mutable().setY(MUTABLE_MAGIC_DETECTION_NUMBER), Blocks.AIR.defaultBlockState());
//        MUTABLE_MAGIC_DETECTION_NUMBER -= featurePlaceContext.origin().getY();
//        z = 0;
//        currentX = 0;
        GEODES.add(featurePlaceContext.origin());



        return bl;
    }



    //use the Pythagorean Theorm to calculate the distance between an origin blockpos and another blockpos
    public static double distanceFrom(BlockPos origin, BlockPos other){
        double distance1x = (origin.getX() - other.getX())*(origin.getX() - other.getX());//get the distance between x and square
        double distance1y = (origin.getY() - other.getY())*(origin.getY() - other.getY());// same for y
        double distance1z = (origin.getZ() - other.getZ())*(origin.getZ() - other.getZ());//and z

        return Math.sqrt(distance1x + distance1y + distance1z);//take the root of them all
    }

    public void recursiveStuff(FeaturePlaceContext<GeodeConfiguration> featurePlaceContext, Metaballs2D noise, int size, int iter, ArrayList<BlockPos> toSet){
        int x = featurePlaceContext.random().nextIntBetweenInclusive(-size, size);
        int z = featurePlaceContext.random().nextIntBetweenInclusive(-size, size);

        if(noise.inside(x, z)){
            BlockPos pos = new BlockPos(x, featurePlaceContext.origin().getY(), z).offset(featurePlaceContext.origin().getX(), 0, featurePlaceContext.origin().getZ());
            if(distanceFrom(featurePlaceContext.origin(), pos) > 10) {
//                p(featurePlaceContext, featurePlaceContext.origin(), pos, distanceFrom(featurePlaceContext.origin(), pos), toSet);
                return;
            }
        }
        iter++;

        recursiveStuff(featurePlaceContext, noise, size, iter, toSet);
    }
}

package gamma02.resourcegeodes.features;

import com.mojang.math.Vector3d;
import com.mojang.serialization.Codec;
import com.sun.management.GarbageCollectorMXBean;
import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import gamma02.resourcegeodes.blocks.SmoothXenolithBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.*;

import static gamma02.resourcegeodes.features.XenolithGeodeFeature.distanceFrom;
import static gamma02.resourcegeodes.ResourceGeodes.*;

public class XenolithLumpFeature extends Feature<NoneFeatureConfiguration> {


    public static int MAX_RADIUS = 0;

    public static int MIN_RADIUS = 0;


    double distance;

    public XenolithLumpFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
        MAX_RADIUS = 60;
        MIN_RADIUS = 55;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {

        if(ResourceGeodes.isPlaceCommandRegistered){
            ResourceGeodes.isPlaceCommandRegistered = false;
        }


        GEODES.add(featurePlaceContext.origin());

        Collections.sort(GEODES);
        int indexOf = GEODES.indexOf(featurePlaceContext.origin());

        double distanceLessThan = 3.14159265;
        if(indexOf > 1)
            distanceLessThan = distanceFrom(featurePlaceContext.origin(), GEODES.get(indexOf - 1));
        double distanceMoreThan = 3.14159265;

        if(indexOf < GEODES.size()-1)
            distanceMoreThan = distanceFrom(featurePlaceContext.origin(), GEODES.get(indexOf+1));

        if(distanceMoreThan == 3.14159265 || distanceLessThan == 3.14159265){
            return false;
        }


        BlockPos geodeOrigin = (distanceLessThan <= distanceMoreThan ? GEODES.get(indexOf-1) : GEODES.get(indexOf+1));


        BlockPos lumpOrigin = featurePlaceContext.origin();
        int biggerSize = Mth.randomBetweenInclusive(featurePlaceContext.random(), MIN_RADIUS, MAX_RADIUS);

        double distance = distanceFrom(geodeOrigin, lumpOrigin);
        this.distance = distance;

        if(distance > biggerSize ){
            return false;
        }





        double x1 = featurePlaceContext.origin().getX();
        double z1 = featurePlaceContext.origin().getZ();
        double y1 = featurePlaceContext.origin().getY();

        int amountBase = (Math.max(Mth.floor(distance + 1.1 * (8 - distance)), 2))*5;
        int amountRandom = Mth.randomBetweenInclusive(featurePlaceContext.random(), amountBase-2, amountBase);

        int radius = 15;
        boolean placedAll = false;



//            for(int x = -radius; x < radius; x++){
//                for(int y = -radius; y < radius; y++){
//                    for(int z = -radius; z < radius; z++){
//
//                        if()
//
//
//                    }
//                }
//            }

        List<Vector3d> poses = nextSkewedBoundedVec3DsNoDuplicates(-radius, radius, geodeOrigin, 2.0, featurePlaceContext.random(), amountRandom);
        for(Vector3d vec : poses){

            Vector3d vec2 = new Vector3d(0, 0, 0);
            vec2.set(vec);
            vec2.add(new Vector3d(x1, y1, z1));


            if(!placeLump(vec2, featurePlaceContext))
                placedAll = true;

        }




//        if(!placedAll && Mth.randomBetweenInclusive(featurePlaceContext.random(), 1, 20) == 10){
//            System.out.println("PLACED: " + lumpOrigin.getX() + " " + lumpOrigin.getY() + " " + lumpOrigin.getZ());
//            System.out.println("AMOUNT: " + amountRandom + " DISTANCE: " + distance + " SIZE: " + biggerSize);
//        }





        return placedAll;
    }




    public boolean alternatePlace(BlockPos lumpOrigin, double distance, FeaturePlaceContext<NoneFeatureConfiguration> ctx){
        this.distance = distance;
        return place(ctx);
    }

    double random(FeaturePlaceContext featurePlaceContext){return Mth.nextDouble(featurePlaceContext.random(), -0.5, 0.5);}
    double random(FeaturePlaceContext featurePlaceContext, double d, double e){return Mth.nextDouble(featurePlaceContext.random(), d, e);}

    boolean canPlace(BlockPos pos, @Nullable WorldGenRegion region){
        if(region != null) {
            int i = SectionPos.blockToSectionCoord(pos.getX());
            int j = SectionPos.blockToSectionCoord(pos.getZ());
            ChunkPos chunkPos = region.getCenter();
            int k = Math.abs(chunkPos.x - i);
            int l = Math.abs(chunkPos.z - j);
            return k <= region.getWriteRadiusCutoff() && l <= region.getWriteRadiusCutoff();
        }
        return false;
    }

    boolean placeLump(Vector3d lumpOrigin, FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext){
        float size = (float)(distance/-15)+3.5f;
        int size1 = (int)Math.floor(size);
        if(/*canPlace(new BlockPos(vec3dToi(lumpOrigin, true)), (WorldGenRegion) featurePlaceContext.level()) */featurePlaceContext.level().ensureCanWrite(new BlockPos(vec3dToi(lumpOrigin, true)))){
            boolean allPlaced = true;


//        final Metaballs3D noise = new Metaballs3D(RandomSource.create(), 1, 2, 0.12f * size, 0.3f * size,  size*0.2f);
            int amoutNonAir = 0;
            ArrayList<BlockPos> nameHere = new ArrayList<>();

            for (int x = -size1; x <= size; x++) {
                for (int y = -size1; y <= size; y++) {
                    for (int z = -size1; z <= size; z++) {
                        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
                        mutablePos.setWithOffset(vec3dToi(lumpOrigin, true), x, y, z);
                        double radIn = distanceFrom(lumpOrigin, mutablePos.immutable());
                        //double radOut = Math.abs(size1)+2- Mth.nextDouble(featurePlaceContext.random(), 1.0, size-1);

                        //make two metaballs:
                        //this is centered at, well, the center of the lump.
                        double metaballOne = 1 / (Math.sqrt(((-x) * (-x)) + ((-y) * (-y)) + ((-z) * (-z))));
                        //this one is offset a ***tiny*** amount
                        double metaballTwo = 1 / Math.sqrt(((-x + random(featurePlaceContext)) * (-x + random(featurePlaceContext))) + ((-y + random(featurePlaceContext)) * (-y + random(featurePlaceContext))) + ((-z + random(featurePlaceContext)) * (-z + random(featurePlaceContext))));

                        if ((radIn <= metaballTwo + metaballOne) && featurePlaceContext.level().ensureCanWrite(mutablePos.immutable())) {
//                            if (Mth.randomBetweenInclusive(featurePlaceContext.random(), 1, 20) == 10) {
//                                System.out.println("PLACED BLOCK: " + mutablePos);
//                            }
                            Block block = featurePlaceContext.level().getBlockState(mutablePos.immutable()).getBlock();

                            if(block != Blocks.AIR && block != Blocks.CAVE_AIR && block != Blocks.VOID_AIR) {

                                amoutNonAir++;
                            }
                            nameHere.add(mutablePos.immutable());
                        }
                    }
                }
            }
            if(amoutNonAir < 2){
                return false;
            }

            for(BlockPos pos : nameHere){
                setBlock(featurePlaceContext.level(), pos, ResourceGeodes.SMOOTH_XENOLITH.get().defaultBlockState().setValue(SmoothXenolithBlock.variant, featurePlaceContext.random().nextBoolean()));

            }

            return allPlaced;
        }
        return false;
    }

    /**
     * thanks to a random person on StackOverflow for this... you're a lifesaver :)
     * @param min minimum
     * @param max maximum
     * @param skew number to skew towards
     * @param bias bias of the generator
     * @param rand random
     * @return skewed random
     */
    public static double nextSkewedBoundedDouble(double min, double max, double skew, double bias, Random rand) {
        double range = max - min;
        double mid = min + range / 2.0;
        double unitGaussian = rand.nextGaussian();
        double biasFactor = Math.exp(bias);
        double retval = mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5));
        return retval;
    }

    public static List<Vector3d> nextSkewedBoundedVec3DsNoDuplicates(double min, double max, BlockPos skew, double bias, Random rand, int amount ){

        return nextSkewedBoundedVec3DsNoDuplicatesRecursive(min, max, skew, bias, rand, amount, new ArrayList<Vector3d>());

    }

    private static List<Vector3d> nextSkewedBoundedVec3DsNoDuplicatesRecursive(double min, double max, BlockPos skew, double bias, Random rand, int amount, List<Vector3d> poses){
        if(poses.size()+1 >= amount)
            return poses;

        Vector3d vec = randomVec3D(min, max, skew, bias, rand);




        if(!poses.contains(vec) && vec.y > -50)
            poses.add(vec);




        return nextSkewedBoundedVec3DsNoDuplicatesRecursive(min, max, skew, bias, rand, amount, poses);
    }


    public static Vector3d randomVec3D(double min, double max, BlockPos skew, double bias, Random rand){
        return new Vector3d(nextSkewedBoundedDouble(min, max, skew.getX(), bias, rand), nextSkewedBoundedDouble(min, max, skew.getY(), bias, rand), nextSkewedBoundedDouble(min, max, skew.getZ(), bias, rand));
    }

    public static Vec3i vec3dToi(Vector3d input, boolean floor){
        if(floor) {
            return new Vec3i(Math.floor(input.x), Math.floor(input.y), Math.floor(input.z));
        }else{
            return new Vec3i(Math.ceil(input.x), Math.ceil(input.y), Math.ceil(input.z));
        }
    }

}

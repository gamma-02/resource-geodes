package gamma02.resourcegeodes.features;

import com.mojang.math.Vector3d;
import com.mojang.serialization.Codec;
import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;

import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

import static gamma02.resourcegeodes.ResourceGeodes.GEODES;

public class XenolithGeodeFeature extends Feature<GeodeConfiguration> {


    public XenolithGeodeFeature(Codec<GeodeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<GeodeConfiguration> ctx) {
        boolean bl = true;
//        System.out.println("PLACED DIAMOND GEODE: " + ctx.origin());

//        if(Mth.randomBetweenInclusive(ctx.random(), 1, 5) == 3 && !ResourceGeodes.isPlaceCommandRegistered) {
//            return false;
//        }





        //shortcut for ctx.origin()
        BlockPos centerPos = ctx.origin();
        //offset it a little
        Vec3i random1 = random(ctx.random());
        BlockPos p1 = ctx.origin().offset(random1);
        //and again(for metaballs)
        Vec3i random2 = random(ctx.random());
        BlockPos p2 = ctx.origin().offset(random2);


        //for the placement of it
        double size = Mth.nextDouble(ctx.random(), 4, 6);
        size += maximum(random1.getY(), random1.getX(), random1.getZ(), random2.getX(), random2.getY(), random2.getZ());



        BlockPos.MutableBlockPos offsetPos = new BlockPos.MutableBlockPos();

        List<BlockPos> budPoses = new ArrayList<>();
        HashMap<BlockPos, Double> poses = new HashMap<>();

        int amountOfBlocksTouching = 0;

        //x iteration
        for(int x = (int) -size; x < size; x++){
            //y iteration
            for(int y = (int) -size; y < size; y++){
                //z iteration
                for(int z = (int)-size; z < size; z++){
                    BlockPos offset = new BlockPos(x, y, z);
                    offsetPos.setWithOffset(centerPos, offset);



                    double metaball1 = makeMetaball(BlockPos.ZERO, offset);
                    double metaball2 = makeMetaball(BlockPos.ZERO.offset(random1), offset);
                    double metaball3 = makeMetaball(BlockPos.ZERO.offset(random2), offset);
                    double metaballs = metaball1+metaball2+metaball3;
                    double radius = metaballs*size;

                    if(radius >= Collections.min(List.of(distanceFrom(centerPos, offsetPos), distanceFrom(p1, offsetPos)-1.2, distanceFrom(p2, offsetPos)-1.2))) {

                        if(ctx.level().getBlockState(offsetPos).getBlock() != Blocks.AIR || ctx.level().getBlockState(offsetPos).getBlock() != Blocks.CAVE_AIR || ctx.level().getBlockState(offsetPos).getBlock() != Blocks.VOID_AIR)
                            amountOfBlocksTouching++;

                        poses.put(offsetPos.immutable(), radius);
                    }






                }
            }
        }

        if(amountOfBlocksTouching < 10 && !ResourceGeodes.isPlaceCommandRegistered)
            return false;
        else if(ResourceGeodes.isPlaceCommandRegistered){
            ResourceGeodes.isPlaceCommandRegistered = false;
        }

        for(BlockPos pos : poses.keySet()) {
            double radius = poses.get(pos);
            offsetPos = pos.mutable();
            if (distanceFrom(centerPos, offsetPos) < radius - 3) {

                if (distanceFrom(centerPos, offsetPos) <= radius - 6) {
                    setBlock(ctx.level(), offsetPos, Blocks.AIR.defaultBlockState());
                    continue;
                }

                if (Mth.randomBetweenInclusive(ctx.random(), 1, 17) == 9) {
                    setBlock(ctx.level(), offsetPos, ctx.config().geodeBlockSettings.alternateInnerLayerProvider.getState(ctx.random(), offsetPos));
                    budPoses.add(offsetPos.immutable());
                } else {
                    setBlock(ctx.level(), offsetPos, ctx.config().geodeBlockSettings.innerLayerProvider.getState(ctx.random(), offsetPos));
                }
                continue;

            }

            setBlock(ctx.level(), offsetPos, ctx.config().geodeBlockSettings.outerLayerProvider.getState(ctx.random(), offsetPos));
        }

        Collections.shuffle(budPoses);

        while(budPoses.size()+1 > 10)
            budPoses.remove(budPoses.get(0));





        for(BlockPos pos : budPoses){
            for(Direction dir : Direction.values()){

//                if(ctx.level().getBlockState(pos.immutable().relative(dir.getOpposite())) == ctx.config().geodeBlockSettings.alternateInnerLayerProvider.getState(ctx.random(), pos)){
//                    int budLevel = Mth.randomBetweenInclusive(ctx.random(), 1, 5);
//                    setBlock(ctx.level(), pos, ResourceGeodes.budsByLevelAndDirection(budLevel, dir.getOpposite(), ctx.config().geodeBlockSettings.innerPlacements));
//                }
                if(ctx.level().getBlockState(pos.relative(dir)).getBlock() == Blocks.AIR){
                    setBlock(ctx.level(), pos.relative(dir), ResourceGeodes.budsByLevelAndDirection(Mth.randomBetweenInclusive(ctx.random(), 1, 5), dir.getOpposite(), ctx.config().geodeBlockSettings.innerPlacements));
                }


            }

        }








        if(ResourceGeodes.isPlaceCommandRegistered){
            ResourceGeodes.isPlaceCommandRegistered = false;
        }

//        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
//        this.size = Mth.randomBetweenInclusive(ctx.random(), 25, 45);
//
//        final Metaballs2D noise = new Metaballs2D(RandomSource.create(), 2, 5, 0.2f * size, size*2, size * 0.8f);
//        List<BlockPos> options = new ArrayList<>();
//        for (int x = -size; x <= size; x++) {
//            for (int z = -size; z <= size; z++) {
//
//                if (noise.inside(x, z)) {
//                    mutablePos.setWithOffset(ctx.origin().offset((size / Mth.randomBetweenInclusive(ctx.random(), 4, 7)), 0, -(size / Mth.randomBetweenInclusive(ctx.random(), 4, 7))), x, 3, z);
////                        ctx.level().getLevel().setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2);
//                    if (Mth.randomBetweenInclusive(ctx.random(), 1, 60) == 10) {//gotta make it nice and rare
//                        BlockPos lumpOrigin = new BlockPos(mutablePos);
//                        placeXenolithLump(ctx, ctx.origin(), lumpOrigin, distanceFrom(ctx.origin(), lumpOrigin));
//                    }
//                }
//            }
//        }
        ArrayList<BlockPos> toSet = new ArrayList<>();
//        MUTABLE_MAGIC_DETECTION_NUMBER += ctx.origin().getY();
//        z = ctx.origin().getZ();
//        currentX = ctx.origin().getX();
//        setBlock(ctx.level(), ctx.origin().mutable().setY(MUTABLE_MAGIC_DETECTION_NUMBER), Blocks.AIR.defaultBlockState());
//        MUTABLE_MAGIC_DETECTION_NUMBER -= ctx.origin().getY();
//        z = 0;
//        currentX = 0;
        GEODES.add(ctx.origin());



        return bl;
    }



    //use the Pythagorean Theorm to calculate the distance between an origin blockpos and another blockpos
    public static double distanceFrom(BlockPos origin, BlockPos other){
        double distance1x = (origin.getX() - other.getX())*(origin.getX() - other.getX());//get the distance between x and square
        double distance1y = (origin.getY() - other.getY())*(origin.getY() - other.getY());// same for y
        double distance1z = (origin.getZ() - other.getZ())*(origin.getZ() - other.getZ());//and z

        return Math.sqrt(distance1x + distance1y + distance1z);//take the root of them all
    }
    public static double distanceFrom(Vector3d origin, BlockPos other){
        double distance1x = (origin.x - other.getX())*(origin.x - other.getX());//get the distance between x and square
        double distance1y = (origin.y - other.getY())*(origin.y - other.getY());// same for y
        double distance1z = (origin.z - other.getZ())*(origin.z - other.getZ());//and z

        return Math.sqrt(distance1x + distance1y + distance1z);//take the root of them all
    }



    public Vec3i random(Random random){
        return new Vec3i(Mth.randomBetweenInclusive(random, -3, 3), Mth.randomBetweenInclusive(random, -3, 3), Mth.randomBetweenInclusive(random, -3, 3));

    }

    public int maximum(int... ints){
        for(int i = 0; i < ints.length; i++){
            ints[i] = Math.abs(ints[i]);
        }

        return Arrays.stream(ints).max().getAsInt();
    }

    public double makeMetaball(BlockPos pos, BlockPos offset){
        return 1/Math.sqrt(((pos.getX()-offset.getX())*(pos.getX()-offset.getX()))+((pos.getY()-offset.getY())*(pos.getY()-offset.getY()))+((pos.getZ()-offset.getZ())*(pos.getZ()-offset.getZ())));
    }




}

package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import gamma02.resourcegeodes.blocks.SmoothXenolithBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static gamma02.resourcegeodes.features.XenolithGeodeFeature.distanceFrom;
import static gamma02.resourcegeodes.ResourceGeodes.*;

public class XenolithLumpFeature extends Feature<NoneFeatureConfiguration> {



    double distance;

    public XenolithLumpFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {


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


        BlockPos geodeOrigin = (distanceLessThan < distanceMoreThan ? GEODES.get(indexOf-1) : GEODES.get(indexOf+1));


        BlockPos lumpOrigin = featurePlaceContext.origin();
        int biggerSize = featurePlaceContext.random().nextIntBetweenInclusive(35, 50);

        if(featurePlaceContext.origin().getY() <= -60 || (distanceLessThan < distanceMoreThan ? distanceLessThan > biggerSize : distanceMoreThan > biggerSize)){
            return false;
        }
        double distance = distanceFrom(geodeOrigin, lumpOrigin);


        double x1 = featurePlaceContext.origin().getX();
        double z1 = featurePlaceContext.origin().getZ();
        double y1 = featurePlaceContext.origin().getY();
//        double metaballOne1 = 1/(Math.sqrt(((-x1)*(0-x1))+((0-y1)*(0-y1))+((0-z1)*(0-z1))));
//        //this one is offset by a maximum of four
//        double metaballTwo1 = 1/Math.sqrt(((-x1+random(featurePlaceContext, -4, 4))*(-x1+random(featurePlaceContext, -4, 4)))+((-y1+random(featurePlaceContext, -4, 4))*(-y1+random(featurePlaceContext, -4, 4)))+((-z1+random(featurePlaceContext, -4, 4))*(-z1+random(featurePlaceContext, -4, 4))));



        float size = (float)(distance/-15)+3.5f;
        int size1 = (int)Math.floor(size);


//        final Metaballs3D noise = new Metaballs3D(RandomSource.create(), 1, 2, 0.12f * size, 0.3f * size,  size*0.2f);

        for (int x = -size1; x <= size; x++)
        {
            for (int y = -size1; y <= size; y++)
            {
                for (int z = -size1; z <= size; z++)
                {
                    BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
                    mutablePos.setWithOffset(lumpOrigin, x, y, z);
                    double radIn = distanceFrom(lumpOrigin, mutablePos.immutable());
                    double radOut = Math.abs(size1)+2- Mth.nextDouble(featurePlaceContext.random(), 1.0, size-1);

                    //make two metaballs:
                    //this is centered at, well, the center of the lump.
                    double metaballOne = 1/(Math.sqrt(((-x)*(0-x))+((0-y)*(0-y))+((0-z)*(0-z))));
                    //this one is offset a ***tiny*** amount
                    double metaballTwo = 1/Math.sqrt(((-x+random(featurePlaceContext))*(-x+random(featurePlaceContext)))+((-y+random(featurePlaceContext))*(-y+random(featurePlaceContext)))+((-z+random(featurePlaceContext))*(-z+random(featurePlaceContext))));

                    if ((radIn <= metaballTwo+metaballOne) && mutablePos.getY() >= -60)
                    {
                        setBlock(featurePlaceContext.level(), mutablePos, ResourceGeodes.SMOOTH_XENOLITH.get().defaultBlockState().setValue(SmoothXenolithBlock.variant, featurePlaceContext.random().nextBoolean()));
                    }
                }
            }
        }

        return true;
    }




    public boolean alternatePlace(BlockPos lumpOrigin, double distance, FeaturePlaceContext<NoneFeatureConfiguration> ctx){
        this.distance = distance;
        return place(ctx);
    }

    double random(FeaturePlaceContext featurePlaceContext){return Mth.nextDouble(featurePlaceContext.random(), -0.5, 0.5);}
    double random(FeaturePlaceContext featurePlaceContext, double d, double e){return Mth.nextDouble(featurePlaceContext.random(), d, e);}

}

package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LumpFeatureConfiguration implements FeatureConfiguration {
    public static Codec<LumpFeatureConfiguration> CODEC = RecordCodecBuilder.create((instance) ->{
        return instance.group(
                Codec.INT.fieldOf("x").forGetter((codec) -> {return ((LumpFeatureConfiguration)codec).x;}),
                Codec.INT.fieldOf("y").forGetter((codec) -> {return ((LumpFeatureConfiguration)codec).y;}),
                Codec.INT.fieldOf("z").forGetter((codec) -> {return ((LumpFeatureConfiguration)codec).z;}),
                Codec.DOUBLE.fieldOf("distance").forGetter((codec) -> {return ((LumpFeatureConfiguration)codec).distance;})).
                apply(instance, LumpFeatureConfiguration::new);

    } );
    int x;
    int y;
    int z;

    double distance;

    public LumpFeatureConfiguration(int x, int y, int z, double distance){
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
    }
}

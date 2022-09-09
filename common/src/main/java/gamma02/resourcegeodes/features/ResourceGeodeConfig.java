package gamma02.resourcegeodes.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ResourceGeodeConfig implements FeatureConfiguration {

     public final GeodeBlockSettings geodeBlockSettings;
     public final GeodeLayerSettings geodeLayerSettings;

     public final double firstLayerDiff;
     public final double airLayerDiff;

     public final double minSize;
     public final double maxSize;

     public final int innerLayerPlacmentChance;

    public static final Codec<ResourceGeodeConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(GeodeBlockSettings.CODEC.fieldOf("blocks").forGetter((geodeConfiguration) -> {
            return geodeConfiguration.geodeBlockSettings;
        }), GeodeLayerSettings.CODEC.fieldOf("layers").forGetter((geodeConfiguration) -> {
            return geodeConfiguration.geodeLayerSettings;
        }), Codec.DOUBLE.fieldOf("firstLayerDiff").forGetter((config) -> {
            return config.firstLayerDiff;
        }), Codec.DOUBLE.fieldOf("airLayerDiff").forGetter((config) -> {
            return config.airLayerDiff;
        }), Codec.DOUBLE.fieldOf("minSize").forGetter((config) -> {
            return config.minSize;
        }), Codec.DOUBLE.fieldOf("maxSize").forGetter((config) -> {
            return config.maxSize;
        }), Codec.INT.fieldOf("innerLayerPlacmentChance").forGetter((config) ->{
            return config.innerLayerPlacmentChance;
        })).apply(instance, ResourceGeodeConfig::new);
    });


    public ResourceGeodeConfig(GeodeBlockSettings geodeBlockSettings, GeodeLayerSettings geodeLayerSettings, double firstLayer, double airLayer, double minSize, double maxSize, int innerLayerPlacmentChance) {
        this.geodeBlockSettings = geodeBlockSettings;
        this.geodeLayerSettings = geodeLayerSettings;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.airLayerDiff = airLayer;
        this.firstLayerDiff = firstLayer;
        this.innerLayerPlacmentChance = innerLayerPlacmentChance;
    }

}


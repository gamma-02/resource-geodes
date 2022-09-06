package gamma02.resourcegeodes;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static gamma02.resourcegeodes.ResourceGeodes.*;

public class GeodesWG {
    public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> CONFIGURED_XENOLITH_FEATURE = configure("configured_xenolith_feature", XENOLITH_FEATURE.get(), new GeodeConfiguration(
            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(ResourceGeodes.ROUGH_XENOLITH.get()), BlockStateProvider.simple(BUDDING_XENOLITH.get()), BlockStateProvider.simple(SMOOTH_XENOLITH.get()), BlockStateProvider.simple(SMOOTH_XENOLITH.get()), List.of(SMALL_DIAMOND_BUD.get().defaultBlockState(), MEDIUM_DIAMOND_BUD.get().defaultBlockState(), MEDIUM_DIAMOND_BUD.get().defaultBlockState(), DIAMOND_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
            new GeodeLayerSettings(0.5, 1.3, 2.2, 3.2),
            new GeodeCrackSettings(1d, 0.1, 1),
            0.35, 0.083, true,
            UniformInt.of(2, 3), UniformInt.of(2, 4), UniformInt.of(2, 3),
            -16, 16, 0.05, 1));

    public static final Holder<PlacedFeature> PLACED_XENOLITH_FEATURE = place("placed_xenolith_feature", CONFIGURED_XENOLITH_FEATURE, List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));


    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> configure(String id, F feature, FC featureConfiguration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, MOD_ID  + ":" + id, new ConfiguredFeature<>(feature, featureConfiguration));
    }
    public static Holder<PlacedFeature> place(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, MOD_ID + ":" + string, new PlacedFeature(Holder.hackyErase(holder), List.copyOf(list)));
    }

    public static void init(){

    }
}

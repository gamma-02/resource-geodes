package gamma02.resourcegeodes;

import gamma02.resourcegeodes.blocks.SmoothXenolithBlock;
import gamma02.resourcegeodes.features.SlightlySmallerXenolithLump;
import gamma02.resourcegeodes.features.XenolithLumpFeature;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static gamma02.resourcegeodes.ResourceGeodes.*;

public class GeodesWG {

    public static SimpleWeightedRandomList<BlockState> list = new SimpleWeightedRandomList.Builder<BlockState>().add(SMOOTH_XENOLITH.get().defaultBlockState(), 1).add(SMOOTH_XENOLITH.get().defaultBlockState().setValue(SmoothXenolithBlock.variant, true), 1).build();
    public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> CONFIGURED_XENOLITH_FEATURE = configure("configured_xenolith_feature", XENOLITH_FEATURE.get(), new GeodeConfiguration(
            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(ResourceGeodes.ROUGH_XENOLITH.get()), BlockStateProvider.simple(BUDDING_XENOLITH.get()), new WeightedStateProvider(list), BlockStateProvider.simple(SMOOTH_XENOLITH.get()), List.of(SMALL_DIAMOND_BUD.get().defaultBlockState(), MEDIUM_DIAMOND_BUD.get().defaultBlockState(), LARGE_DIAMOND_BUD.get().defaultBlockState(), DIAMOND_CLUSTER.get().defaultBlockState(), Blocks.AIR.defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
            new GeodeLayerSettings(0.5, 1.3, 2.2, 3.2),
            new GeodeCrackSettings(1d, 0.5, 0),
            0.35, 0.083, true,
            UniformInt.of(3, 3), UniformInt.of(2, 3), UniformInt.of(1, 2),
            -16, 16, 0.05, 1), true);

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, XenolithLumpFeature>> CONFIGURED_XENOLITH_LUMP = configure("configured_xenolith_lump", XENOLITH_LUMP_FEATURE.get(), FeatureConfiguration.NONE);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, SlightlySmallerXenolithLump>> SMALLER_CONFIGURED_XENOLITH_LUMP = configure("smaller_configured_xenolith_lump", SMALLER_XENOLITH_LUMP_FEATURE.get(), FeatureConfiguration.NONE);

    public static final Holder<PlacedFeature> PLACED_XENOLITH_FEATURE = place("placed_xenolith_feature", CONFIGURED_XENOLITH_FEATURE, List.of(RarityFilter.onAverageOnceEvery(80), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(10)), BiomeFilter.biome()));

    public static final Holder<PlacedFeature> PLACED_XENOLITH_LUMP = PlacementUtils.register("resourcegeodes:placed_xenolith_lump", CONFIGURED_XENOLITH_LUMP, List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(10))));
    public static final Holder<PlacedFeature> PLACED_XENOLITH_LUMPx2 = PlacementUtils.register("resourcegeodes:placed_xenolith_lump_2", CONFIGURED_XENOLITH_LUMP, List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(10))));
    public static final Holder<PlacedFeature> SMALLER_PLACED_XENOLITH_LUMP = PlacementUtils.register("resourcegeodes:smaller_placed_xenolith_lump", SMALLER_CONFIGURED_XENOLITH_LUMP, List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(10))));

    public static final Holder<PlacedFeature> SMALLER_PLACED_XENOLITH_LUMPx2 = PlacementUtils.register("resourcegeodes:smaller_placed_xenolith_lumpx2", SMALLER_CONFIGURED_XENOLITH_LUMP, List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(10)), RarityFilter.onAverageOnceEvery(4)));


    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, F>> configure(String id, F feature, FC featureConfiguration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, MOD_ID  + ":" + id, new ConfiguredFeature<>(feature, featureConfiguration));
    }
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> configure(String id, F feature, FC featureConfiguration, boolean bl) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, MOD_ID  + ":" + id, new ConfiguredFeature<>(feature, featureConfiguration));
    }
    public static Holder<PlacedFeature> place(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return PlacementUtils.register(string, holder, list);
    }

    public static void init(){

    }

    public static void init(MinecraftServer minecraftServer) {
    }
}

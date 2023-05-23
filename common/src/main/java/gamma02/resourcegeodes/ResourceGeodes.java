package gamma02.resourcegeodes;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import gamma02.resourcegeodes.blocks.BuddingXenolithBlock;
import gamma02.resourcegeodes.features.ResourceGeodeConfig;
import gamma02.resourcegeodes.features.SlightlySmallerXenolithLump;
import gamma02.resourcegeodes.features.XenolithLumpFeature;
import gamma02.resourcegeodes.blocks.SmoothXenolithBlock;
import gamma02.resourcegeodes.features.XenolithGeodeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static gamma02.resourcegeodes.RenderTypeStuffPlatformEdition.moreRenderTypeStuffAgh;
import static net.minecraft.world.level.block.Blocks.AMETHYST_CLUSTER;
import static net.minecraft.world.level.block.Blocks.OBSIDIAN;

public class ResourceGeodes {


    public static final String MOD_ID = "resourcegeodes";

    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    public static Registrar<Item> ITEMS = REGISTRIES.get().get(Registry.ITEM);
    public static Registrar<Block> BLOCKS = REGISTRIES.get().get(Registry.BLOCK);

    public static final RegistrySupplier<Block> DIAMOND_CLUSTER = BLOCKS.register(new ResourceLocation(MOD_ID, "diamond_cluster"), () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5f).lightLevel(arg -> 5)));
    public static final RegistrySupplier<Block> LARGE_DIAMOND_BUD = BLOCKS.register(new ResourceLocation(MOD_ID, "large_diamond_bud"), () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD).lightLevel(arg -> 4).noOcclusion()));
    public static final RegistrySupplier<Block> MEDIUM_DIAMOND_BUD = BLOCKS.register(new ResourceLocation(MOD_ID, "medium_diamond_bud"), () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD).lightLevel(arg -> 2).noOcclusion()));
    public static final RegistrySupplier<Block> SMALL_DIAMOND_BUD = BLOCKS.register(new ResourceLocation(MOD_ID, "small_diamond_bud"), () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(arg -> 1).noOcclusion()));

    public static final RegistrySupplier<Block> SMOOTH_XENOLITH = BLOCKS.register(new ResourceLocation(MOD_ID, "smooth_xenolith"), () -> new SmoothXenolithBlock(BlockBehaviour.Properties.copy(OBSIDIAN).strength(100.0f, 2400.0f)));
    public static final RegistrySupplier<Block> ROUGH_XENOLITH = BLOCKS.register(new ResourceLocation(MOD_ID, "rough_xenolith"), () -> new Block(BlockBehaviour.Properties.copy(OBSIDIAN)
));
    public static final RegistrySupplier<Block> BUDDING_XENOLITH = BLOCKS.register(new ResourceLocation(MOD_ID, "budding_xenolith"), () -> new BuddingXenolithBlock(BlockBehaviour.Properties.copy(OBSIDIAN).randomTicks()));

    public static final RegistrySupplier<Block> XENOLITH_TILE = BLOCKS.register(new ResourceLocation(MOD_ID, "xenolith_tile"), () -> new Block(BlockBehaviour.Properties.copy(OBSIDIAN).strength(100.0f, 2400.0f)));
    public static final RegistrySupplier<Block> XENOLITH_TILE_SLAB = BLOCKS.register(new ResourceLocation(MOD_ID, "xenolith_tile_slab"), () -> new SlabBlock(BlockBehaviour.Properties.copy(OBSIDIAN).strength(100.0f, 2400.0f)));
    public static final RegistrySupplier<Block> XENOLITH_TILE_STAIRS = BLOCKS.register(new ResourceLocation(MOD_ID, "xenolith_tile_stairs"), () -> new StairBlock(XENOLITH_TILE.get().defaultBlockState(), BlockBehaviour.Properties.copy(OBSIDIAN).strength(100.0f, 2400.0f)));
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(MOD_ID, Registry.FEATURE_REGISTRY);
    public static final Supplier<Feature<GeodeConfiguration>> XENOLITH_FEATURE = FEATURES.register(new ResourceLocation(MOD_ID, "xenolith_feature"), () -> new XenolithGeodeFeature(GeodeConfiguration.CODEC));

    public static final List<BlockPos> GEODES = new ArrayList<>();
    public static final Supplier<XenolithLumpFeature> XENOLITH_LUMP_FEATURE = FEATURES.register(new ResourceLocation(MOD_ID, "xenolith_lump_feature"), () -> new XenolithLumpFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<SlightlySmallerXenolithLump> SMALLER_XENOLITH_LUMP_FEATURE = FEATURES.register(new ResourceLocation(MOD_ID, "smaller_xenolith_lump_feature"), () -> new SlightlySmallerXenolithLump(NoneFeatureConfiguration.CODEC));


    public static final RegistrySupplier<Item> LARGE_BUD = ITEMS.register(new ResourceLocation(MOD_ID,"large_diamond_bud"), () -> new BlockItem(LARGE_DIAMOND_BUD.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistrySupplier<Item> MEDIUM_BUD = ITEMS.register(new ResourceLocation(MOD_ID,"medium_diamond_bud"), () -> new BlockItem(MEDIUM_DIAMOND_BUD.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistrySupplier<Item> SMALL_BUD = ITEMS.register(new ResourceLocation(MOD_ID, "small_diamond_bud"), () -> new BlockItem(SMALL_DIAMOND_BUD.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistrySupplier<Item> DIAMOND_CLUSTER_ITEM = ITEMS.register(new ResourceLocation(MOD_ID, "diamond_cluster"), () -> new BlockItem(DIAMOND_CLUSTER.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistrySupplier<Item> SMOOTH_XENOLITH_ITEM = ITEMS.register(new ResourceLocation(MOD_ID,"smooth_xenolith"), () -> new BlockItem(SMOOTH_XENOLITH.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> ROUGH_XENOLITH_ITEM = ITEMS.register(new ResourceLocation(MOD_ID,"rough_xenolith"), () -> new BlockItem(ROUGH_XENOLITH.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> XENOLITH_TILE_ITEM = ITEMS.register(new ResourceLocation(MOD_ID,"xenolith_tile"), () -> new BlockItem(XENOLITH_TILE.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> BUDDING_XENOLITH_ITEM = ITEMS.register(new ResourceLocation(MOD_ID,"budding_xenolith"), () -> new BlockItem(BUDDING_XENOLITH.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> XENOLITH_TILE_STAIRS_ITEM = ITEMS.register(new ResourceLocation(MOD_ID,"xenolith_tile_stairs"), () -> new BlockItem(XENOLITH_TILE_STAIRS.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> XENOLITH_TILE_SLAB_ITEM = ITEMS.register(new ResourceLocation(MOD_ID, "xenolith_tile_slab"), () -> new BlockItem(XENOLITH_TILE_SLAB.get(), new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));


    public static boolean isPlaceCommandRegistered = false;






    public static void init() {

        FEATURES.register();
        if(!Platform.isForge())
            LifecycleEvent.SETUP.register(GeodesWG::init);



        ClientLifecycleEvent.CLIENT_SETUP.register(instance -> moreRenderTypeStuffAgh());




        BiomeModifications.addProperties((ctx, mutable) ->{
//            String str = ctx.getKey().orElseGet(() -> new ResourceLocation("air")).toString();
//            if(ServerLevel..)
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, GeodesWG.PLACED_XENOLITH_FEATURE);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, GeodesWG.PLACED_XENOLITH_LUMP);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, GeodesWG.PLACED_XENOLITH_LUMPx2);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, GeodesWG.SMALLER_PLACED_XENOLITH_LUMP);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, GeodesWG.SMALLER_PLACED_XENOLITH_LUMPx2);


        } );




    }

    public static BlockState budsByLevelAndDirection(int state, Direction dir, List<BlockState> states){
        return switch (state) {
            case 1 ->
                    states.get(0).setValue(AmethystClusterBlock.FACING, dir.getOpposite());
            case 2 ->
                    states.get(1).setValue(AmethystClusterBlock.FACING, dir.getOpposite());
            case 3 ->
                    states.get(2).setValue(AmethystClusterBlock.FACING, dir.getOpposite());
            case 4 ->
                    states.get(3).setValue(AmethystClusterBlock.FACING, dir.getOpposite());
            default -> states.get(4);
        };
    }


}
package gamma02.resourcegeodes.blocks;

import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BuddingXenolithBlock extends Block {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingXenolithBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, RandomSource randomSource) {
        int buds = countBuds(blockState, serverLevel, blockPos);

        if (randomSource.nextInt(5 + buds*2) == 0) {
            Direction direction = DIRECTIONS[randomSource.nextInt(DIRECTIONS.length)];
            BlockPos blockPos2 = blockPos.relative(direction);
            BlockState blockState2 = serverLevel.getBlockState(blockPos2);
            Block block = null;
            if (canClusterGrowAtState(blockState2)) {
                block = ResourceGeodes.SMALL_DIAMOND_BUD.get();
            } else if (blockState2.is(ResourceGeodes.SMALL_DIAMOND_BUD.get()) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ResourceGeodes.MEDIUM_DIAMOND_BUD.get();
            } else if (blockState2.is(ResourceGeodes.MEDIUM_DIAMOND_BUD.get()) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ResourceGeodes.LARGE_DIAMOND_BUD.get();
            } else if (blockState2.is(ResourceGeodes.LARGE_DIAMOND_BUD.get()) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ResourceGeodes.DIAMOND_CLUSTER.get();
            }

            if (block != null) {
                BlockState blockState3 = (BlockState)((BlockState)block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)).setValue(AmethystClusterBlock.WATERLOGGED, blockState2.getFluidState().getType() == Fluids.WATER);
                serverLevel.setBlockAndUpdate(blockPos2, blockState3);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState blockState) {
        return blockState.isAir() || blockState.is(Blocks.WATER) && blockState.getFluidState().getAmount() == 8;
    }

    public int countBuds(BlockState state, ServerLevel level, BlockPos pos){
        int buds = 0;
        for(Direction dir : DIRECTIONS){
            if(level.getBlockState(pos.relative(dir)).getBlock() instanceof AmethystClusterBlock)
                buds++;
        }

        return buds;

    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        return super.getDrops(blockState, builder);
    }
}

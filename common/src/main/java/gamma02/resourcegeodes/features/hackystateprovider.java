package gamma02.resourcegeodes.features;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import java.util.Random;

public class hackystateprovider extends BlockStateProvider {

    BlockState state;

    public hackystateprovider(BlockState state){
        this.state = state;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return null;
    }

    @Override
    public BlockState getState(Random randomSource, BlockPos blockPos) {
        return null;
    }
}

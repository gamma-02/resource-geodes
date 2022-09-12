package gamma02.resourcegeodes.features;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

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
    public BlockState getState(RandomSource randomSource, BlockPos blockPos) {
        return null;
    }
}

package gamma02.resourcegeodes.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SmoothXenolithBlock extends Block {

    public static final BooleanProperty variant = BooleanProperty.create("variant");

    public SmoothXenolithBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState) this.defaultBlockState().setValue(variant, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(variant);
    }
}

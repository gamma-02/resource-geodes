package gamma02.resourcegeodes.mixin_common;

import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static gamma02.resourcegeodes.ResourceGeodes.MOD_ID;

@Mixin(PlaceCommand.class)
public class PlaceCommandMixin {

    @Inject(method = "placeFeature", at = @At("HEAD"))
    private static void mixinFeature(CommandSourceStack commandSourceStack, Holder<ConfiguredFeature<?, ?>> holder, BlockPos blockPos, CallbackInfoReturnable<Integer> cir){
        if(holder.equals(GeodesWG.CONFIGURED_XENOLITH_FEATURE)){
            ResourceGeodes.isPlaceCommandRegistered = true;
        }
    }
}

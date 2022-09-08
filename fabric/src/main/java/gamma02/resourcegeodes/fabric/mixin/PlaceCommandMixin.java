package gamma02.resourcegeodes.fabric.mixin;

import gamma02.resourcegeodes.GeodesWG;
import gamma02.resourcegeodes.ResourceGeodes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlaceCommand.class)
public class PlaceCommandMixin {

    @Inject(method = "placeFeature", at = @At("HEAD"))
    private static void mixinFeature(CommandSourceStack commandSourceStack, Holder<ConfiguredFeature<?, ?>> holder, BlockPos blockPos, CallbackInfoReturnable<Integer> cir){

        if(Objects.equals(Registry.FEATURE.getKey(holder.value().feature()), Registry.FEATURE.getKey(ResourceGeodes.XENOLITH_FEATURE.get()))){
            ResourceGeodes.isPlaceCommandRegistered = true;
        }
    }
}

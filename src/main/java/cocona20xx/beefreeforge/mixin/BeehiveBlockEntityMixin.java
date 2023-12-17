package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntityMixin extends BlockEntity {
    public BeehiveBlockEntityMixin(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Redirect(
            method = "releaseOccupant",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;isRaining()Z"
            )
    )
    private static boolean isRaining(Level world){
        if(BeeFreeForge.config.beesIgnoreWeather.get() && !BeeFreeForge.config.beesHurtFromRain.get()){
            return false;
        } else {
            return world.isRaining();
        }
    }

}



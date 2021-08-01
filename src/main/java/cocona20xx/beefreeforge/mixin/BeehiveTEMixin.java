package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BeehiveTileEntity.class)
public abstract class BeehiveTEMixin extends TileEntity implements ITickableTileEntity {
    public BeehiveTEMixin(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    private BlockPos hivePos;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constructInjector(CallbackInfo ci){
        hivePos = this.getBlockPos();
    }

    @Redirect(
            method = "releaseOccupant",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isRaining()Z"
            )
    )
    private boolean isRaining(World world){
        if(BeeFreeForge.config.beesIgnoreWeather.get()){
            if(BeeFreeForge.config.beesHurtFromRain.get()){
                return world.isRaining() && world.canSeeSky(hivePos.above());
            } else return false;
        } else {
            return world.isRaining();
        }
    }

}



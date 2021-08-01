package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(BeehiveTileEntity.class)
public abstract class BeehiveTEMixin extends TileEntity implements ITickableTileEntity {
    public BeehiveTEMixin(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
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
            return false;
        } else {
            return world.isRaining();
        }
    }

}



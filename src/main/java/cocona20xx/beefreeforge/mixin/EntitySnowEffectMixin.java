package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntitySnowEffectMixin {

    @Shadow public abstract BlockPos blockPosition();

    @Shadow public World level;

    @Inject(method = "isInRain", at = @At("RETURN"), cancellable = true)
    private void isInRainInjector(CallbackInfoReturnable<Boolean> cir){
        BlockPos blockPos = this.blockPosition();
        if(snowCheck(blockPos, this.level) && BeeFreeForge.config.wetterSnow.get()){
            cir.setReturnValue(true);
        }

    }

    private boolean snowCheck(BlockPos pos, World level){
        if(!level.isRaining()){
            return false;
        } else if(!level.canSeeSky(pos)){
            return false;
        } else if(level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY()){
            return false;
        } else {
            Biome biome = level.getBiome(pos);
            return biome.getPrecipitation() == Biome.RainType.SNOW;
        }
    }
}

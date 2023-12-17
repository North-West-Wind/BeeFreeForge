package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntitySnowEffectMixin {

    @Shadow public abstract BlockPos blockPosition();

    @Shadow public Level level;

    @Inject(method = "isInRain", at = @At("RETURN"), cancellable = true)
    private void isInRainInjector(CallbackInfoReturnable<Boolean> cir){
        BlockPos blockPos = this.blockPosition();
        if(snowCheck(blockPos, this.level) && BeeFreeForge.config.wetterSnow.get()){
            cir.setReturnValue(true);
        }

    }

    private boolean snowCheck(BlockPos pos, Level level){
        if(!level.isRaining()){
            return false;
        } else if(!level.canSeeSky(pos)){
            return false;
        } else if(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()){
            return false;
        } else {
            Holder<Biome> biome = level.getBiome(pos);
            return biome.value().getPrecipitation() == Biome.Precipitation.SNOW;
        }
    }
}

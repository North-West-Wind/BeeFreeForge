package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements IAngerable, IFlyingAnimal {
    protected BeeEntityMixin(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
    }

    @Inject(method = "aiStep()V", at = @At("HEAD"))
    private void addRainDamage(CallbackInfo ci){
        if (!this.level.isClientSide && this.isInWaterRainOrBubble() && BeeFreeForge.config.beesHurtFromRain.get()) {
            this.hurt(DamageSource.DROWN, 1.0F);
        }
    }

    @Redirect(
            method = "wantsToEnterHive",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z")
    )
    private boolean isRainingProxyBee(World world){
        if(BeeFreeForge.config.beesIgnoreWeather.get()){
            return false;
        } else {
            return world.isRaining();
        }
    }

    @Mixin(BeeEntity.PollinateGoal.class)
    private abstract static class PollinateGoalMixin{
        @Redirect(
                method = {"canBeeUse", "canBeeContinueToUse"},
                at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z")
        )
        private boolean isRainingPollinateGoal(World world){
            if(BeeFreeForge.config.beesIgnoreWeather.get()){
                return false;
            } else {
                return world.isRaining();
            }
        }
    }
}

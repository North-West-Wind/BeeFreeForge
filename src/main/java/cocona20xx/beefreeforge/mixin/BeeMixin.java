package cocona20xx.beefreeforge.mixin;

import cocona20xx.beefreeforge.BeeFreeForge;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal implements NeutralMob, FlyingAnimal {
    protected BeeMixin(EntityType<? extends Animal> p_i48568_1_, Level p_i48568_2_) {
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
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isRaining()Z")
    )
    private boolean isRainingProxyBee(Level level){
        if(BeeFreeForge.config.beesIgnoreWeather.get()){
            return false;
        } else {
            return level.isRaining();
        }
    }

    @Mixin(Bee.BeePollinateGoal.class)
    private abstract static class BeePollinateGoalMixin {
        @Redirect(
                method = {"canBeeUse", "canBeeContinueToUse"},
                at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isRaining()Z")
        )
        private boolean isRainingPollinateGoal(Level level){
            if(BeeFreeForge.config.beesIgnoreWeather.get()){
                return false;
            } else {
                return level.isRaining();
            }
        }
    }
}

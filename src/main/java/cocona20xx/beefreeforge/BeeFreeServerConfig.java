package cocona20xx.beefreeforge;

import cocona20xx.beefreeforge.databuddy.ConfigHelper;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.ForgeConfigSpec;

public class BeeFreeServerConfig {
    public ConfigHelper.ConfigObject<Boolean> wetterSnow;
    public ConfigHelper.ConfigObject<Boolean> beesIgnoreWeather;
    public ConfigHelper.ConfigObject<Boolean> beesHurtFromRain;

    public BeeFreeServerConfig(ForgeConfigSpec.Builder builder){
        builder.push("BeeFree-Forge Config");
        this.wetterSnow = ConfigHelper.defineObject(builder.comment("Makes snow count as rain for mobs that take damage from rain."), "wetsnow", Codec.BOOL, false);
        this.beesIgnoreWeather = ConfigHelper.defineObject(builder.comment("Bees will ignore weather when entering/leaving hive."), "ignorantbees", Codec.BOOL, true);
        this.beesHurtFromRain = ConfigHelper.defineObject(builder.comment("Bees will now take damage from rain, akin to Blazes. If ignorantbees is active, bees will not exit hive if the hive has a direct line of site to the sky."), "rainhurtsbees", Codec.BOOL, false);
        builder.pop();
    }

}

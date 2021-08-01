package cocona20xx.beefreeforge;

import cocona20xx.beefreeforge.databuddy.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class BeeFreeServerConfig {
    public ConfigHelper.ConfigValueListener<Boolean> wetterSnow;
    public ConfigHelper.ConfigValueListener<Boolean> beesIgnoreWeather;
    public ConfigHelper.ConfigValueListener<Boolean> beesHurtFromRain;

    public BeeFreeServerConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber){
        builder.push("BeeFree-Forge Config");
        this.wetterSnow = subscriber.subscribe(builder
            .comment("Makes snow count as rain for mobs that take damage from rain.")
            .define("wetsnow", false));
        this.beesIgnoreWeather = subscriber.subscribe(builder
            .comment("Bees will ignore weather when entering/leaving hive.")
            .define("ignorantbees", true)
        );
        this.beesHurtFromRain = subscriber.subscribe(builder
            .comment("Bees will now take damage from rain, akin to Blazes. If ignorantbees is active, bees will not exit hive if the hive has a direct line of site to the sky.")
            .define("rainhurtsbees", false)
        );
        builder.pop();
    }

}

package cocona20xx.beefreeforge;

import cocona20xx.beefreeforge.databuddy.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class BeeFreeServerConfig {
    public ConfigHelper.ConfigValueListener<Boolean> wetterSnow;
    public ConfigHelper.ConfigValueListener<Boolean> beesIgnoreWeather;

    public BeeFreeServerConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber){
        builder.push("BeeFree-Forge Config");
        this.wetterSnow = subscriber.subscribe(builder
            .comment("Wetter Snow")
            .define("wetsnow", true));
        this.beesIgnoreWeather = subscriber.subscribe(builder
            .comment("Bees Ignore Weather")
            .define("ignorantbees", true)
        );
        builder.pop();
    }

}

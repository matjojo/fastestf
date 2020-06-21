package matjojo.client;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class main implements ClientModInitializer {
	public static FConfig configData;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(FConfig.class, GsonConfigSerializer::new);
		configData = AutoConfig.getConfigHolder(FConfig.class).getConfig();
	}
}

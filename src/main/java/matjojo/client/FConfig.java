package matjojo.client;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.Arrays;
import java.util.List;

@Config(name = util.MOD_ID)
public class FConfig implements ConfigData {

	public String fText = "F";
	@ConfigEntry.Gui.Tooltip(count = 2)
	public List<String> fTextList = Arrays.asList("F", "Classic F moment", "rip", "f");
}

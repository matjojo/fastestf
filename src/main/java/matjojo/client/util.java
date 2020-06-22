package matjojo.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;
import java.util.Random;


public class util {

	public static final String MOD_ID = "fastestf";
	public static final Random random = new Random();

	/**
	 * Check if the passed message and type are part of a death message usable for died-player name checking.
	 * @param type The MessageType sent with the Text
	 * @param text The Text that is checked to be a usable death message
	 * @return false if the message is either of the wrong type or does not have the correct arguments
	 */
	public static boolean isUsableDeathMessage(MessageType type, Text text) {
		if (type != MessageType.SYSTEM) {
			return false;
		}
		if (!(text instanceof TranslatableText)) {
			return false;
		}
		TranslatableText translatableText = (TranslatableText) text;
		if (!translatableText.getKey().startsWith("death.")) {
			return false;
		}

		// there is one death message without arguments: "death.attack.badRespawnPoint.link"
		// for now we can just ignore that one, since we can't really check if that was not us.
		if (translatableText.getArgs().length == 0) {
			return false;
		}
		// this is always true, even in the message
		// "death.attack.message_too_long" which takes a list of arguments,
		// but that list is always a list of 1 Literaltext,
		// in ServerPlayerEntity:480.
		//noinspection RedundantIfStatement
		if (!(translatableText.getArgs()[0] instanceof LiteralText)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the passed death message Text is about another player dying.
	 * @param deathMessage The text that contains a usable (see isUsableDeathMessage) death message
	 * @return true if the player that died was not this client's player.
	 */
	@Environment(EnvType.CLIENT)
	public static boolean deathMessageIsAboutOtherPlayer(TranslatableText deathMessage) {
		ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().player;
		if (clientPlayerEntity == null) {
			return false;
		}
		if (!(clientPlayerEntity.getName() instanceof LiteralText)) {
			return false;
		}
		LiteralText diedPlayerName = (LiteralText) deathMessage.getArgs()[0];
		LiteralText currentPlayerName = (LiteralText) clientPlayerEntity.getName();
		//noinspection RedundantIfStatement
		if (diedPlayerName.getRawString().equals(currentPlayerName.getRawString())) {
			return false;
		}
		return true;
	}

	@Environment(EnvType.CLIENT)
	public static String getFMessage() {
		if (!"".equals(main.configData.fText)) {
			return main.configData.fText;
		}
		List<String> textList = main.configData.fTextList;
		if (textList == null || textList.isEmpty()) {
			return "F";
		}
		return textList.get(random.nextInt(textList.size()));
	}
}

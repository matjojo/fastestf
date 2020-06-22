package matjojo.client.mixin;

import matjojo.client.util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatListenerHud;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;


@Mixin(ChatListenerHud.class)
public abstract class detectOtherPlayerDeathChatListenerHudMixin {

	@Environment(EnvType.CLIENT)
	@Inject(method = "onChatMessage(Lnet/minecraft/network/MessageType;Lnet/minecraft/text/Text;Ljava/util/UUID;)V",
	at = @At(value = "HEAD"))
	private void onChatMessageMixin(MessageType type, Text text, UUID id, CallbackInfo ci) {
		if (!util.isUsableDeathMessage(type, text)) {
			return;
		}
		// if it cannot be casted to Translatable it would not be a usable death message
		if (!util.deathMessageIsAboutOtherPlayer((TranslatableText) text)) {
			return;
		}
		// This is only the case when no world is open
		// but a message will never be added to the chat of a world outside a world
		assert MinecraftClient.getInstance().player != null;
		MinecraftClient.getInstance().player.networkHandler.sendPacket(
				new ChatMessageC2SPacket(util.getFMessage())
		);
	}
}

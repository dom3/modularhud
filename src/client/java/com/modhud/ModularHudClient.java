package com.modhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModularHudClient implements ClientModInitializer {
	private static KeyBinding optionsKeybind;
	@Override
	public void onInitializeClient() {
		optionsKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.modhud.options", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_Y, // The keycode of the key
				"category.modhud" // The translation key of the keybinding's category.
		));
		MyConfig.HANDLER.load();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (optionsKeybind.wasPressed()) {
				// This entrypoint is suitable for setting up client-specific logic, such as rendering
				MinecraftClient.getInstance().setScreen(ConfigScreenBuilder.generate(client.currentScreen));

			}
		});
	}
}
package com.modhud.categories;

import com.modhud.MyConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface Module {
    ConfigCategory builder(MyConfig defaults, MyConfig config, YetAnotherConfigLib.Builder builder);
    void render(DrawContext context, float tickDelta, CallbackInfo ci);

    static MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }
}

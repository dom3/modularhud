package com.modhud.mixin.client;

import com.modhud.categories.CoordinateModule;
import com.modhud.categories.GameModule;
import com.modhud.categories.TodoModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(at = @At("RETURN"), method = "render")
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        new CoordinateModule().render(context,tickDelta,ci);
        new TodoModule().render(context,tickDelta,ci);
        new GameModule().render(context,tickDelta,ci);
    }
}

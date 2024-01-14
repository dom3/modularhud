package com.modhud.categories;

import com.modhud.MyConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class GameModule implements Module{
    String name = "Game";
    @Override
    public ConfigCategory builder(MyConfig defaults, MyConfig config, YetAnotherConfigLib.Builder builder) {
        return ConfigCategory.createBuilder()
                .name(Text.literal(name))
                .tooltip(Text.literal(name + " Module"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.literal("Enabled"))
                        .binding(
                                defaults.game_on,
                                () -> config.game_on,
                                (value) -> config.game_on = value
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                )
                .group(OptionGroup.createBuilder()
                        .name(Text.literal("Formatting"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("X Position"))
                                .binding(
                                        defaults.game_xPosition,
                                        () -> config.game_xPosition,
                                        (value) -> config.game_xPosition = value
                                ).controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Text.literal("Y Position"))
                                .binding(
                                        defaults.game_yPosition,
                                        () -> config.game_yPosition,
                                        (value) -> config.game_yPosition = value
                                ).controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).build()
                )
                .group(OptionGroup.createBuilder()
                        .name(Text.literal("FPS"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Enabled"))
                                .binding(
                                        defaults.game_fps_on,
                                        () -> config.game_fps_on,
                                        (value) -> config.game_fps_on = value
                                )
                                .controller(BooleanControllerBuilder::create)
                                .build()
                        ).build()
                )
                .build();
    }

    @Override
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (MyConfig.HANDLER.instance().game_on){
            int positioning = 1;
            context.drawText(Module.getClient().textRenderer, "§nGame Info",MyConfig.HANDLER.instance().game_xPosition,MyConfig.HANDLER.instance().game_yPosition,0xFFFFFF,false);
            if (MyConfig.HANDLER.instance().game_fps_on) {
                positioning+=1;
                context.drawText(Module.getClient().textRenderer, "FPS: " + MinecraftClient.getInstance().getCurrentFps(),MyConfig.HANDLER.instance().game_xPosition,MyConfig.HANDLER.instance().game_yPosition+(10*positioning),0xFFFFFF,false);
            }
        }
    }
}

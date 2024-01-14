package com.modhud;

import com.modhud.categories.CoordinateModule;
import com.modhud.categories.GameModule;
import com.modhud.categories.TodoModule;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenBuilder {
    public static Screen generate(Screen parent){
        MyConfig.HANDLER.load();
        return YetAnotherConfigLib.create(MyConfig.HANDLER,((defaults, config, builder) -> builder
                .title(Text.literal("Modular Hud"))
                .category(new CoordinateModule().builder(defaults,config,builder)) // Coordinate Module
                .category(new TodoModule().builder(defaults,config,builder)) // To-do Module
                .category(new GameModule().builder(defaults,config,builder)) // Game Module
                .save(() -> {
                    MinecraftClient.getInstance().options.write();
                    MyConfig.HANDLER.save();
                })
        )).generateScreen(parent);
    }
}

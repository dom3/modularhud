package com.modhud;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MyConfig {
    public static ConfigClassHandler<MyConfig> HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
            .id(new Identifier("toddmod","config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("toddmod.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    // COORDINATE MODULE

    @SerialEntry(comment = "Is coordinate module on?")
    public boolean coord_on = false;

    @SerialEntry(comment = "The amount of decimal placements for each coordinate.")
    public int coord_decimalPlacements = 1;

    @SerialEntry(comment = "The X Position of the UI")
    public int coord_xPosition = 0;

    @SerialEntry(comment = "The Y Position of the UI")
    public int coord_yPosition = 0;

    @SerialEntry(comment = "Is Comparison On?")
    public boolean coord_compare_on = false;
    @SerialEntry(comment = "X Coordinate Comparison")
    public int coord_compare_x = 0;

    @SerialEntry(comment = "Y Coordinate Comparison")
    public int coord_compare_y = 0;

    @SerialEntry(comment = "Z Coordinate Comparison")
    public int coord_compare_z = 0;

    // TODO MODULE

    @SerialEntry(comment = "Is to-do module on?")
    public boolean todo_on = false;

    @SerialEntry(comment = "Todo list")
    public List<String> todo_list = new ArrayList<>();

    @SerialEntry(comment = "The X Position of the UI")
    public int todo_xPosition = 0;

    @SerialEntry(comment = "The Y Position of the UI")
    public int todo_yPosition = 0;

    // Game Module
    @SerialEntry(comment = "Toggle Game Module")
    public boolean game_on = false;

    //FPS
    @SerialEntry(comment = "Toggle FPS")
    public boolean game_fps_on = false;
    @SerialEntry(comment = "The X Position of the FPS")
    public int game_xPosition = 0;

    @SerialEntry(comment = "The Y Position of the FPS")
    public int game_yPosition = 0;

}

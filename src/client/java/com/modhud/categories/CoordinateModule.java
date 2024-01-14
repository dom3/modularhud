package com.modhud.categories;

import com.modhud.MyConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


public class CoordinateModule implements Module{
    @Override
    public ConfigCategory builder(MyConfig defaults, MyConfig config, YetAnotherConfigLib.Builder builder) {
        return ConfigCategory.createBuilder()
                .name(Text.literal("Coordinates"))
                .tooltip(Text.literal("Coordinate Module"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.literal("Enabled"))
                        .binding(
                                defaults.coord_on,
                                () -> config.coord_on,
                                (value) -> config.coord_on = value
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                )
                .group(OptionGroup.createBuilder()
                        .name(Text.literal("Formatting"))
                        .option(Option.<Integer>createBuilder() // Decimal Placements
                                .name(Text.literal("Decimal Placements"))
                                .binding(
                                        defaults.coord_decimalPlacements,
                                        () -> config.coord_decimalPlacements,
                                        (value) -> config.coord_decimalPlacements = value
                                )
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0,5)
                                        .step(1)
                                )
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Text.literal("X Position"))
                                .binding(
                                        defaults.coord_xPosition,
                                        () -> config.coord_xPosition,
                                        (value) -> config.coord_xPosition = value
                                )
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                        .min(0)
                                )
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Text.literal("Y Position"))
                                .binding(
                                        defaults.coord_yPosition,
                                        () -> config.coord_yPosition,
                                        (value) -> config.coord_yPosition = value
                                )
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                        .min(0)
                                )
                                .build()
                        )
                        .build()
                )                .group(OptionGroup.createBuilder()
                        .name(Text.literal("Comparison"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Compare Enabled"))
                                .binding(
                                        defaults.coord_compare_on,
                                        () -> config.coord_compare_on,
                                        (value) -> config.coord_compare_on = value
                                )
                                .controller(BooleanControllerBuilder::create)
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("X Position"))
                                .binding(
                                        defaults.coord_compare_x,
                                        () -> config.coord_compare_x,
                                        (value) -> config.coord_compare_x = value
                                )
                                .controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Text.literal("Y Position"))
                                .binding(
                                        defaults.coord_compare_y,
                                        () -> config.coord_compare_y,
                                        (value) -> config.coord_compare_y = value
                                )
                                .controller(IntegerFieldControllerBuilder::create)
                                .build()
                        )                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Z Position"))
                                .binding(
                                        defaults.coord_compare_z,
                                        () -> config.coord_compare_z,
                                        (value) -> config.coord_compare_z = value
                                )
                                .controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).build()
                )
                .build();
    }

    String format(double db){
        return String.format("%." + MyConfig.HANDLER.instance().coord_decimalPlacements+"f",db);
    }

    @Override
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (MyConfig.HANDLER.instance().coord_on) {
            //Coordinate renderer
            double x = MinecraftClient.getInstance().player.getX();
            double y = MinecraftClient.getInstance().player.getY();
            double z = MinecraftClient.getInstance().player.getZ();
            context.drawText(MinecraftClient.getInstance().textRenderer,format(x) + " " + format(y) + " " + format(z), MyConfig.HANDLER.instance().coord_xPosition,MyConfig.HANDLER.instance().coord_yPosition,0xFFFFFF,false);
            if (MyConfig.HANDLER.instance().coord_compare_on){
                double compareX = MyConfig.HANDLER.instance().coord_compare_x;
                double compareY = MyConfig.HANDLER.instance().coord_compare_y;
                double compareZ = MyConfig.HANDLER.instance().coord_compare_z;
                double distX =  x - compareX;
                double distY =  y - compareY;
                double distZ =  z - compareZ;
                //https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2634708-how-to-get-distance-from-current-block-to-target?comment=4
                double blocksAway = Math.sqrt((distX*distX) + (distY*distY) + (distZ*distZ));

                context.drawText(MinecraftClient.getInstance().textRenderer,format(distX) + " " + format(distY) + " " + format(distZ), MyConfig.HANDLER.instance().coord_xPosition,MyConfig.HANDLER.instance().coord_yPosition+10,0x00FF00,false);
                context.drawText(MinecraftClient.getInstance().textRenderer,format(blocksAway) + " blocks away", MyConfig.HANDLER.instance().coord_xPosition,MyConfig.HANDLER.instance().coord_yPosition+20,0x00FF00,false);
                context.drawText(MinecraftClient.getInstance().textRenderer,"► " + format(compareX) + " " + format(compareY) + " " + format(compareZ), MyConfig.HANDLER.instance().coord_xPosition,MyConfig.HANDLER.instance().coord_yPosition+30,0x808080,false);
            }
        }
    }
}

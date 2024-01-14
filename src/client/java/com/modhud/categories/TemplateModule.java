package com.modhud.categories;

import com.modhud.MyConfig;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class TemplateModule implements Module{
    String name = "To-do";
    @Override
    public ConfigCategory builder(MyConfig defaults, MyConfig config, YetAnotherConfigLib.Builder builder) {
        return ConfigCategory.createBuilder()
                .name(Text.literal(name))
                .tooltip(Text.literal(name + " Module"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.literal("Enabled"))
                        .binding(
                                defaults.todo_on,
                                () -> config.todo_on,
                                (value) -> config.todo_on = value
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                )
                .group(OptionGroup.createBuilder()
                        .name(Text.literal("Formatting"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("X Position"))
                                .binding(
                                        defaults.todo_xPosition,
                                        () -> config.todo_xPosition,
                                        (value) -> config.todo_xPosition = value
                                ).controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Text.literal("Y Position"))
                                .binding(
                                        defaults.todo_yPosition,
                                        () -> config.todo_yPosition,
                                        (value) -> config.todo_yPosition = value
                                ).controller(IntegerFieldControllerBuilder::create)
                                .build()
                        ).build()
                )

                .build();
    }

    @Override
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (MyConfig.HANDLER.instance().todo_on){
            //To-do renderer
            context.drawText(Module.getClient().textRenderer, "To-do:",MyConfig.HANDLER.instance().todo_xPosition,MyConfig.HANDLER.instance().todo_yPosition,0xFFFFFF,false);
            for (int i = 0; i < MyConfig.HANDLER.instance().todo_list.size();i++){
                String item = MyConfig.HANDLER.instance().todo_list.get(i);
                String text;
                //§n
                if (item.endsWith("//")){
                    text = "§m• " + item;
                    text = text.replace("//","");
                } else {
                    text = "• " + item;
                }
                context.drawText(Module.getClient().textRenderer,text,MyConfig.HANDLER.instance().todo_xPosition,MyConfig.HANDLER.instance().todo_yPosition+(i+1)*10,0xFFFFFF,false);
            }
        }
    }
}

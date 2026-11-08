package ru.homestead.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ru.homestead.block.entity.MediaBlockEntity;
import ru.homestead.menu.MediaMenu;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

public class MediaScreen extends AbstractContainerScreen<MediaMenu> {
    private EditBox urlBox;
    private EditBox fileBox;

    public MediaScreen(MediaMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 240;
        this.imageHeight = 170;
    }

    @Override
    protected void init() {
        super.init();
        int x = leftPos + 10;
        int y = topPos + 18;

        urlBox = new EditBox(font, x, y, 220, 16, Component.translatable("gui.homestead.url"));
        urlBox.setMaxLength(512);
        urlBox.setValue(menu.getUrl());
        urlBox.setResponder(s -> menu.setUrl(s));
        addRenderableWidget(urlBox);

        y += 22;
        fileBox = new EditBox(font, x, y, 160, 16, Component.translatable("gui.homestead.file"));
        fileBox.setMaxLength(1024);
        fileBox.setValue(menu.getFilePath());
        fileBox.setResponder(s -> menu.setFilePath(s));
        addRenderableWidget(fileBox);

        addRenderableWidget(Button.builder(Component.translatable("gui.homestead.browse"), b -> browseLocalFile())
                .bounds(x + 165, y - 1, 55, 18).build());

        y += 26;
        addRenderableWidget(Button.builder(Component.translatable("gui.homestead.play"), b -> menu.play())
                .bounds(x, y, 70, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.homestead.pause"), b -> menu.pause())
                .bounds(x + 75, y, 70, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.homestead.stop"), b -> menu.stop())
                .bounds(x + 150, y, 70, 20).build());

        y += 24;
        addRenderableWidget(Button.builder(Component.translatable("gui.homestead.power_off"), b -> {
            menu.powerOff();
            onClose();
        }).bounds(x, y, 100, 20).build());
    }

    private void browseLocalFile() {
        try {
            FileDialog dialog = new FileDialog((Frame) null, "Select video", FileDialog.LOAD);
            dialog.setFilenameFilter((dir, name) -> {
                String n = name.toLowerCase();
                return n.endsWith(".mp4") || n.endsWith(".webm") || n.endsWith(".mkv")
                        || n.endsWith(".avi") || n.endsWith(".mov");
            });
            dialog.setVisible(true);
            String file = dialog.getFile();
            String dir = dialog.getDirectory();
            if (file != null && dir != null) {
                String path = new File(dir, file).getAbsolutePath();
                fileBox.setValue(path);
                menu.setFilePath(path);
                urlBox.setValue("");
            }
        } catch (Throwable t) {
            // AWT may fail on some setups — user can paste path manually
            if (minecraft != null && minecraft.player != null) {
                minecraft.player.displayClientMessage(
                        Component.translatable("gui.homestead.browse_failed"), false);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics g, float partial, int mx, int my) {
        int x = leftPos;
        int y = topPos;
        g.fill(x, y, x + imageWidth, y + imageHeight, 0xFF2A2A2A);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, 0xFF3A3A3A);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, 10, 6, 0xFFFFFF, false);
        MediaBlockEntity.PlayState state = menu.getPlayState();
        String stateKey = switch (state) {
            case OFF -> "gui.homestead.state_off";
            case ON -> "gui.homestead.state_on";
            case PLAYING -> "gui.homestead.state_playing";
            case PAUSED -> "gui.homestead.state_paused";
        };
        g.drawString(font, Component.translatable(stateKey), 10, 130, 0xAAAAAA, false);
        String source = switch (menu.getSourceType()) {
            case URL -> "URL";
            case FILE -> "FILE";
            default -> "-";
        };
        g.drawString(font, Component.translatable("gui.homestead.source", source), 10, 142, 0x888888, false);
        g.drawString(font, Component.translatable("gui.homestead.video_note"), 10, 154, 0x666666, false);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float partial) {
        renderBackground(g);
        super.render(g, mx, my, partial);
        renderTooltip(g, mx, my);
    }

    @Override
    public boolean keyPressed(int key, int scan, int modifiers) {
        if ((urlBox.isFocused() || fileBox.isFocused()) && key != 256) {
            return urlBox.isFocused() ? urlBox.keyPressed(key, scan, modifiers)
                    : fileBox.keyPressed(key, scan, modifiers);
        }
        return super.keyPressed(key, scan, modifiers);
    }
}

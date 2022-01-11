package subaraki.paintings.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.mixins.ScreenAccessor;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.network.ServerNetwork;

import java.util.List;
import java.util.Optional;

public class PaintingScreen extends Screen {

    public static final int START_X = 10;
    public static final int START_Y = 30;
    public static final int GAP = 5;
    private final int entityID;
    private final Button defaultButton = new Button(0, 0, 0, 0, new TextComponent("default"), button -> {
    });
    private final Motive[] types;
    private int scrollBarScroll = 0;

    public PaintingScreen(Motive[] types, int entityID) {
        super(new TranslatableComponent("select.a.painting"));
        this.types = types;
        this.entityID = entityID;
    }

    @Override
    protected void init() {
        super.init();
        this.addButtons();
        scrollBarScroll = 0;
    }

    private void addButtons() {
        final int END_X = width - 30;
        int prevHeight = types[0].getHeight(); // paintings are sorted from biggest to smallest at this point

        int posx = START_X;
        int posy = GAP + START_Y;

        int index = 0;
        int rowstart = 0;

        for (Motive motive : types) {
            // if the painting size is different, or we're at the end of the row, jump down
            // and start at the beginning of the row again
            if (posx + motive.getWidth() > END_X || prevHeight > motive.getHeight()) {
                centerRow(rowstart, index - 1);
                rowstart = index;
                posx = START_X;
                posy += prevHeight + GAP;
                prevHeight = motive.getHeight(); // stays the same on row end, changes when heights change

            }
            try {
                this.addRenderableWidget(new PaintingButton(posx, posy, motive.getWidth(), motive.getHeight(), new TextComponent(""), button -> {
                    //Encodes needed data and sends to server
                    FriendlyByteBuf buf = PacketByteBufs.create();
                    buf.writeUtf(Registry.MOTIVE.getKey(motive).toString());
                    buf.writeInt(entityID);
                    ClientPlayNetworking.send(ServerNetwork.SERVER_PACKET, buf);
                    this.removed();
                    this.onClose();
                }, motive));
            } catch (NullPointerException e) {
                subaraki.paintings.Paintings.LOGGER.warn("*******************");
                subaraki.paintings.Paintings.LOGGER.warn(e.getMessage());
                subaraki.paintings.Paintings.LOGGER.warn("*******************");
            }
            posx += GAP + motive.getWidth();

            index++;
        }

        // call last time for last line
        centerRow(rowstart, getRenderablesWithCast().size() - 1);
    }

    private void centerRow(int start, int end) {

        if (optionalAbstractWidget(start).isPresent() && optionalAbstractWidget(end).isPresent()) {
            int left = this.optionalAbstractWidget(start).get().x;
            int right = optionalAbstractWidget(end).get().x + optionalAbstractWidget(end).get().getWidth();

            // We're 10 pixels away from each edge
            int correction = (width - 20 - (right - left)) / 2;
            for (int i = start; i <= end; ++i) {
                optionalAbstractWidget(i).ifPresent(widget -> {
                    if (widget instanceof PaintingButton painting)
                        painting.shiftX(correction);
                });
            }
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float f) {
        this.renderBackground(stack);
        fill(stack, START_X, START_Y, width - START_X, height - START_Y, 0x44444444);
        Window window = minecraft.getWindow();
        int scale = (int) window.getGuiScale();
        RenderSystem.enableScissor(START_X * scale, START_Y * scale, width * scale, (height - (START_Y * 2)) * scale);
        super.render(stack, mouseX, mouseY, f);
        RenderSystem.disableScissor();
        if (!getRenderablesWithCast().isEmpty()) {
            drawFakeScrollBar(stack);
        }
        drawCenteredString(stack, font, title, width / 2, START_Y / 2, 0xffffff);
        drawToolTips(stack, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double mouseScroll) {
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {
            int foreseeBottomLimit = (optionalLastWidget().get().y + optionalLastWidget().get().getHeight() + (16));
            int bottomLimit = height - START_Y - optionalLastWidget().get().getHeight();

            int foreseeTopLimit = (optionalFirstWidget().get().y + 16);
            int topLimit = GAP + START_Y;
            // scrolling up
            if (mouseScroll < 0.0 && foreseeBottomLimit < bottomLimit)
                return super.mouseScrolled(mouseX, mouseY, mouseScroll);
            // down
            if (mouseScroll > 0.0 && foreseeTopLimit > topLimit)
                return super.mouseScrolled(mouseX, mouseY, mouseScroll);

            move(mouseScroll);
        }


        return super.mouseScrolled(mouseX, mouseY, mouseScroll);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int buttonID, double amountX, double amountY) {
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {
            // correct speed and inversion of dragging
            amountY *= -1d;
            amountY /= 2d;
            int foreseeBottomLimit = (int) (optionalLastWidget().get().y + optionalLastWidget().get().getHeight() + (amountY * 16));
            int bottomLimit = height - START_Y - optionalLastWidget().get().getHeight();

            int foreseeTopLimit = (int) (optionalFirstWidget().get().y + amountY * 16);
            int topLimit = GAP + START_Y;
            // scrolling up
            if (amountY < 0.0 && foreseeBottomLimit < bottomLimit)
                return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);
            // down
            if (amountY > 0.0 && foreseeTopLimit > topLimit)
                return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);

            move(amountY);
        }

        return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);
    }


    private void move(double scroll) {
        scrollBarScroll -= scroll * 16;
        for (Widget w : this.getRenderablesWithCast()) {
            if (w instanceof AbstractWidget widget)
                widget.y += scroll * 16;
        }
    }

    private void drawToolTips(PoseStack mat, int mouseX, int mouseY) {
        if (!Paintings.config.show_painting_size)
            return;
        for (Widget guiButton : getRenderablesWithCast()) {
            if (guiButton instanceof PaintingButton button) {
                if (button.isMouseOver(mouseX, mouseY)) {
                    TextComponent text = new TextComponent(button.getWidth() / 16 + "x" + button.getHeight() / 16);
                    HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);

                    Style style = Style.EMPTY.withHoverEvent(hover);

                    this.renderComponentHoverEffect(mat, style, width / 2 - font.width(text.getContents()) - 4, height - START_Y / 4);
                }
            }
        }
    }

    private void drawFakeScrollBar(PoseStack mat) {
        if (getRenderablesWithCast().isEmpty())
            return;
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {
            int top = optionalFirstWidget().get().y;
            int bot = optionalLastWidget().get().y + optionalLastWidget().get().getHeight();
            // get total size for buttons drawn
            float totalSize = (bot - top) + (GAP);
            float containerSize = height - START_Y * 2;

            // relative % of the scale between the buttons drawn and the screen size
            float percent = ((containerSize / totalSize) * 100f);

            if (percent < 100) {

                float sizeBar = (containerSize / 100f * percent);

                float relativeScroll = ((float) scrollBarScroll / 100f * percent);

                // what kind of dumbfuck decided it was intelligent to have 'fill' fill in from
                // left to right
                // and fillgradient from right to fucking left ???

                // draw a black background background
                this.fillGradient(mat, width - START_X, START_Y, width, START_Y + (int) containerSize, 0x80000000, 0x80222222);
                // Draw scrollbar
                this.fillGradient(mat, width - START_X, START_Y + (int) relativeScroll, width, START_Y + (int) relativeScroll + (int) sizeBar, 0x80ffffff, 0x80222222);

            }
        }
    }

    private AbstractWidget getAbstractWidget(Widget widget) {
        if (widget instanceof AbstractWidget abstractWidget)
            return abstractWidget;
        return defaultButton;
    }

    private Optional<AbstractWidget> optionalAbstractWidget(int index) {
        Widget w = getRenderablesWithCast().get(index);
        if (w instanceof AbstractWidget aw)
            return Optional.of(aw);
        return Optional.empty();
    }

    private Optional<AbstractWidget> optionalFirstWidget() {
        return optionalAbstractWidget(0);
    }

    private Optional<AbstractWidget> optionalLastWidget() {
        return optionalAbstractWidget(getRenderablesWithCast().size() - 1);
    }

    public List<Widget> getRenderablesWithCast() {
        return ((ScreenAccessor) this).getRenderables();
    }
}

package subaraki.paintings.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.utils.Services;

import java.util.List;
import java.util.Optional;

public class CommonPaintingScreen extends Screen implements IPaintingGUI {

    public static final int START_X = 10;
    public static final int START_Y = 30;
    public static final int GAP = 5;
    private final BlockPos pos;
    private final Direction face;
    private final PaintingVariant[] types;
    private int scrollBarScroll = 0;

    public CommonPaintingScreen(PaintingVariant[] types, BlockPos pos, Direction face) {
        super(Component.translatable("select.a.painting"));
        this.types = types;
        this.pos = pos;
        this.face = face;
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

        for (PaintingVariant variant : types) {
            // if the painting size is different, or we're at the end of the row, jump down
            // and start at the beginning of the row again
            if (posx + variant.getWidth() > END_X || prevHeight > variant.getHeight()) {
                centerRow(rowstart, index - 1);
                rowstart = index;
                posx = START_X;
                posy += prevHeight + GAP;
                prevHeight = variant.getHeight(); // stays the same on row end, changes when heights change

            }
            try {
                this.addRenderableWidget(new PaintingButton(posx, posy, variant.getWidth(), variant.getHeight(), Component.literal(""), button -> {
                    sendPacket(BuiltInRegistries.PAINTING_VARIANT.getKey(variant), pos, face);
                    this.removed();
                    this.onClose();
                }, variant));
            } catch (NullPointerException e) {
                subaraki.paintings.Paintings.LOGGER.warn("*******************");
                subaraki.paintings.Paintings.LOGGER.warn(e.getMessage());
                subaraki.paintings.Paintings.LOGGER.warn("*******************");
            }
            posx += GAP + variant.getWidth();

            index++;
        }

        // call last time for last line
        centerRow(rowstart, getRenderablesWithCast().size() - 1);
    }

    private void centerRow(int start, int end) {

        if (optionalAbstractWidget(start).isPresent() && optionalAbstractWidget(end).isPresent()) {
            int left = this.optionalAbstractWidget(start).get().getX();
            int right = optionalAbstractWidget(end).get().getX() + optionalAbstractWidget(end).get().getWidth();

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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        this.renderBackground(guiGraphics);
        guiGraphics.fill(START_X, START_Y, width - START_X, height - START_Y, 0x44444444);
        Window window = minecraft.getWindow();
        int scale = (int) window.getGuiScale();
        RenderSystem.enableScissor(START_X * scale, START_Y * scale, width * scale, (height - (START_Y * 2)) * scale);
        super.render(guiGraphics, mouseX, mouseY, f);
        RenderSystem.disableScissor();
        if (!getRenderablesWithCast().isEmpty()) {
            drawFakeScrollBar(guiGraphics);
        }
        guiGraphics.drawCenteredString(font, title, width / 2, START_Y / 2, 0xffffff);
        drawToolTips(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double mouseScroll) {
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {

            int move = (mouseScroll < 0 ? -1 : mouseScroll > 0 ? 1 : 0) * 16;
            movePaintingWidgets(move);
        }
        return super.mouseScrolled(mouseX, mouseY, mouseScroll);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int buttonID, double amountX, double amountY) {
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {

            float move = (float) amountY * -1.0f;

            int paintingCanvasTopY = (optionalFirstWidget().get().getY());
            int paintingCanvasBotY = (optionalLastWidget().get().getY() + optionalLastWidget().get().getHeight());

            int paintingContainerSize = paintingCanvasBotY - paintingCanvasTopY; //total height span of all shown paintings. gaps accounted for
            int viewport = height - (START_Y); //height of cutout rect in which paintings are shown
            float portToCanvasScale = ((float) viewport / (float) paintingContainerSize); // scale from viewport to total span
            float barsize = ((float) viewport * portToCanvasScale); //size of the scrollbar
            float scaledViewport = viewport - barsize; //scale of viewport - bar height. top of the scrollbar can only scroll between this 'bar'
            float usableSpaceScale = ((float) paintingContainerSize / scaledViewport);

            float scaledDrageMove = (move * usableSpaceScale);
            movePaintingWidgets((int) scaledDrageMove);
        }
        return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);
    }

    private void movePaintingWidgets(int scrollAmount) {
        int paintingCanvasTopY = (optionalFirstWidget().get().getY());
        int onScreenTopLimit = GAP + START_Y; //relative screen position, first painting can only scroll up until here
        int paintingCanvasBotY = (optionalLastWidget().get().getY() + optionalLastWidget().get().getHeight());
        int onScreenBottomLimit = height - (onScreenTopLimit); //relative screen position, last painting can only scroll up until here

        if ((scrollAmount > 0 && paintingCanvasTopY < onScreenTopLimit) || (scrollAmount < 0 && paintingCanvasBotY >= onScreenBottomLimit)) {
            getRenderablesWithCast().forEach(widget -> ((AbstractWidget) widget).setY(((AbstractWidget) widget).getY() + scrollAmount));
            scrollBarScroll -= scrollAmount;
        }
    }


    private void drawToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!Services.CONFIG.showPaintingSize())
            return;
        for (Renderable guiButton : getRenderablesWithCast()) {
            if (guiButton instanceof PaintingButton button) {
                if (button.isMouseOver(mouseX, mouseY)) {
                    MutableComponent text = Component.literal(button.getWidth() / 16 + "x" + button.getHeight() / 16);
                    HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);

                    Style style = Style.EMPTY.withHoverEvent(hover);
                    guiGraphics.renderComponentHoverEffect(this.font, style, width / 2 - font.width(text) - 4, height - START_Y / 4);
                }
            }
        }
    }

    private void drawFakeScrollBar(GuiGraphics guiGraphics) {
        if (getRenderablesWithCast().isEmpty())
            return;
        if (optionalFirstWidget().isPresent() && optionalLastWidget().isPresent()) {
            int top = optionalFirstWidget().get().getY();
            int bot = optionalLastWidget().get().getY() + optionalLastWidget().get().getHeight();
            // get total size for buttons drawn
            int totalSize = (bot - top);
            int containerSize = (height - (START_Y * 2));

            // relative % of the scale between the buttons drawn and the screen size
            float percent = (((float) containerSize / (float) totalSize) * 100f);

            if (percent < 100.0) {
                int sizeBar = (int) ((float) containerSize / 100f * percent);
                int relativeScroll = (int) ((float) scrollBarScroll / 100f * percent);
                // draw a black background
                guiGraphics.fillGradient(width - START_X, START_Y, width, START_Y + containerSize, 0x80000000, 0x80222222);
                // Draw scrollbar
                guiGraphics.fillGradient(width - START_X, START_Y + relativeScroll, width, START_Y + relativeScroll + sizeBar, 0x80ffffff, 0x80222222);

            }
        }
    }

    private Optional<AbstractWidget> optionalFirstWidget() {
        return optionalAbstractWidget(0);
    }

    private Optional<AbstractWidget> optionalLastWidget() {
        return optionalAbstractWidget(getRenderablesWithCast().size() - 1);
    }

    @Override
    public Optional<AbstractWidget> optionalAbstractWidget(int index) {
        return Optional.empty();
    }

    @Override
    public List<Renderable> getRenderablesWithCast() {
        throw new IllegalStateException("painting gui common code crash override. please override paintingscreen");
    }

    @Override
    public void sendPacket(ResourceLocation variantName, BlockPos pos, Direction face) {

    }
}

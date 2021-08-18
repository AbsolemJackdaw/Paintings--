package subaraki.paintings.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.server.SPacketPainting;

public class PaintingScreen extends Screen {

    final int START_X = 10;
    final int START_Y = 30;
    final int GAP = 5;

    private Motive[] resLocs;
    private final int entityID;

    private int scrollbarscroll = 0;

    public PaintingScreen(Motive[] resLocs, int entityID) {

        super(new TranslatableComponent("select.a.painting"));
        this.resLocs = resLocs;
        this.entityID = entityID;
    }

    @Override
    protected void init()
    {

        super.init();

        this.addButtons();
        scrollbarscroll = 0;
    }

    private void addButtons()
    {

        final int END_X = width - 30;

        int prevHeight = resLocs[0].getHeight(); // paintings are sorted from biggest to smallest at this point

        int posx = START_X;
        int posy = GAP + START_Y;

        int index = 0;
        int rowstart = 0;

        for (Motive type : resLocs)
        {
            // if the painting size is different, or we're at the end of the row, jump down
            // and start at the beginning of the row again
            if (posx + type.getWidth() > END_X || prevHeight > type.getHeight())
            {

                centerRow(rowstart, index - 1);

                rowstart = index;

                posx = START_X;
                posy += prevHeight + GAP;
                prevHeight = type.getHeight(); // stays the same on row end, changes when heights change

            }

            this.addRenderableWidget(new PaintingButton(posx, posy, type.getWidth(), type.getHeight(), new TextComponent(""), (Button) -> {
                NetworkHandler.NETWORK.sendToServer(new SPacketPainting(type, this.entityID));
                this.removed();
                this.onClose();
                
            }, type));

            posx += GAP + type.getWidth();

            index++;
        }

        // call last time for last line
        centerRow(rowstart, this.renderables.size() - 1);

    }

    private void centerRow(int start, int end)
    {

        int left = this.getAbstractWidget(start).x;
        int right = getAbstractWidget(end).x + getAbstractWidget(end).getWidth();

        // We're 10 pixels away from each edge
        int correction = (width - 20 - (right - left)) / 2;
        for (int i = start; i <= end; ++i)
        {
            AbstractWidget widget = this.getAbstractWidget(i);
            if (widget instanceof PaintingButton)
                ((PaintingButton) widget).shiftX(correction);
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float p_render_3_)
    {

        this.renderBackground(stack);

        fill(stack, START_X, START_Y, width - START_X, height - START_Y, 0x44444444);

        Window window = minecraft.getWindow();
        int scale = (int) window.getGuiScale();

        RenderSystem.enableScissor(START_X * scale, START_Y * scale, width * scale, (height - (START_Y * 2)) * scale);

        super.render(stack, mouseX, mouseY, p_render_3_);

        RenderSystem.disableScissor();

        if (!renderables.isEmpty())
        {
            drawFakeScrollBar(stack);
        }
        drawCenteredString(stack, font, title, width / 2, START_Y / 2, 0xffffff);

        drawToolTips(stack, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double mouseScroll)
    {

        AbstractWidget last = getAbstractWidget(renderables.size() - 1);
        AbstractWidget first = getAbstractWidget(0);

        int forsee_bottom_limit = (int) (last.y + last.getHeight() + (mouseScroll * 16));
        int bottom_limit = height - START_Y - last.getHeight();

        int forsee_top_limit = (int) (first.y + mouseScroll * 16);
        int top_limit = GAP + START_Y;
        // scrolling up
        if (mouseScroll < 0.0 && forsee_bottom_limit < bottom_limit)
            return super.mouseScrolled(mouseX, mouseY, mouseScroll);
        // down
        if (mouseScroll > 0.0 && forsee_top_limit > top_limit)
            return super.mouseScrolled(mouseX, mouseY, mouseScroll);

        move(mouseScroll);

        return super.mouseScrolled(mouseX, mouseY, mouseScroll);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int buttonID, double amountX, double amountY)
    {

        // correct speed and inversion of dragging
        amountY *= -1d;
        amountY /= 2d;

        AbstractWidget last = getAbstractWidget(renderables.size() - 1);
        AbstractWidget first = getAbstractWidget(0);

        int forsee_bottom_limit = (int) (last.y + last.getHeight() + (amountY * 16));
        int bottom_limit = height - START_Y - last.getHeight();

        int forsee_top_limit = (int) (first.y + amountY * 16);
        int top_limit = GAP + START_Y;
        // scrolling up
        if (amountY < 0.0 && forsee_bottom_limit < bottom_limit)
            return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);
        // down
        if (amountY > 0.0 && forsee_top_limit > top_limit)
            return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);

        move(amountY);

        return super.mouseDragged(mouseX, mouseY, buttonID, amountX, amountY);
    }

    private void move(double scroll)
    {

        scrollbarscroll -= scroll * 16;

        for (Widget w : this.renderables)
        {
            getAbstractWidget(w).y += scroll * 16;
        }
    }

    private void drawToolTips(PoseStack mat, int mouseX, int mouseY)
    {

        if (!ConfigData.show_painting_size)
            return;
        for (Widget guiButton : renderables)
        {
            if (guiButton instanceof PaintingButton)
            {
                PaintingButton button = (PaintingButton) guiButton;
                if (button.isMouseOver(mouseX, mouseY))
                {
                    TextComponent text = new TextComponent(button.getWidth() / 16 + "x" + button.getHeight() / 16);
                    HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);

                    Style style = Style.EMPTY.withHoverEvent(hover);

                    this.renderComponentHoverEffect(mat, style, width / 2 - font.width(text.getContents()) - 4, height - START_Y / 4);
                }
            }
        }
    }

    private void drawFakeScrollBar(PoseStack mat)
    {

        int top = getAbstractWidget(0).y;
        int bot = getAbstractWidget(renderables.size() - 1).y + getAbstractWidget(renderables.size() - 1).getHeight();

        // get total size for buttons drawn
        float totalSize = (bot - top) + (GAP);
        float containerSize = height - START_Y * 2;

        // relative % of the scale between the buttons drawn and the screen size
        float percent = (((float) containerSize / (float) totalSize) * 100f);

        if (percent < 100)
        {

            float sizeBar = (containerSize / 100f * percent);

            float relativeScroll = ((float) scrollbarscroll / 100f * percent);

            // what kind of dumbfuck decided it was intelligent to have 'fill' fill in from
            // left to right
            // and fillgradient from right to fucking left ???

            // this.fill(width - START_X, START_Y + (int) relativeScroll, width - START_X -
            // 4, START_Y + (int) relativeScroll + (int) sizeBar,
            // 0xff00ffff);

            // draw a black background background
            this.fillGradient(mat, width - START_X, START_Y, width, START_Y + (int) containerSize, 0x80000000, 0x80222222);
            // Draw scrollbar
            this.fillGradient(mat, width - START_X, START_Y + (int) relativeScroll, width, START_Y + (int) relativeScroll + (int) sizeBar, 0x80ffffff,
                    0x80222222);

        }
    }

    private final Button defaultButton = new Button(0, 0, 0, 0, new TextComponent("default"), (Button) -> {
    });

    private AbstractWidget getAbstractWidget(Widget widget)
    {

        if (widget instanceof AbstractWidget)
            return (AbstractWidget) widget;

        return defaultButton;
    }

    private AbstractWidget getAbstractWidget(int index)
    {

        if (index < 0 || index > this.renderables.size())
            return defaultButton;

        Widget w = this.renderables.get(index);

        if (w instanceof AbstractWidget)
            return (AbstractWidget) w;

        return defaultButton;
    }
}

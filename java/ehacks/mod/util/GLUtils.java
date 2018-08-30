package ehacks.mod.util;

import ehacks.mod.external.axis.AltAxisAlignedBB;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.wrapper.Wrapper;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class GLUtils {

    public static boolean hasClearedDepth = false;
    
    private static final Minecraft mc = Wrapper.INSTANCE.mc();
    private static final RenderItem itemRenderer = new RenderItem();
    private static final Sphere sphere = new Sphere();
    static Sphere s = new Sphere();
    protected float zLevel;

    public GLUtils() {
        sphere.setDrawStyle(100013);
        this.zLevel = 0.0f;
    }

    public static void drawSphere(double d1, double d2, double d3, double d4, double x, double y, double z, float size, int slices, int stacks, float lWidth) {

        GLUtils.enableDefaults();
        GL11.glColor4d((double) d1, (double) d2, (double) d3, (double) d4);
        GL11.glTranslated((double) x, (double) y, (double) z);
        GL11.glLineWidth((float) lWidth);
        sphere.draw(size, slices, stacks);
        GLUtils.disableDefaults();
    }

    public static void drawCheck(int x, int y) {

        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glColor4f((float) 0.0f, (float) 0.0f, (float) 0.75f, (float) 0.5f);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glLineWidth((float) 2.0f);
        GL11.glBegin((int) 3);
        GL11.glVertex2f((float) (x + 1), (float) (y + 4));
        GL11.glVertex2f((float) (x + 3), (float) ((float) y + 6.5f));
        GL11.glVertex2f((float) (x + 7), (float) (y + 2));
        GL11.glEnd();
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 3553);
    }

    public static void enableDefaults() {
        Wrapper.INSTANCE.mc().entityRenderer.disableLightmap(1.0);
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glPushMatrix();
    }

    public static void disableDefaults() {
        GL11.glPopMatrix();
        GL11.glDisable((int) 2848);
        GL11.glDepthMask((boolean) true);
        GL11.glEnable((int) 2929);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2896);
        GL11.glDisable((int) 3042);
        Wrapper.INSTANCE.mc().entityRenderer.enableLightmap(1.0);
    }

    public void removeDuplicated(Collection list) {
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
    }

    public static void renderPlayerSphere(double par3, double par5, double par7) {

        float x = (float) par3;
        float y = (float) par5;
        float z = (float) par7;
        GLUtils.renderSphere(x, y, z);
    }

    private static void renderSphere(float x, float y, float z) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) (y + 1.0f), (float) z);
        GL11.glColor4f((float) 0.0f, (float) 1.0f, (float) 1.0f, (float) 0.5f);
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 2896);
        GL11.glLineWidth((float) 0.5f);
        s.setDrawStyle(100011);
        float radius = 4.0f;
        s.draw(radius, 32, 32);
        GL11.glEnable((int) 3553);
        GL11.glPopMatrix();
    }

    public static void drawItem(int x, int y, ItemStack stack) {

        itemRenderer.renderItemIntoGUI(GLUtils.mc.fontRenderer, GLUtils.mc.renderEngine, stack, x, y);
        itemRenderer.renderItemAndEffectIntoGUI(GLUtils.mc.fontRenderer, GLUtils.mc.renderEngine, stack, x, y);
        GL11.glDisable((int) 2884);
        GL11.glEnable((int) 3008);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 2884);
        GL11.glClear((int) 256);
    }

    public static void drawSmallString(String s, int x, int y, int co) {

        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(s, x * 2, y * 2, co);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public void drawTexturedModalRect(double i, double d, int par3, int par4, double e, double f) {

        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(i + 0.0, d + f, (double) this.zLevel, (double) ((float) (par3 + 0) * var7), (double) ((float) ((double) par4 + f) * var8));
        var9.addVertexWithUV(i + e, d + f, (double) this.zLevel, (double) ((float) ((double) par3 + e) * var7), (double) ((float) ((double) par4 + f) * var8));
        var9.addVertexWithUV(i + e, d + 0.0, (double) this.zLevel, (double) ((float) ((double) par3 + e) * var7), (double) ((float) (par4 + 0) * var8));
        var9.addVertexWithUV(i + 0.0, d + 0.0, (double) this.zLevel, (double) ((float) (par3 + 0) * var7), (double) ((float) (par4 + 0) * var8));
        var9.draw();
    }

    public static void drawBorderRect(int x, int y, int x1, int y1, int color, int bcolor) {

        float rs = 2.0f;
        x = (int) ((float) x * rs);
        y = (int) ((float) y * rs);
        x1 = (int) ((float) x1 * rs);
        y1 = (int) ((float) y1 * rs);
        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        Gui.drawRect((int) (x + 1), (int) (y + 1), (int) (x1 - 1), (int) (y1 - 1), (int) color);
        Gui.drawRect((int) x, (int) y, (int) (x + 1), (int) y1, (int) bcolor);
        Gui.drawRect((int) (x1 - 1), (int) y, (int) x1, (int) y1, (int) bcolor);
        Gui.drawRect((int) x, (int) y, (int) x1, (int) (y + 1), (int) bcolor);
        Gui.drawRect((int) x, (int) (y1 - 1), (int) x1, (int) y1, (int) bcolor);
        GL11.glScalef((float) rs, (float) rs, (float) rs);
    }

    public static void drawMovingString(String s, int height, int displaywidth, int color) {

        Integer widthmover = null;
        if (widthmover == null || widthmover >= displaywidth + 1) {
            widthmover = -Wrapper.INSTANCE.fontRenderer().getStringWidth(s);
        }
        if (widthmover != null) {
            Wrapper.INSTANCE.fontRenderer().drawString(s, widthmover.intValue(), height, color);
            Integer n = widthmover = Integer.valueOf(widthmover + 1);
        }
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {

        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        GLUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        GLUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        GLUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        GLUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        GLUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        GLUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        GLUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        GLUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        GLUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, int borderC, int insideC) {

        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        GLUtils.drawVLine(x *= 2.0f, y *= 2.0f, (y1 *= 2.0f) - 1.0f, borderC);
        GLUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
        GLUtils.drawHLine(x, x1 - 1.0f, y, borderC);
        GLUtils.drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
        GLUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public static void drawButton(int x, int y, int x2, int y2, int borderC, int topgradient, int bottomgradient) {

        GLUtils.drawBorderedRect(x, y, x2, y2, borderC, 16777215);
        Gui.drawRect((int) (x2 - 2), (int) y, (int) (x2 - 1), (int) y2, (int) -16172197);
        Gui.drawRect((int) (x + 1), (int) (y + 1), (int) (x2 - 1), (int) (y + 2), (int) -15050626);
        Gui.drawRect((int) (x + 1), (int) (y + 1), (int) (x + 2), (int) (y2 - 1), (int) -15050626);
        Gui.drawRect((int) x, (int) (y2 - 2), (int) x2, (int) (y2 - 1), (int) -16172197);
        GLUtils.drawGradientRect(x + 2, y + 2, x2 - 2, y2 - 2, topgradient, bottomgradient);
    }

    public static boolean stringListContains(List<String> list, String needle) {
        for (String s : list) {
            if (!s.trim().equalsIgnoreCase(needle.trim())) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {

        GLUtils.drawRect((float) x, (float) y, (float) x2, (float) y2, col2);
        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f2 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        GL11.glLineWidth((float) l1);
        GL11.glBegin((int) 1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
    }

    public static void drawHLine(float par1, float par2, float par3, int par4) {
        if (par2 < par1) {
            float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        GLUtils.drawRect(par1, par3, par2 + 1.0f, par3 + 1.0f, par4);
    }

    public static void drawVLine(float par1, float par2, float par3, int par4) {
        if (par3 < par2) {
            float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        GLUtils.drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }

    public static int getColor(int a, int r, int g, int b) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static int getColor(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {

        float alpha = (float) (paramColor >> 24 & 255) / 255.0f;
        float red = (float) (paramColor >> 16 & 255) / 255.0f;
        float green = (float) (paramColor >> 8 & 255) / 255.0f;
        float blue = (float) (paramColor & 255) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
        GL11.glBegin((int) 7);
        GL11.glVertex2d((double) paramXEnd, (double) paramYStart);
        GL11.glVertex2d((double) paramXStart, (double) paramYStart);
        GL11.glVertex2d((double) paramXStart, (double) paramYEnd);
        GL11.glVertex2d((double) paramXEnd, (double) paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {

        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f2 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        float f4 = (float) (col2 >> 24 & 255) / 255.0f;
        float f5 = (float) (col2 >> 16 & 255) / 255.0f;
        float f6 = (float) (col2 >> 8 & 255) / 255.0f;
        float f7 = (float) (col2 & 255) / 255.0f;
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glShadeModel((int) 7425);
        GL11.glPushMatrix();
        GL11.glBegin((int) 7);
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glColor4f((float) f5, (float) f6, (float) f7, (float) f4);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glShadeModel((int) 7424);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f2 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        GLUtils.drawRect((float)x, (float)y, (float)x2, (float)y2, col3);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 3042);
        GL11.glPushMatrix();
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        GL11.glLineWidth((float) 1f);
        GL11.glBegin((int) 1);
        GL11.glVertex2d((double) x, (double) y - 0.5);
        GL11.glVertex2d((double) x, (double) y2 + 0.5);
        GL11.glVertex2d((double) x2, (double) y2 + 0.5);
        GL11.glVertex2d((double) x2, (double) y - 0.5);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        //GLUtils.drawGradientRect(x, y, x2, y2, col2, col3);
        GL11.glEnable((int) 3042);
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 2848);
    }

    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {

        int i;
        float yc;
        float a2;
        float xc;
        GL11.glPushMatrix();
        float f1 = (float) (color >> 24 & 255) / 255.0f;
        float f2 = (float) (color >> 16 & 255) / 255.0f;
        float f3 = (float) (color >> 8 & 255) / 255.0f;
        float f4 = (float) (color & 255) / 255.0f;
        GL11.glTranslatef((float) x, (float) y, (float) 0.0f);
        GL11.glColor4f((float) f2, (float) f3, (float) f4, (float) f1);
        GL11.glLineWidth((float) width);
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 3008);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glHint((int) 3154, (int) 4354);
        GL11.glEnable((int) 32925);
        if (angle > 0.0) {
            GL11.glBegin((int) 3);
            i = 0;
            while ((double) i < angle) {
                a2 = (float) ((double) i * (angle * 3.141592653589793 / (double) points));
                xc = (float) (Math.cos(a2) * (double) radius);
                yc = (float) (Math.sin(a2) * (double) radius);
                GL11.glVertex2f((float) xc, (float) yc);
                ++i;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin((int) 3);
            i = 0;
            while ((double) i > angle) {
                a2 = (float) ((double) i * (angle * 3.141592653589793 / (double) points));
                xc = (float) (Math.cos(a2) * (double) (-radius));
                yc = (float) (Math.sin(a2) * (double) (-radius));
                GL11.glVertex2f((float) xc, (float) yc);
                --i;
            }
            GL11.glEnd();
        }
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3008);
        GL11.glEnable((int) 2929);
        GL11.glDisable((int) 32925);
        GL11.glDisable((int) 3479);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        cx *= 2.0f;
        cy *= 2.0f;
        float f = (float) (c >> 24 & 255) / 255.0f;
        float f1 = (float) (c >> 16 & 255) / 255.0f;
        float f2 = (float) (c >> 8 & 255) / 255.0f;
        float f3 = (float) (c & 255) / 255.0f;
        float theta = (float) (6.2831852 / (double) num_segments);
        float p2 = (float) Math.cos(theta);
        float s = (float) Math.sin(theta);
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glBegin((int) 2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f((float) (x + cx), (float) (y + cy));
            float t = x;
            x = p2 * x - s * y;
            y = s * t + p2 * y;
        }
        GL11.glEnd();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f = (float) (c >> 24 & 255) / 255.0f;
        float f1 = (float) (c >> 16 & 255) / 255.0f;
        float f2 = (float) (c >> 8 & 255) / 255.0f;
        float f3 = (float) (c & 255) / 255.0f;
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        GL11.glBegin((int) 6);
        for (int i = 0; i <= 360; ++i) {
            double x = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
            double y = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d((double) ((double) cx + x), (double) ((double) cy + y));
        }
        GL11.glEnd();
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public static void drawOutlinedBoundingBox(AltAxisAlignedBB par1AxisAlignedBB) {

        Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(1);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.draw();
    }

    public static void drawBoundingBox(AltAxisAlignedBB axisalignedbb) {

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.draw();
    }

    public static void drawESP(double d, double d1, double d2, double r, double b2, double g) {

        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glLineWidth((float) 1.5f);
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glColor4d((double) r, (double) g, (double) b2, (double) 0.18250000476837158);
        GLUtils.drawBoundingBox(new AltAxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glColor4d((double) r, (double) g, (double) b2, (double) 1.0);
        GLUtils.drawOutlinedBoundingBox(new AltAxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glLineWidth((float) 2.0f);
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2896);
        GL11.glEnable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glPopMatrix();
    }

    public static void startDrawingESPs(double d, double d1, double d2, double r, double g, double b2) {

        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glLineWidth((float) 1.5f);
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glColor4d((double) r, (double) g, (double) b2, (double) 0.1850000023841858);
        GLUtils.drawBoundingBox(new AltAxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glColor4d((double) r, (double) g, (double) b2, (double) 1.0);
        GLUtils.drawOutlinedBoundingBox(new AltAxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glLineWidth((float) 2.0f);
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2896);
        GL11.glEnable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glPopMatrix();
    }

    public static void startDrawingESPs(AltAxisAlignedBB bb, float r, float b2, float g) {

        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glLineWidth((float) 1.5f);
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glColor4d((double) r, (double) b2, (double) g, (double) 0.1850000023841858);
        GLUtils.drawBoundingBox(bb);
        GL11.glColor4d((double) r, (double) b2, (double) g, (double) 1.0);
        GLUtils.drawOutlinedBoundingBox(bb);
        GL11.glLineWidth((float) 2.0f);
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2896);
        GL11.glEnable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glPopMatrix();
    }

    public static void renderBlock(int x, int y, int z, XRayBlock block) {

        GL11.glColor4ub((byte) ((byte) block.r), (byte) ((byte) block.g), (byte) ((byte) block.b), (byte) ((byte) block.a));
        GL11.glVertex3f((float) x, (float) y, (float) z);
        GL11.glVertex3f((float) (x + 1), (float) y, (float) z);
        GL11.glVertex3f((float) (x + 1), (float) y, (float) z);
        GL11.glVertex3f((float) (x + 1), (float) y, (float) (z + 1));
        GL11.glVertex3f((float) x, (float) y, (float) z);
        GL11.glVertex3f((float) x, (float) y, (float) (z + 1));
        GL11.glVertex3f((float) x, (float) y, (float) (z + 1));
        GL11.glVertex3f((float) (x + 1), (float) y, (float) (z + 1));
        GL11.glVertex3f((float) x, (float) (y + 1), (float) z);
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) z);
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) z);
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) (z + 1));
        GL11.glVertex3f((float) x, (float) (y + 1), (float) z);
        GL11.glVertex3f((float) x, (float) (y + 1), (float) (z + 1));
        GL11.glVertex3f((float) x, (float) (y + 1), (float) (z + 1));
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) (z + 1));
        GL11.glVertex3f((float) x, (float) y, (float) z);
        GL11.glVertex3f((float) x, (float) (y + 1), (float) z);
        GL11.glVertex3f((float) x, (float) y, (float) (z + 1));
        GL11.glVertex3f((float) x, (float) (y + 1), (float) (z + 1));
        GL11.glVertex3f((float) (x + 1), (float) y, (float) z);
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) z);
        GL11.glVertex3f((float) (x + 1), (float) y, (float) (z + 1));
        GL11.glVertex3f((float) (x + 1), (float) (y + 1), (float) (z + 1));
    }
}

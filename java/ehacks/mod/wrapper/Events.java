package ehacks.mod.wrapper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Module;
import ehacks.api.module.ModuleController;
import ehacks.mod.commands.ConsoleInputGui;
import ehacks.mod.main.Main;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.modulesystem.classes.Forcefield;
import ehacks.mod.modulesystem.classes.KillAura;
import ehacks.mod.modulesystem.classes.MobAura;
import ehacks.mod.modulesystem.classes.ProphuntAura;
import ehacks.mod.modulesystem.classes.SeeHealth;
import ehacks.mod.modulesystem.classes.TriggerBot;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import java.awt.Color;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Events {

    public static boolean cheatEnabled = true;
    FontRenderer fontRender;
    private final boolean[] keyStates;

    public Events() {
        this.fontRender = Wrapper.INSTANCE.fontRenderer();
        this.keyStates = new boolean[256];
    }
    
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (!cheatEnabled) {
            return true;
        }
        boolean ok = true;
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            ok &= mod.onPacket(packet, side);
        }
        return ok;
    }

    private boolean ready = false;

    public boolean prevState = false;
    public boolean prevCState = false;

    @SubscribeEvent
    public void onTicks(TickEvent.ClientTickEvent event) {
        EHacksGui.clickGui.canInputConsole = Wrapper.INSTANCE.mc().currentScreen instanceof ConsoleInputGui;
        boolean nowState = Keyboard.isKeyDown(Keybinds.hideCheat);
        if (!prevState && nowState) {
            cheatEnabled = !cheatEnabled;
        }
        prevState = nowState;
        Wrapper.INSTANCE.mcSettings().viewBobbing = true;
        if (!cheatEnabled) {
            return;
        }
        boolean nowCState = Keyboard.isKeyDown(Keybinds.openConsole);
        if (!prevCState && nowCState && (Wrapper.INSTANCE.mc().currentScreen == null)) {
            Wrapper.INSTANCE.mc().displayGuiScreen(new ConsoleInputGui("/"));
        }
        prevCState = nowCState;
        Wrapper.INSTANCE.mcSettings().viewBobbing = false;
        if (Wrapper.INSTANCE.player() != null) {
            if (!ready) {
                new PacketHandler(this);
                ready = true;
            }
        } else {
            ready = false;
        }
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (mod.isActive() && Wrapper.INSTANCE.world() != null) {
                mod.onTicks();
            }
            if (!this.checkAndSaveKeyState(mod.getKeybind()) || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            mod.toggle();
            break;
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (!cheatEnabled) {
            return;
        }
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            mod.onWorldRender(event);
        }
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (!cheatEnabled) {
            return;
        }
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            mod.onMouse(event);
        }
    }

    @SubscribeEvent
    public void onLiving(LivingEvent.LivingUpdateEvent event) {
        if (!cheatEnabled) {
            return;
        }
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            mod.onLiving(event);
        }
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Text event) {
        if (!cheatEnabled) {
            return;
        }
        GLUtils.hasClearedDepth = false;
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            mod.onGameOverlay(event);
        }
        if (Wrapper.INSTANCE.mc().currentScreen == null) {
            int x2 = 8;
            int y2 = 7;
            GL11.glPushMatrix();
            GL11.glScalef((float) 1f, (float) 1f, (float) 1f);
            String Copyright1 = "EHacks Lite v" + Main.version;
            String Copyright2 = "by radioegor146";
            String Copyright3 = "[\u00a7eFFTeam\u00a7f]";
            ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
            this.fontRender.drawString(Copyright1, 2, 2, Events.rainbowEffect_Text(9999999L, 1.0f).getRGB());
            this.fontRender.drawStringWithShadow(Copyright2, get.getScaledWidth() - 2 - this.fontRender.getStringWidth(Copyright2), get.getScaledHeight() - this.fontRender.FONT_HEIGHT - 2, GLUtils.getColor(255, 255, 255));
            this.fontRender.drawStringWithShadow(Copyright3, get.getScaledWidth() - 2 - this.fontRender.getStringWidth(Copyright3), 2, GLUtils.getColor(255, 255, 255));
            GL11.glPopMatrix();
        }
        EHacksGui.clickGui.drawBack();
    }

    public static Color rainbowEffect_Text(long offset, float fade) {
        float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade, (float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (!cheatEnabled) {
            return;
        }
        if (!(KillAura.isActive || MobAura.isActive || ProphuntAura.isActive || Forcefield.isActive || TriggerBot.isActive || !Criticals.isActive || Wrapper.INSTANCE.player().isInWater() || Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) || Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) || !Wrapper.INSTANCE.player().onGround || !Wrapper.INSTANCE.mcSettings().keyBindAttack.getIsKeyPressed() || Wrapper.INSTANCE.mc().objectMouseOver == null || Wrapper.INSTANCE.mc().objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)) {
            event.setCanceled(true);
            Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
            Wrapper.INSTANCE.player().fallDistance = 0.1f;
            Wrapper.INSTANCE.player().onGround = false;
            event.setCanceled(false);
        }
        if (event.target instanceof EntityPlayer) {
            EntityPlayer e = (EntityPlayer) event.target;
            if (SeeHealth.isActive) {
                InteropUtils.log("Player health &e" + e.getCommandSenderName() + "&f: &e" + e.getHealth(), "SeeHealth");
            }
        }
    }

    public boolean checkAndSaveKeyState(int key) {
        if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return false;
        }
        if (Keyboard.isKeyDown((int) key) != this.keyStates[key]) {
            this.keyStates[key] = !this.keyStates[key];
            return this.keyStates[key];
        }
        return false;
    }

    @SubscribeEvent
    public void onGuiScreenDraw(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.gui instanceof GuiMainMenu) {
            GuiMainMenu mainMenu = (GuiMainMenu) event.gui;
            ReflectionHelper.setPrivateValue(GuiMainMenu.class, mainMenu, "Fucked by radioegor146", Mappings.splashText);
        }
    }
}

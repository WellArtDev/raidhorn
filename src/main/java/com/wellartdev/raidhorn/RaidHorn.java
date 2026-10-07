package com.wellartdev.raidhorn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;

/**
 * Bad Omen raid warning: warns the player the instant the Bad Omen
 * effect is gained.
 *
 * Bad Omen is what triggers a raid when you walk into a village — and
 * vanilla gives you no obvious heads-up that you carry it. RaidHorn
 * watches the player's effect list, and on the rising edge of Bad Omen
 * it blasts a war-horn sound and flashes a big red warning above the
 * hotbar for about five seconds. One shot per Bad Omen application;
 * the effect wearing off re-arms the warning for the next time.
 *
 * Client-side only. No mixins, no networking, nothing persisted.
 */
public class RaidHorn implements ClientModInitializer {
    public static final String MOD_ID = "raidhorn";

    /** Warning text shown above the hotbar while the alarm is active. */
    private static final String WARNING_TEXT = "RAID IMMINENT — Bad Omen!";

    /** How long the HUD warning stays visible after the alarm fires (ticks). */
    private static final int WARNING_DURATION_TICKS = 100;

    /** Red text color for the warning. */
    private static final int WARNING_COLOR = 0xFF5555;

    // Rising-edge detector: true while Bad Omen is currently active.
    private boolean hadOmen = false;

    // Tick count when the alarm last fired; -1 while inactive.
    private int alarmTick = -1;

    private int tick = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "raid_horn"),
                (extractor, deltaTracker) -> {
                    if (alarmTick < 0) {
                        return;
                    }
                    Minecraft mc = Minecraft.getInstance();
                    int x = (mc.getWindow().getGuiScaledWidth() - mc.font.width(WARNING_TEXT)) / 2;
                    int y = mc.getWindow().getGuiScaledHeight() - 70;
                    extractor.text(mc.font, WARNING_TEXT, x, y, WARNING_COLOR);
                });
    }

    private void onTick(Minecraft mc) {
        tick++;

        // Hide the warning once its display time has elapsed.
        if (alarmTick >= 0 && tick - alarmTick >= WARNING_DURATION_TICKS) {
            alarmTick = -1;
        }

        if (mc.player == null || tick % 5 != 0) {
            return;
        }

        boolean hasOmen = mc.player.hasEffect(MobEffects.BAD_OMEN);
        if (hasOmen && !hadOmen) {
            // Rising edge: Bad Omen was just applied. Blast the war horn
            // and light up the HUD warning.
            mc.getSoundManager().play(
                    SimpleSoundInstance.forUI(SoundEvents.RAID_HORN, 1.0f));
            alarmTick = tick;
        }
        hadOmen = hasOmen;
    }
}

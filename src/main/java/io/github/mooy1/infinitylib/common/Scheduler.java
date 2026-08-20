package io.github.mooy1.infinitylib.common;

import java.util.concurrent.TimeUnit;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.experimental.UtilityClass;

import org.bukkit.Bukkit;

import io.github.mooy1.infinitylib.core.AbstractAddon;

/**
 * A class for scheduling tasks
 *
 * Folia-compatible: detects Folia at runtime and routes tasks through
 * Folia's GlobalRegionScheduler / AsyncScheduler instead of the classic
 * Bukkit scheduler, which Folia does not support (throws
 * UnsupportedOperationException). On regular Paper/Spigot this behaves
 * exactly as before.
 *
 * @author Mooy1
 */
@UtilityClass
@ParametersAreNonnullByDefault
public final class Scheduler {

    private static final boolean FOLIA = isFolia();

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void run(Runnable runnable) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().execute(AbstractAddon.instance(), runnable);
        } else {
            Bukkit.getScheduler().runTask(AbstractAddon.instance(), runnable);
        }
    }

    public static void runAsync(Runnable runnable) {
        if (FOLIA) {
            Bukkit.getAsyncScheduler().runNow(AbstractAddon.instance(), task -> runnable.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(AbstractAddon.instance(), runnable);
        }
    }

    public static void run(int delayTicks, Runnable runnable) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().runDelayed(AbstractAddon.instance(), task -> runnable.run(), Math.max(1, delayTicks));
        } else {
            Bukkit.getScheduler().runTaskLater(AbstractAddon.instance(), runnable, delayTicks);
        }
    }

    public static void runAsync(int delayTicks, Runnable runnable) {
        if (FOLIA) {
            Bukkit.getAsyncScheduler().runDelayed(AbstractAddon.instance(), task -> runnable.run(), ticksToMillis(delayTicks), TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getScheduler().runTaskLaterAsynchronously(AbstractAddon.instance(), runnable, delayTicks);
        }
    }

    public static void repeat(int intervalTicks, Runnable runnable) {
        repeat(intervalTicks, 1, runnable);
    }

    public static void repeatAsync(int intervalTicks, Runnable runnable) {
        repeatAsync(intervalTicks, 1, runnable);
    }

    public static void repeat(int intervalTicks, int delayTicks, Runnable runnable) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().runAtFixedRate(AbstractAddon.instance(), task -> runnable.run(),
                    Math.max(1, delayTicks), Math.max(1, intervalTicks));
        } else {
            Bukkit.getScheduler().runTaskTimer(AbstractAddon.instance(), runnable, delayTicks, Math.max(1, intervalTicks));
        }
    }

    public static void repeatAsync(int intervalTicks, int delayTicks, Runnable runnable) {
        if (FOLIA) {
            Bukkit.getAsyncScheduler().runAtFixedRate(AbstractAddon.instance(), task -> runnable.run(),
                    ticksToMillis(delayTicks), ticksToMillis(Math.max(1, intervalTicks)), TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getScheduler().runTaskTimerAsynchronously(AbstractAddon.instance(), runnable, delayTicks, Math.max(1, intervalTicks));
        }
    }

    private static long ticksToMillis(int ticks) {
        return Math.max(1, ticks) * 50L;
    }

}

package com.fastasyncworldedit.bukkit.util.scheduler;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public record FoliaScheduler(Plugin plugin) implements Scheduler {

    @Override
    public void runTaskTimer(Plugin plugin, Runnable runnable, long delay, long period) {
        if (delay == 0) {
            plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
            if (period > 0) {
                plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), (int) period, (int) period);
            }
        } else {
            plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), (int) delay, (int) period);
        }
    }

    @Override
    public void runTask(Plugin plugin, Runnable runnable) {
        plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
    }

    @Override
    public void runTaskAsynchronously(Plugin plugin, Runnable runnable) {
        plugin.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> runnable.run());
    }

    @Override
    public void runTaskLater(Plugin plugin, Runnable runnable, long delay) {
        if (delay == 0) {
            plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
        } else {
            plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay);
        }
    }

    @Override
    public void runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long delay) {
        if (delay == 0) {
            plugin.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> runnable.run());
        } else {
            plugin.getServer().getAsyncScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay * 50L, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void runTaskTimer(Runnable runnable, long delay, long period) {
        plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), (int) delay, (int) period);
    }

    @Override
    public void runTask(Runnable runnable) {
        plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
    }

    @Override
    public void runTaskAsynchronously(Runnable runnable) {
        plugin.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> runnable.run());
    }

    @Override
    public void runTaskLater(Runnable runnable, long delay) {
        if (delay == 0) {
            plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
        } else {
            plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay);
        }
    }

    @Override
    public void runTaskLaterAsynchronously(Runnable runnable, long delay) {
        if (delay == 0) {
            plugin.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> runnable.run());
        } else {
            plugin.getServer().getAsyncScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay * 50L, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void scheduleSyncDelayedTask(Plugin plugin, Runnable runnable, long delay) {
        if (delay == 0) {
            plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
        } else {
            plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay);
        }
    }

    @Override
    public CancellableTask runTaskCancellable(Plugin plugin, Runnable runnable) {
        ScheduledTask task = plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
        return new FoliaCancellableTask(task);
    }

    @Override
    public CancellableTask runTaskLaterCancellable(Plugin plugin, Runnable runnable, long delay) {
        ScheduledTask task = delay == 0
                ? plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run())
                : plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay);
        return new FoliaCancellableTask(task);
    }

    @Override
    public CancellableTask runTaskTimerCancellable(Plugin plugin, Runnable runnable, long delay, long period) {
        ScheduledTask task = plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), (int) delay, (int) period);
        return new FoliaCancellableTask(task);
    }

    @Override
    public CancellableTask runTaskCancellable(Runnable runnable) {
        ScheduledTask task = plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run());
        return new FoliaCancellableTask(task);
    }

    @Override
    public CancellableTask runTaskLaterCancellable(Runnable runnable, long delay) {
        ScheduledTask task = delay == 0
                ? plugin.getServer().getGlobalRegionScheduler().run(plugin, scheduledTask -> runnable.run())
                : plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delay);
        return new FoliaCancellableTask(task);
    }

    @Override
    public CancellableTask runTaskTimerCancellable(Runnable runnable, long delay, long period) {
        ScheduledTask task = plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), (int) delay, (int) period);
        return new FoliaCancellableTask(task);
    }

    private static class FoliaCancellableTask implements CancellableTask {

        private final ScheduledTask task;
        private volatile boolean cancelled = false;

        public FoliaCancellableTask(ScheduledTask task) {
            this.task = task;
        }

        @Override
        public void cancel() {
            if (!cancelled) {
                cancelled = true;
                if (task != null) {
                    task.cancel();
                }
            }
        }

        @Override
        public boolean isCancelled() {
            return cancelled || (task != null && task.isCancelled());
        }
    }
}

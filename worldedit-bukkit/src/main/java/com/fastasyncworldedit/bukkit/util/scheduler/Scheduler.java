package com.fastasyncworldedit.bukkit.util.scheduler;

import org.bukkit.plugin.Plugin;

public interface Scheduler {

    void runTaskTimer(Plugin plugin, Runnable runnable, long delay, long period);

    void runTask(Plugin plugin, Runnable runnable);

    void runTaskAsynchronously(Plugin plugin, Runnable runnable);

    void runTaskLater(Plugin plugin, Runnable runnable, long delay);

    void runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long delay);

    void runTaskTimer(Runnable runnable, long delay, long period);

    void runTask(Runnable runnable);

    void runTaskAsynchronously(Runnable runnable);

    void runTaskLater(Runnable runnable, long delay);

    void runTaskLaterAsynchronously(Runnable runnable, long delay);

    void scheduleSyncDelayedTask(Plugin plugin, Runnable runnable, long delay);

    CancellableTask runTaskCancellable(Plugin plugin, Runnable runnable);

    CancellableTask runTaskLaterCancellable(Plugin plugin, Runnable runnable, long delay);

    CancellableTask runTaskTimerCancellable(Plugin plugin, Runnable runnable, long delay, long period);

    CancellableTask runTaskCancellable(Runnable runnable);

    CancellableTask runTaskLaterCancellable(Runnable runnable, long delay);

    CancellableTask runTaskTimerCancellable(Runnable runnable, long delay, long period);

    interface CancellableTask {
        void cancel();

        boolean isCancelled();
    }
}

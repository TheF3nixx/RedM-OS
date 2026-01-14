package System;

import System.Subsystems.SubsystemManager;
import System.Subsystems.Scheduler;

public class SystemClock implements Runnable {

    private boolean running = false;
    private Thread thread;

    public void run() {
        if (running) return;
        running = true;

        thread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(100); // 100 ms = 1 tick lógico
                } catch (InterruptedException e) {
                    return;
                }

                Scheduler s = (Scheduler) SubsystemManager.get("Scheduler");
                if (s != null && s.isRunning()) {
                    s.tick();
                }
            }
        });

        thread.start();
    }

    public void stop() {
        running = false;
    }

    public static void pulse() {
        Scheduler s = (Scheduler) SubsystemManager.get("Scheduler");
        if (s != null && s.isRunning()) {
            s.tick();
        }
    }
}


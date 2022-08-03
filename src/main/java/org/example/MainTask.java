package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainTask {
    private static final int NUM_TASKS = 10;

    public static void main(String[] args) {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(NUM_TASKS);
        List<WorkerTask> tasks = new ArrayList<>();

        for (int i = 0; i < NUM_TASKS; i++) {
            int processingSeconds = (int)(Math.random() * 10);
            tasks.add(new WorkerTask(i, processingSeconds, startSignal, doneSignal));
        }

        tasks.forEach(workerTask -> new Thread(workerTask).start());

        try {
            startSignal.countDown();
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Aggregate results of worker task processing.
        int aggregatedResult = tasks.stream().mapToInt(WorkerTask::getProcessingResult).sum();
        System.out.println("Aggregated processing result: " + aggregatedResult);
    }
}

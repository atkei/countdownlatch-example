package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class WorkerTask implements Runnable {
    private final int taskId;
    private final int processingSeconds;
    private final CountDownLatch startSignal;

    private final CountDownLatch latch;
    private int processingResult;

    public int getProcessingResult() {
        return processingResult;
    }

    public WorkerTask(int taskId, int processingSeconds, CountDownLatch startSignal, CountDownLatch latch) {
        this.taskId = taskId;
        this.processingSeconds = processingSeconds;
        this.startSignal = startSignal;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            System.out.println("-- Started worker task. taskId=" + taskId + ", processingSeconds=" + processingSeconds);
            TimeUnit.SECONDS.sleep(processingSeconds * 2L);  // Dummy heavy processing.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processingResult = taskId * processingSeconds;  // Set dummy processing result.

        System.out.println("-- Complete worker task. taskId=" + taskId + ", processingResult=" + getProcessingResult());

        latch.countDown();
    }
}

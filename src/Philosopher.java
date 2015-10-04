package com.company;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread{
    private final Random random;
    private int number;
    private Logger log;
    private int timeOfThinkingAboutFood;
    private int timeOfEating;
    private Semaphore semaphore;
    private List<Fork> forks;
    private boolean needStop = false;

    private int eatCount = 0;
    private int totalWaitTime = 0;
    public Philosopher(int number, Logger log, int timeOfThinkingAboutFood, int timeOfEating, Semaphore semaphore, List<Fork> forks){
        this.number = number;
        this.log = log;
        this.timeOfThinkingAboutFood = timeOfThinkingAboutFood;
        this.timeOfEating = timeOfEating;
        this.semaphore = semaphore;
        this.forks = forks;

        this.random = new Random();
    }

    public void StopExecution(){
        this.needStop = true;
    }

    @Override
    public void run() {
        while (true) {
            if (!needStop){
                try {
                    int waitTime = random.nextInt(timeOfThinkingAboutFood);
                    sleep(waitTime);
                    totalWaitTime += waitTime;
                    GetFork();
                } catch (InterruptedException e) {
                }
            }
            else return;
        }
    }

    public void PrintStatistics(){
        log.log("[" + number + "] "+ eatCount + " " + totalWaitTime);
    }

    private void GetFork() throws InterruptedException {
        semaphore.acquire();
        int leftFork = number;
        int rightFork = number == 0 ? forks.size() -1 : 0;
        if (!forks.get(leftFork).getState() && !forks.get(rightFork).getState()){
            forks.get(leftFork).setState(true);
            log.log("[" + number + "] taken left fork" );
            forks.get(rightFork).setState(true);
            log.log("[" + number + "] taken right fork" );
            log.log("[" + number +"] Philosopher is eating");
            eatCount++;
            sleep(random.nextInt(timeOfEating));
            forks.get(leftFork).setState(false);
            forks.get(rightFork).setState(false);
        }

        semaphore.release();
    }
}

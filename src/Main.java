package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
public class Main {
	public static void main(String[] args) throws InterruptedException {
		int countOfPhilosophers = Integer.parseInt(args[0]);
		int timeLimit = Integer.parseInt(args[1]);//seconds
		int maxTimeOfThinking = Integer.parseInt(args[2]);//milliseconds
		int maxTimeOfEating = Integer.parseInt(args[3]);//milliseconds
		boolean writeInConsoleFlag = "1".equals(args[4]);
		List<Fork> forks = new ArrayList<>(countOfPhilosophers);
		List<Philosopher> threads = new ArrayList<>(countOfPhilosophers);
		Logger logger = new Logger(writeInConsoleFlag);
		logger.log("The program is start!");
		Semaphore semaphore = new Semaphore(countOfPhilosophers / 2);

		for (int i = 0; i < countOfPhilosophers; i++) {
			forks.add(new Fork());
			threads.add(new Philosopher(i, logger, maxTimeOfThinking, maxTimeOfEating, semaphore, forks));
		}
		for (int i = 0; i < countOfPhilosophers; i++) {
			threads.get(i).start();
		}
		Thread.sleep(timeLimit * 1000);

		for (int i = 0; i < countOfPhilosophers; i++) {
			threads.get(i).StopExecution();
		}

		Thread.sleep(maxTimeOfEating);
		for (int i = 0; i < countOfPhilosophers; i++) {
			threads.get(i).PrintStatistics();
		}
	}
}

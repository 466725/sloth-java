package com.interview.synchronize;

public class FiveTimesPrinter {
	synchronized void printFiveTimes(int n) {// synchronized method
		for (int i = 1; i <= 5; i++) {
			System.out.println(n * i);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}

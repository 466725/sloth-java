package com.interview.synchronize;

class ThreadSmallNumber extends Thread {
	FiveTimesPrinter printer;

	ThreadSmallNumber(FiveTimesPrinter printer) {
		this.printer = printer;
	}

	public void run() {
		printer.printFiveTimes(10);
	}
}
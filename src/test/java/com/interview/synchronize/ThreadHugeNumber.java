package com.interview.synchronize;

class ThreadHugeNumber extends Thread {
	FiveTimesPrinter printer;

	ThreadHugeNumber(FiveTimesPrinter printer) {
		this.printer = printer;
	}

	public void run() {
		printer.printFiveTimes(1000000);
	}
}
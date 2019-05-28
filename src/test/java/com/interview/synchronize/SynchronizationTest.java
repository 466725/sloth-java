package com.interview.synchronize;

public class SynchronizationTest {
	public static void main(String args[]) {
		FiveTimesPrinter printer = new FiveTimesPrinter();// only one object
		ThreadSmallNumber smallNumberPrint = new ThreadSmallNumber(printer);
		ThreadHugeNumber hugeNumberPrint = new ThreadHugeNumber(printer);
		smallNumberPrint.start();
		hugeNumberPrint.start();
		smallNumberPrint = new ThreadSmallNumber(printer);
		hugeNumberPrint = new ThreadHugeNumber(printer);
		smallNumberPrint.start();
		hugeNumberPrint.start();
	}
}
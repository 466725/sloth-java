package com.interview.synchronization;

class CustomerTest {
	public static void main(String args[]) {
		final BankAccount bankAccount = new BankAccount();
		new Thread() {
			public void run() {
				bankAccount.withdraw(15000);
			}
		}.start();
		new Thread() {
			public void run() {
				bankAccount.deposit(10000);
			}
		}.start();
	}
}
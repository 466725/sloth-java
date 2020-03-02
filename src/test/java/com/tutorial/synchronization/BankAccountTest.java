package com.tutorial.synchronization;

class BankAccountTest {
	public static void main(String args[]) {
		final BankAccount bankAccount = new BankAccount();
		new Thread() {
			public void run() {
				bankAccount.withdraw(15000);
			}
		}.start();
		new Thread() {
			public void run() {
				bankAccount.deposit(3000);
			}
		}.start();
		new Thread() {
			public void run() {
				bankAccount.deposit(3000);
			}
		}.start();
		new Thread() {
			public void run() {
				bankAccount.deposit(10000);
			}
		}.start();
	}
}
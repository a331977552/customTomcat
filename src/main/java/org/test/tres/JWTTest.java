package org.test.tres;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class JWTTest {
	static int b = 0;
	static ReentrantLock reentLock = new ReentrantLock();
	static Condition newCondition = reentLock.newCondition();
	public static void main(String[] args) {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		long nanoTime = System.currentTimeMillis();
		IntStream.range(0, 10).forEach(i -> {
			newFixedThreadPool.submit(() -> {
				doSth();
			});
		});
		System.out.println("shutdown");
		newFixedThreadPool.shutdown();
		while (!newFixedThreadPool.isTerminated()) {
		}
		System.out.println(System.currentTimeMillis() - nanoTime);
	}
	public static void doSth() {

		try {

			reentLock.lockInterruptibly();
			newCondition.await();
			System.out.println(Thread.currentThread().getName()+" do business"+reentLock.getHoldCount());

			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			reentLock.unlock();
		}
	}


}

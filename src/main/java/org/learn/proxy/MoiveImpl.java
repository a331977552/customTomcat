package org.learn.proxy;

public class MoiveImpl implements Movive{

	@Override
	public void play(String name) {
		System.out.println("play ..."+ name);
		throw new IllegalArgumentException("test");
	}

	@Override
	public void stop() {
		System.out.println("stop ...");

	}

}

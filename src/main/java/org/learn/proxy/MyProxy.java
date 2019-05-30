package org.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler {


	private MoiveImpl impl;
	private HEHE sss;
	public MyProxy(String haha) {
		super();
		impl=new MoiveImpl();
		// TODO Auto-generated constructor stub
	sss=new SSS();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(args==null) {
			args=new Object[0];
		}
		Class[] class1=new Class[args.length];
		int i=0;
		for (Object object : args) {
			class1[i]=object.getClass();
		i++;
		}
		if(method.getDeclaringClass().isAssignableFrom(impl.getClass())) {
			Method method2 = impl.getClass().getMethod(method.getName(),class1);
			try {

				return method2.invoke(impl, args);
			}catch (Exception e) {
				e.getCause().printStackTrace();
			System.out.println("got error "+ e);
			}
		}else if(method.getDeclaringClass().isAssignableFrom(sss.getClass())) {
			Method method2 = sss.getClass().getMethod(method.getName(),class1);
			return method2.invoke(sss, args);
		}

		return null;
	}

public static void main(String[] args) {
	Object newProxyInstance = Proxy.newProxyInstance(MoiveImpl.class.getClassLoader(),new Class[] {Movive.class,HEHE.class},new MyProxy("haha"));
	Movive moive = (Movive) newProxyInstance;
	moive.play("haha");
	moive.stop();
	HEHE hehe=(HEHE)newProxyInstance;
	System.out.println(hehe.dddd("aaaa"));

}
}


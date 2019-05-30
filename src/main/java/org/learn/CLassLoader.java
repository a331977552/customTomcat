package org.learn;

import org.test.tres.TestClassLoaderInter;

public class CLassLoader {

	public static final String classDic="/home/users/cody/classLoaderTest";
//	public static final FileSystemClassLoader
	public static void main(String[] args) {
		ClassLoader classLoader = CLassLoader.class.getClassLoader();
		try {
			FileSystemClassLoader classLoader2=new FileSystemClassLoader(classDic);
			FileSystemClassLoader classLoader3=new FileSystemClassLoader(classDic);

			Class<?> loadClass = classLoader2.loadClass("org.test.tres.TestClassLoader");
			Class<?> loadClass2 = classLoader3.loadClass("org.test.tres.TestClassLoader");

			TestClassLoaderInter newInstance = (TestClassLoaderInter) loadClass.newInstance();
			TestClassLoaderInter newInstance2 = (TestClassLoaderInter) loadClass2.newInstance();

			newInstance.setTestClassload(newInstance2);
			newInstance.print();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

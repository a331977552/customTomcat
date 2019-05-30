package org.test.tres;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements List<T> {
	private Object[] array;
	private static final int DEFAULT_CAPACITY = 4;
	private static final int MAX_CAPACITY = Integer.MAX_VALUE;
	private int currentCapacity;
	private int size;
	private int modCount;

	@Override
	public int size() {
		return size;
	}

	public MyArrayList() {
		super();
		array = new Object[DEFAULT_CAPACITY];
		currentCapacity = array.length;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		for (Object object : array) {
			if (object == o || object != null && object.equals(object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Inter();
	}
	private class Inter implements Iterator<T>{

		private int cursor=0;
		private int expectedModCount;

		public Inter() {
			super();
			cursor=-1;
			expectedModCount=modCount;
		}

		@Override
		public boolean hasNext() {
			return cursor<(size-1);
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {

			checkForComodification();
			if(cursor>=size) {
				throw new NoSuchElementException();
			}
			Object[] eles = MyArrayList.this.array;
			if(cursor>eles.length) {
				throw new ConcurrentModificationException("2");
			}
			cursor=cursor+1;
			return (T) array[cursor];
		}

		private final void checkForComodification() {
			if(expectedModCount!=modCount)
				throw new ConcurrentModificationException("1");
		}

	}

	@Override
	public Object[] toArray() {
		Object[] objects = new Object[size];
		System.arraycopy(array, 0, objects, 0, size);
		return objects;
	}

	@Override
	public <T> T[] toArray(T[] a) {

		return null;
	}

	@Override
	public boolean add(T e) {
		ensureSize();
		Object[] array = this.array;
		if (size > MAX_CAPACITY)
			return false;
		array[size++] = e;
		modCount++;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (Object object : array) {
			if (object == null)
				break;
			builder.append(object);
			builder.append(", ");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("]");

		return builder.toString();
	}

	public static void main(String[] args) {
		MyArrayList<String> arrayList = new MyArrayList<>();
		arrayList.add("123");
		arrayList.add("1234");
		arrayList.add("1235");
		arrayList.add("1236");
		arrayList.add("1237");
		System.out.println(arrayList);
		System.out.println(arrayList.currentCapacity);
		Object[] array2 = arrayList.toArray();
		String string = Arrays.toString(array2);
		System.out.println(string);
		arrayList.remove("123");
		System.out.println(arrayList);
		String string2 = arrayList.get(2);
		System.out.println(string2);
		arrayList.add(1,"haha");
		System.out.println(arrayList);

		Iterator<String> iterator = arrayList.iterator();
		String next = iterator.next();
		System.out.println(next);
		String next2 = iterator.next();
		System.out.println(next2);

	}

	private void ensureSize() {
		if (size >= array.length) {
			enlargeArray();
		}
	}

	private void enlargeArray() {
		if (currentCapacity >= MAX_CAPACITY)
			throw new IllegalStateException("the size of ArrayList is too big!");
		currentCapacity = currentCapacity << 1;
		if (currentCapacity > MAX_CAPACITY)
			currentCapacity = MAX_CAPACITY;
		Object[] arr = new Object[currentCapacity];
		System.arraycopy(array, 0, arr, 0, array.length);
		array = arr;
		System.out.println("enlarge array: " + currentCapacity);

	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		Object[] arr = this.array;
		for (int i = 0; i < size; i++) {
			Object ob = arr[i];
			if (ob == o || ob != null && ob.equals(ob)) {
				System.arraycopy(array, i + 1, array, i, size - i - 1);
				array[--size] = null;
				return true;
			}
		}
		modCount++;
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		size = 0;
		modCount++;
	}

	@Override
	public T get(int index) {
		rangeCheck(index);
		return (T) array[index];
	}

	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	@Override
	public T set(int index, T element) {
		rangeCheck(index);
		Object oldValue = array[index];
		array[index] = element;
		return (T) oldValue;

	}

	@Override
	public void add(int index, T element) {
		rangeCheck(index);
		ensureSize();
		System.arraycopy(array, index, array,index+1,size-index);
		array[index] = element;
		modCount++;
	}

	@Override
	public T remove(int index) {
		modCount++;
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}

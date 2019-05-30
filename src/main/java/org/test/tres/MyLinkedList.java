package org.test.tres;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E>{

	private int size;
	private int modCount;
	private Node first;
	private Node last;
	private class Node<E>{
		private Node<E> next;
		private Node<E> pre;

		private E value;
		public Node(Node<E> pre,Node<E> next, E value) {
			super();
			this.pre=pre;
			this.next = next;
			this.value = value;
		}

	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E e) {
		if(first==null) {
			first=new Node<>(null,null,e);
			last=first;
			return true;
		}else {
			last.next=new Node<E>(last,null, e);
			last=last.next;
			return true;
		}
	}

	@Override
	public boolean remove(Object o) {
		for (Node no=first; no!=null; no=no.next) {
			if(no.value==o || no.value!=null && no.equals(o)) {
				unkLink(no);
				return  true;
			}
		}
		return false;
	}

	private Object unkLink(Node no) {
		Node pre = no.pre;
		Node next = no.next;
		//check header firstly
		if(pre==null) {
			first=no.next;
		}
		else {
			pre.next=next;
			no.pre=null;
		}
		//check tail
		if(next==null) {
			last=pre;
		}else {
			next.pre=pre;
			no.next=null;
		}
		modCount++;
		size--;
		return no.value;

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub

	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
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
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}

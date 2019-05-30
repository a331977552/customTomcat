package org.test.tres;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MyHashMap<K, V> implements Map<K, V>{
	private static final int DEFAULT_CAPACITY = 16;
	private static final int MAX_CAPACITY=1<<30;
	private int current_capacity = 0;

	private Node<K, V>[] array;
	private int size;

	private class Node<K, V> {
		private K key;
		private V value;
		private Node next;
		private int hash;

		public Node(K key, V value, Node next,int hash) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
			this.hash=hash;
		}

	}

	public MyHashMap() {
		this(DEFAULT_CAPACITY);
	}

	public MyHashMap(int capacity) {
		super();
		current_capacity = arraySizeFor(capacity);
		array = new Node[current_capacity];
	}

	private int arraySizeFor(int capacity) {
		int numberOfLeadingZeros = Integer.numberOfLeadingZeros(capacity - 1);
		int value = (-1 >>> numberOfLeadingZeros);
		return value + 1;
	}

	private int hash(Object key) {
		if (key == null)
			return 0;
		int h = key.hashCode();
		int lh = h >>> 16;
		h = h ^ lh;
		return h;
	}

	public V put(K key, V value) {
		Node<K, V>[] nodes = this.array;
		 int h = hash(key);
		int index =h& (array.length - 1);
		Node<K, V> node = nodes[index];
		if (node == null) {
			array[index] = new Node<K, V>(key, value, null,h);
			size++;
		} else {
			// found the existing key
			if (node.key == key || key != null && key.equals(node.key)) {
				node.value = value;
			} else {
				while (node.next != null) {
					node = node.next;
					// found the existing key
					if (node.key == key || key != null && key.equals(node.key)) {
						node.value = value;
						break;
					}
				}
				size++;
				node.next = new Node<K, V>(key, value, null,h);
			}
		}
		if(size>current_capacity) {
			resize();
		}
		return value;
	}
	private void resize() {
		int newCapacity=(current_capacity<<1);
		if(current_capacity>=MAX_CAPACITY) {
			return ;
		}
		current_capacity=newCapacity>MAX_CAPACITY?MAX_CAPACITY:newCapacity;
		Node<K,V>[] oldNodes=array;
		array=new Node[current_capacity];
		for (int i = 0; i < oldNodes.length; i++) {
			for (Node<K, V> no=oldNodes[i];no!=null;no=no.next) {
					int index=no.hash& (array.length - 1);


			}


		}


	}

	public V get(Object key) {
		Node<K, V>[] nodes = this.array;
		int index = hash(key);
		Node<K, V> node = nodes[index];
		if (node == null)
			return null;
		else {
			if (key == node.key || node.key.equals(key)) {
				return node.value;
			}
			while (node.next != null) {
				node = node.next;
				if (key == node.key || node.key.equals(key)) {
					return node.value;
				}
			}
		}
		System.out.println("element not found, key :" + key);
		return null;
	}

	public boolean containsKey(Object key) {
		Node<K, V>[] nodes = this.array;
		int index = hash(key);
		Node<K, V> node = nodes[index];
		if (node == null)
			return false;
		if (node.key == key || node.key != null && node.key.equals(key)) {
			return true;
		}
		while (node.next != null) {
			node = node.next;
			if (node.key == key || node.key != null && node.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsValue(Object value) {

		for (int i = 0; i < array.length; i++) {
			MyHashMap<K, V>.Node<K, V> node = array[i];
			if(node!=null) {
				for (Node<K,V> no=node;no!=null; no=no.next) {
					if(no!=null &&(no.value==value || no.value!=null&&no.value.equals(value))) {
						return true;
					}
				}
			}
		}

		return false;
	}


	public V remove(Object key) {
		Node<K, V>[] nodes = this.array;
		int index = hash(key);
		Node<K, V> selectedNode = nodes[index];
		if (selectedNode == null)
			return null;
		else {
			if (key == selectedNode.key || selectedNode.key.equals(key)) {
				nodes[index] = selectedNode.next;
				return selectedNode.value;
			}
			while (selectedNode.next != null) {
				Node<K, V> preNo = selectedNode;
				selectedNode = preNo.next;
				if (key == selectedNode.key || selectedNode.key.equals(key)) {
					preNo.next = selectedNode.next;
					selectedNode.next = null;
					return selectedNode.value;
				}
			}
		}
		System.out.println("element not found, key :" + key);
		return null;

	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (Node<K, V> node : array) {
			builder.append("{");
			if (node != null) {
				builder.append("{" + node.key + "=" + node.value + "}");
				while (node.next != null) {
					node = node.next;
					builder.append(",{" + node.key + "=" + node.value + "}");
				}
			}
			builder.append("}");
			builder.append(",");

		}

		builder.append("]");

		return builder.toString();
	}

	public void clear() {


	}
	public Set<K> keySet(){
		Set<K> set=new HashSet<>();
		for (int i = 0; i < array.length; i++) {
			MyHashMap<K, V>.Node<K, V> node = array[i];
			if(node!=null) {
				for (Node<K,V> no=node;no!=null; no=no.next) {
					set.add(no.key);
				}
			}
		}
		return set;

	}

	   public Collection<V> values(){
		   java.util.List<V> list=new ArrayList<>();
			for (int i = 0; i < array.length; i++) {
				MyHashMap<K, V>.Node<K, V> node = array[i];
				if(node!=null) {
					for (Node<K,V> no=node;no!=null; no=no.next) {
						list.add(no.value);
					}
				}
			}
			return list;
	   }
	public static void main(String[] args) {

//		Map<String, String> hashMap = new HashkMap<>();
		MyHashMap<String, String> myHashMap = new MyHashMap<>(8);
		System.out.println(myHashMap.current_capacity);
		for (int i = 0; i < 5; i++) {
			myHashMap.put("key" + i, "value" + i);
		}

		System.out.println(myHashMap.size);

		System.out.println(myHashMap);
		myHashMap.put("key" + 0, "new value");

		System.out.println(myHashMap.size);
		System.out.println(myHashMap);

		myHashMap.put(null, "null value");

		System.out.println(myHashMap.toString());
		// String string = myHashMap.get("key11");

		// System.out.println(string);
		String nullValue = myHashMap.get(null);

		System.out.println(nullValue);
		String nullValue2 = myHashMap.remove(null);

		System.out.println(nullValue2);
		System.out.println(myHashMap.toString());

		boolean containsKey0 = myHashMap.containsKey("key0");
		System.out.println(containsKey0);
		boolean containsKey11 = myHashMap.containsKey("key11");
		System.out.println(containsKey11);

		boolean value2 = myHashMap.containsValue("value2");
		System.out.println(value2);
		boolean value111 = myHashMap.containsValue("value111");
		System.out.println(value111);

		Set<String> keySet = myHashMap.keySet();
		System.out.println(keySet);

		Collection<String> values = myHashMap.values();
		System.out.println(values);

	}

	@Test
	public void testArrSizeFor() {
		MyHashMap myHashMap = new MyHashMap(37);
		System.out.println(myHashMap.current_capacity);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}

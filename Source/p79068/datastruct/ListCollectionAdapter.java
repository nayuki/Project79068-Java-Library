package p79068.datastruct;

import java.util.Iterator;
import p79068.lang.NullChecker;


public final class ListCollectionAdapter<E> implements Collection<E> {
	
	private List<E> list;
	
	
	
	public ListCollectionAdapter(List<E> list) {
		this.list = list;
	}
	
	
	
	public int size() {
		return list.length();
	}
	
	
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (int i = 0; i < list.length(); i++) {
			if (o.equals(list.getAt(i)))
				return true;
		}
		return false;
	}
	
	
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		NullChecker.check(o);
		for (int i = 0; i < list.length(); i++) {
			if (o.equals(list.getAt(i)))
				count++;
		}
		return count;
	}
	
	
	public void add(E obj) {
		NullChecker.check(obj);
		list.append(obj);
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		NullChecker.check(coll);
		for (E obj : coll)
			list.append(obj);
	}
	
	
	public E remove(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i))) {
				E result = list.getAt(i);
				list.removeAt(i);
				return result;
			}
		}
		return null;
	}
	
	
	public int removeAllOf(Object obj) {
		NullChecker.check(obj);
		int count = 0;
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i))) {
				list.removeAt(i);
				count++;
			}
		}
		return count;
	}
	
	
	public int removeAll(Collection<?> coll) {
		NullChecker.check(coll);
		int count = 0;
		for (Object obj : coll) {
			if (remove(obj) != null)
				count++;
		}
		return count;
	}
	
	
	public void clear() {
		list.clear();
	}
	
	
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	
	public boolean isSubsetOf(Collection<?> coll) {
		NullChecker.check(coll);
		for (E obj : this) {
			if (count(obj) > coll.count(obj))
				return false;
		}
		return true;
	}
	
	
	public boolean isSupersetOf(Collection<?> coll) {
		NullChecker.check(coll);
		for (Object obj : coll) {
			if (count(obj) < coll.count(obj))
				return false;
		}
		return true;
	}
	
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof Collection))
			return false;
		else {
			Collection<?> coll = (Collection<?>)other;
			return isSubsetOf(coll) && isSupersetOf(coll);
		}
	}
	
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < list.length(); i++)
			hash += list.getAt(i).hashCode();
		return hash;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Collection [");
		for (int i = 0; i < list.length(); i++) {
			sb.append(list.getAt(i));
			if (i != 0)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
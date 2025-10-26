
package saud.csc212;


public class LinkedList<T> {
    private Node<T> head;
	private Node<T> current;

	public LinkedList () {
		head = current = null;
	}

	public boolean empty () {
		return head == null;
	}

	public boolean last () {
		return !empty() && current.getNext()== null ;
	}
        public boolean first() {
		return current.getPrev() == null;
	}
        public void findPrevious() {
		current = current.getPrev(); }


        
        
        public boolean full () {
		return false;
	}
	public void findfirst () {
		current = head;
	}
	public void findnext () {
if(!empty() && !last()) current = current.getNext();
	}
	public T retrieve () {
return !empty() ? current.getData() : null ;
	}
public void update (T val) {
if(!empty()) current.setData(val);
 }
public void insert (T val) {
Node<T> tmp  = new Node<T>(val); ;
if (empty()) {
current = head = new Node<T> (val);
}
else {
tmp.setNext(current.getNext());
tmp.setPrev(current);
if(current.getNext() != null)
current.getNext().setPrev(tmp);
current.setNext(tmp);
current = tmp; }}

public void remove () {
if(current == head) { head = head.getNext();
if(head != null) head.setPrev(null);
		} else {
(current.getPrev()).setNext(current.getNext());
if(current.getNext() != null) (current.getNext()).setPrev(current.getPrev());
}
if(current.getNext() == null) current = head;
else
current = current.getNext();
	}}

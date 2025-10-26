
package saud.csc212;

/**
 *
 * @author huawei
 */
public class Node<T> {
    private T data;
	private Node<T> next;
        private Node<T> previous;


	public Node () {
		data = null;
		next = null;
                previous = null ;
	}

	public Node (T val) {
		data = val;
		next = null;
                previous= null ;
	}
        public T getData(){return this.data ;}
           public Node<T> getNext(){return this.next ;}
           public Node<T> getPrev(){return this.previous ;}
              public void setData(T d){ this.data = d;}
                            public void setNext(Node<T> d){ this.next = d;}
                            public void setPrev(Node<T> d){ this.previous = d;}

}

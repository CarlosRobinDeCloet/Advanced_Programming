import java.util.Comparator;

/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A basic singly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SinglyLinkedList<E> implements Cloneable 
{
	// Main method, to define a linked list and call the methods to be implemented
	public static void main(String[] args) 
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		for(int i = 0; i < 8; i++)
		{
			list.addLast(8-i);
		}
		System.out.println(list);
		System.out.println("get: " + list.get(4));
		
		list.removeFromEnd(2);
		System.out.println(list);
		
		list.reverse();
		System.out.println(list);
		
		list.moveBelowThresholdToEnd(Comparator.naturalOrder(), 6);
		System.out.println(list);
	
		SinglyLinkedList<Integer> other = new SinglyLinkedList<Integer>();
		for(int i = 11; i < 15; i++)
		{
			other.addLast(i);
		}
		System.out.println(other);
		list.alternatingCombine(other);
		System.out.println(list);
	}
	
	public E get(int index)
	{

		// returns last element
		if(index == size() -1){
			return last();
		}

		// finds node that relates to index position.
		Node node = head;
		for(int i = 0; i < index; i++){
			node = node.getNext();
		}

		E e = (E) node.getElement();
		return e;
	}
	
	public E removeFromEnd(int index)
	{
		// initializes search through node
		Node node = head;
		int properIndex = size() - 1 - index;

		// finds preceding node
		for(int i = 0; i < properIndex-1; i++){
			node = node.getNext();
		}

		Node nodeToRemove;
		Node nodeToSet;

		// if node is head node, remove first node and return its element.
		if(properIndex == 0){
			E e = first();
			removeFirst();
			return e;
		}

		// if node is tail node, remove last node and sets preciding node as tail node.
		if(index == 0){
			E e = last();
			node.setNext(null);
			tail = node;
			return e;
		}

		// removes middle node, sets subsequent node as new next node of preceding node.
		nodeToRemove = node.getNext();
		nodeToSet = nodeToRemove.getNext();
		node.setNext(nodeToSet);

		E e = (E) nodeToRemove.getElement();
		size --;

		return e;
	}
	
	public void reverse()
	{
		int sizeOfList = size();

		// adds all elements to the back of the LinkedList in reverse order
		for(int i = sizeOfList - 1; i >= 0; i--){
			addLast(get(i));
		}

		int updatedSizeOfList = size();

		// removes all nodes that weren't added in reverse order
		for(int j = 0; j < updatedSizeOfList - sizeOfList; j++){
			removeFirst();
		}
	}

	public void moveBelowThresholdToEnd(Comparator<E> comp, E threshold)
	{
		// initializes search, addedNumbers is counted to keep track of number of elements that need to be removed.
		int sizeOfList = size();
		int addedElements = 0;

		// adds all elements below threshold to the back of the LinkedList.
		for(int i = 0; i < sizeOfList; i ++){

			if(comp.compare(get(i), threshold) < 0){
				addLast(get(i));
				addedElements ++;
			}
		}

		// removes all elements below the threshold from the LinkedList. If a number is deleted, the index is updated
		// since elements shift forward 1 index. To not remove more elements than that were added, elements are only removed
		// if less elements are removed than elements were added.

		int numberOfRemovedElements = 0;
		for(int j = 0; j < sizeOfList; j++){
			if((comp.compare(get(j), threshold) < 0) && (numberOfRemovedElements < addedElements)){
				removeFromEnd(size()-1-j);
				j--;
				numberOfRemovedElements ++;
			}
		}
	}
	
	public void alternatingCombine(SinglyLinkedList<E> other) 
	{
		// Initializes the nodes from both Linkedlists.
		Node thisNode = this.head;
		Node otherNode = other.head;

		Node nextThisNode;
		Node nextOtherNode;

		while( thisNode != null && otherNode != null) {

			// Copies values of the next nodes of both LinkedList
			nextThisNode = thisNode.getNext();
			nextOtherNode = otherNode.getNext();

			// Sets nodes next node as node from other LinkedList
			thisNode.setNext(otherNode);
			otherNode.setNext(nextThisNode);

			// Restores the next node of the List as the next node in its own LinkedList.
			thisNode = nextThisNode;
			otherNode = nextOtherNode;
		}
	}
	
	// The code below is the source code as obtained from the book's companion site
	private static class Node<E> 
	{
	    /** The element stored at this node */
	    private E element;            // reference to the element stored at this node

	    /** A reference to the subsequent node in the list */
	    private Node<E> next;         // reference to the subsequent node in the list

	    /**
	     * Creates a node with the given element and next node.
	     *
	     * @param e  the element to be stored
	     * @param n  reference to a node that should follow the new node
	     */
	    public Node(E e, Node<E> n) {
	      element = e;
	      next = n;
	    }
	    
	    public String toString()
	    {
	    	return "Node of " + element;
	    }

	    // Accessor methods
	    /**
	     * Returns the element stored at the node.
	     * @return the element stored at the node
	     */
	    public E getElement() { return element; }

	    /**
	     * Returns the node that follows this one (or null if no such node).
	     * @return the following node
	     */
	    public Node<E> getNext() { return next; }

	    // Modifier methods
	    /**
	     * Sets the node's next reference to point to Node n.
	     * @param n    the node that should follow this one
	     */
	    public void setNext(Node<E> n) { next = n; }
	  } //----------- end of nested Node class -----------
	
	 
	 // instance variables of the SinglyLinkedList
	  /** The head node of the list */
	  private Node<E> head = null;               // head node of the list (or null if empty)

	  /** The last node of the list */
	  private Node<E> tail = null;               // last node of the list (or null if empty)

	  /** Number of nodes in the list */
	  private int size = 0;                      // number of nodes in the list

	  /** Constructs an initially empty list. */
	  public SinglyLinkedList() { }              // constructs an initially empty list

	  // access methods
	  /**
	   * Returns the number of elements in the linked list.
	   * @return number of elements in the linked list
	   */
	  public int size() { return size; }

	  /**
	   * Tests whether the linked list is empty.
	   * @return true if the linked list is empty, false otherwise
	   */
	  public boolean isEmpty() { return size == 0; }

	  /**
	   * Returns (but does not remove) the first element of the list
	   * @return element at the front of the list (or null if empty)
	   */
	  public E first() {             // returns (but does not remove) the first element
	    if (isEmpty()) return null;
	    return head.getElement();
	  }

	  /**
	   * Returns (but does not remove) the last element of the list.
	   * @return element at the end of the list (or null if empty)
	   */
	  public E last() {              // returns (but does not remove) the last element
	    if (isEmpty()) return null;
	    return tail.getElement();
	  }

	  // update methods
	  /**
	   * Adds an element to the front of the list.
	   * @param e  the new element to add
	   */
	  public void addFirst(E e) {                // adds element e to the front of the list
	    head = new Node<>(e, head);              // create and link a new node
	    if (size == 0)
	      tail = head;                           // special case: new node becomes tail also
	    size++;
	  }

	  /**
	   * Adds an element to the end of the list.
	   * @param e  the new element to add
	   */
	  public void addLast(E e) {                 // adds element e to the end of the list
	    Node<E> newest = new Node<>(e, null);    // node will eventually be the tail
	    if (isEmpty())
	      head = newest;                         // special case: previously empty list
	    else
	      tail.setNext(newest);                  // new node after existing tail
	    tail = newest;                           // new node becomes the tail
	    size++;
	  }

	  /**
	   * Removes and returns the first element of the list.
	   * @return the removed element (or null if empty)
	   */
	  public E removeFirst() {                   // removes and returns the first element
	    if (isEmpty()) return null;              // nothing to remove
	    E answer = head.getElement();
	    head = head.getNext();                   // will become null if list had only one node
	    size--;
	    if (size == 0)
	      tail = null;                           // special case as list is now empty
	    return answer;
	  }

	  @SuppressWarnings({"unchecked"})
	  public boolean equals(Object o) {
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    SinglyLinkedList other = (SinglyLinkedList) o;   // use nonparameterized type
	    if (size != other.size) return false;
	    Node walkA = head;                               // traverse the primary list
	    Node walkB = other.head;                         // traverse the secondary list
	    while (walkA != null) {
	      if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
	      walkA = walkA.getNext();
	      walkB = walkB.getNext();
	    }
	    return true;   // if we reach this, everything matched successfully
	  }

	  @SuppressWarnings({"unchecked"})
	  public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
	    // always use inherited Object.clone() to create the initial copy
	    SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
	    if (size > 0) {                    // we need independent chain of nodes
	      other.head = new Node<>(head.getElement(), null);
	      Node<E> walk = head.getNext();      // walk through remainder of original list
	      Node<E> otherTail = other.head;     // remember most recently created node
	      while (walk != null) {              // make a new node storing same element
	        Node<E> newest = new Node<>(walk.getElement(), null);
	        otherTail.setNext(newest);     // link previous node to this one
	        otherTail = newest;
	        walk = walk.getNext();
	      }
	    }
	    return other;
	  }

	  public int hashCode() {
	    int h = 0;
	    for (Node walk=head; walk != null; walk = walk.getNext()) {
	      h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
	      h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
	    }
	    return h;
	  }

	  /**
	   * Produces a string representation of the contents of the list.
	   * This exists for debugging purposes only.
	   */
	  public String toString() {
	    StringBuilder sb = new StringBuilder("[");
	    Node<E> walk = head;
	    while (walk != null) {
	      sb.append(walk.getElement()); //+ "-" + walk);
	      if (walk != tail)
	        sb.append(", ");
	      walk = walk.getNext();
	    }
	    sb.append("]");
	    return sb.toString();
	  }
}

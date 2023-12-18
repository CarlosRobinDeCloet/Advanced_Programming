import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;

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
 * A basic doubly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class DoublyLinkedList<E>
{
	public static void main(String [] args)
	{
		DoublyLinkedList<String> list = new DoublyLinkedList<String>();
		for(int i = 0; i < 8; i++)
		{
			list.addLast("elem_" + i);
		}
		System.out.println(list);
		System.out.println();
		
		Iterator<String> iter = list.ordinaryIterator();
		//list.removeFirst(); // If this is done, an exception should be thrown for all but the snapshot iterator.
		printListAlongIterator(iter);
		
		iter = list.reverseIterator();
		//list.removeFirst(); // If this is done, an exception should be thrown for all but the snapshot iterator.
		printListAlongIterator(iter);
		
		iter = list.evenOddIterator();
		//list.removeFirst(); // If this is done, an exception should be thrown for all but the snapshot iterator.
		printListAlongIterator(iter);
		
		iter = list.snapshotIterator();
		list.removeFirst(); 
		list.removeLast();
		printListAlongIterator(iter);
		
		//This can be used once Iterable<E> naturalOrder() has been implemented
		System.out.println();
		for(String s : list.naturalOrder())
		{
			System.out.println(s);
		}
	}
	
	// Function to print the elements of the linked list in the order of the iterator
	private static void printListAlongIterator(Iterator<String> iter)
	{		
		String sep = "[[ ";
		while(iter.hasNext())
		{
			System.out.print(sep + iter.next());
			sep = ", ";
		}
		if(sep.equals(", ")) System.out.println(" ]]");
	}
	
	// This is added, to define the state of the linked list [TD]
	private long modCount = 0;
	
	public Iterator<E> ordinaryIterator() 
	{
		return new OrdinaryIterator();
	}
	public Iterator<E> reverseIterator() 
	{
		return new ReverseIterator();
	}
	public Iterator<E> evenOddIterator() 
	{
		return new EvenOddIterator();
	}
	public Iterator<E> snapshotIterator()
	{
		return new SnapshotIterator();
	}


	// Finish the implementation below
	private class OrdinaryIterator implements Iterator<E> 
	{
		// sets the linked list header as the current node. Finds the next node and returns its element until the node
		// preceding the tail of the linked list has been found.
		private Node currentNode = DoublyLinkedList.this.header;
		private Node lastNode = DoublyLinkedList.this.trailer.getPrev();
		long myModCount;
		OrdinaryIterator()
		{
			myModCount = DoublyLinkedList.this.modCount;
		}
		
	    public boolean hasNext() 
	    {
	    	if(DoublyLinkedList.this.modCount != myModCount)
	    	{
	    		throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
	    	}
	        return !(currentNode.equals(lastNode));
	    }

	    public E next() 
	    {    
	    	if(DoublyLinkedList.this.modCount != myModCount)
	    	{
	    		throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
	    	}
	    	if(!hasNext())
	    	{
	    		throw new NoSuchElementException("There is no next element!");
	    	}

	    	currentNode = currentNode.getNext();
	    	return (E) currentNode.getElement();
	    }
	}	
	
	// Finish the implementation below
	public class ReverseIterator implements Iterator<E> 
	{
		// Sets the tail of the linked list as the current node and then moves to the previous node until the node
		// subsequent to the header of the linked list is found.
		Node currentNode = DoublyLinkedList.this.trailer;
		Node lastNode = DoublyLinkedList.this.header.getNext();

		long myModCount;
		ReverseIterator() {
			myModCount = DoublyLinkedList.this.modCount;
		}

		public boolean hasNext()
		{
			if(DoublyLinkedList.this.modCount != myModCount ){
				throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
			}
	    	return !(currentNode.equals(lastNode));
	    }
	    public E next() 
	    {
			if(DoublyLinkedList.this.modCount != myModCount ){
				throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
			}
			if(!hasNext())
			{
				throw new NoSuchElementException("There is no next element!");
			}

			currentNode = currentNode.getPrev();
			return (E) currentNode.getElement();
	    }
	}	
	// Finish the implementation below
	private class EvenOddIterator implements Iterator<E> 
	{

		// Finds the first node, the final node and the first node with an uneven element.
		Node currentNode = DoublyLinkedList.this.header.getNext();
		Node finalNode = DoublyLinkedList.this.trailer.getPrev();
		Node firstOddNode = DoublyLinkedList.this.header.getNext().getNext();

		// Checks if an iteration is the first iteration.
		boolean firstIteration = true;
		long myModCount;

		EvenOddIterator(){myModCount = DoublyLinkedList.this.modCount;}

		public boolean hasNext() 
	    {
	    	if(DoublyLinkedList.this.modCount != myModCount){
				throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
			}
	    	return !(currentNode.equals(finalNode));
	    }

	    public E next() 
	    {    
	    	if(DoublyLinkedList.this.modCount != myModCount){
				throw new ConcurrentModificationException("The underlying linked list is structurally changed!");
			}

	    	if(!hasNext()){
				throw new NoSuchElementException("There is no next element!");
			}

	    	// Return the element of the first node in the linked list.
	    	if(firstIteration){
	    		firstIteration = false;
	    		return (E) currentNode.getElement();
			}

	    	// updates the node to the next even node. Sets the currentNode as the first uneven node if a null is found.
			currentNode = currentNode.getNext().getNext();
	    	if(currentNode.getElement() == null){
	    		currentNode = firstOddNode;
			}

		   	return (E) currentNode.getElement();
	    }
	}	

	public class SnapshotIterator implements Iterator<E> 
	{
		// makes a copy and iterates over the copy.
		DoublyLinkedList copy = DoublyLinkedList.this;
		Node currentNode = copy.header;
		Node finalNode = DoublyLinkedList.this.trailer;

		public boolean hasNext()
	    {
	        return !(currentNode.getNext().equals(finalNode));
	    }

	    public E next() 
	    {    
	    	if(!hasNext()){
	    		throw new NoSuchElementException("There is no next element!");
			}

	    	currentNode = currentNode.getNext();
	    	return (E) currentNode.getElement();
	    }
	}

	public class OrdinaryIterable implements Iterable<E>{

		@Override
		public Iterator<E> iterator() {
			return ordinaryIterator();
		}
	}

	public Iterable<E> naturalOrder(){
		return new OrdinaryIterable();
	}


	
  //---------------- nested Node class ----------------
  /**
   * Node of a doubly linked list, which stores a reference to its
   * element and to both the previous and next node in the list.
   */
  private static class Node<E> {

    /** The element stored at this node */
    private E element;               // reference to the element stored at this node

    /** A reference to the preceding node in the list */
    private Node<E> prev;            // reference to the previous node in the list

    /** A reference to the subsequent node in the list */
    private Node<E> next;            // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param p  reference to a node that should precede the new node
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }

    // public accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { return element; }

    /**
     * Returns the node that precedes this one (or null if no such node).
     * @return the preceding node
     */
    public Node<E> getPrev() { return prev; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Update methods
    /**
     * Sets the node's previous reference to point to Node n.
     * @param p    the node that should precede this one
     */
    public void setPrev(Node<E> p) { prev = p; }

    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }
  } //----------- end of nested Node class -----------
 
  // instance variables of the DoublyLinkedList
  /** Sentinel node at the beginning of the list */
  private Node<E> header;                    // header sentinel

  /** Sentinel node at the end of the list */
  private Node<E> trailer;                   // trailer sentinel

  /** Number of elements in the list (not including sentinels) */
  private int size = 0;                      // number of elements in the list

  /** Constructs a new empty list. */
  public DoublyLinkedList() {
    header = new Node<>(null, null, null);      // create header
    trailer = new Node<>(null, header, null);   // trailer is preceded by header
    header.setNext(trailer);                    // header is followed by trailer
  }

  // public accessor methods
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
   * Returns (but does not remove) the first element of the list.
   * @return element at the front of the list (or null if empty)
   */
  public E first() {
    if (isEmpty()) return null;
    return header.getNext().getElement();   // first element is beyond header
  }

  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public E last() {
    if (isEmpty()) return null;
    return trailer.getPrev().getElement();    // last element is before trailer
  }

  // public update methods
  /**
   * Adds an element to the front of the list.
   * @param e   the new element to add
   */
  public void addFirst(E e) {
	  modCount++;
    addBetween(e, header, header.getNext());    // place just after the header
  }

  /**
   * Adds an element to the end of the list.
   * @param e   the new element to add
   */
  public void addLast(E e) {
	  modCount++;
    addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {
    if (isEmpty()) return null;                  // nothing to remove
	  modCount++;
    return remove(header.getNext());             // first element is beyond header
  }

  /**
   * Removes and returns the last element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeLast() {
    if (isEmpty()) return null;                  // nothing to remove
	  modCount++;
    return remove(trailer.getPrev());            // last element is before trailer
  }

  // private update methods
  /**
   * Adds an element to the linked list in between the given nodes.
   * The given predecessor and successor should be neighboring each
   * other prior to the call.
   *
   * @param predecessor   node just before the location where the new element is inserted
   * @param successor     node just after the location where the new element is inserted
   */
  private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
    // create and link a new node
    Node<E> newest = new Node<>(e, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
  }

  /**
   * Removes the given node from the list and returns its element.
   * @param node    the node to be removed (must not be a sentinel)
   */
  private E remove(Node<E> node) {
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    return node.getElement();
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = header.getNext();
    while (walk != trailer) {
      sb.append(walk.getElement());
      walk = walk.getNext();
      if (walk != trailer)
        sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }
} //----------- end of DoublyLinkedList class -----------

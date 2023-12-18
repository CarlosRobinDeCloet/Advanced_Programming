import java.util.Stack;

/**
 * This class gives an implementation of a queue.
 *
 * @author Carlos de Cloet
 *
 * @param <E> Object stored in Queue
 */

public class MyQueue<E> {

    private int size;
    private Stack<E> stack1;
    private Stack<E> stack2;

    /**
     * Constructor for the queue. Sets the size of the MyQueue as 0 and creates a new stack to store elements in.
     */
    public MyQueue(){
        this.size = 0;
        this.stack1 = new Stack<>();
    }

    /**
     * @return the size of the queue.
     */
    public int size(){
        return this.size;
    }

    /**
     * @return whether the queue is empty.
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * Puts a new object in the back of the queue by pushing all objects of stack 1 onto stack 2. Next the provided object
     * is added to stack 2. Then all objects in stack 2 are popped onto stack 1.
     */
    public void enqueue(E e){

        this.size++;
        this.stack2 = new Stack<>();
        while(!stack1.empty()){
            stack2.push(stack1.pop());
        }
        stack2.push(e);
        while(!stack2.empty()){
            stack1.push(stack2.pop());
        }
    }

    /**
     * Gets the object in the front of the queue and removes it from the queue by popping an element from stack 1.
     * @return the object in front of the queue.
     */
    public E dequeue(){

        if(!stack1.isEmpty()){
            E e = this.stack1.pop();
            this.size--;
            return e;
        }
        return null;
    }

    /**
     * Looks at the first object in the queue without removing it.
     * @return the object in front of the queue.
     */
    public E first(){
        return this.stack1.peek();
    }
}

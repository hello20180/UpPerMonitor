package com.hnzy.per.socket.util;

import java.util.Arrays;

public class Queue
{
	 // Define capacity constant: CAPACITY
    private static final int CAPACITY = 1024;
    // Define capacity of queue
    private static int capacity;
    // Front of queue
    private static int front;
    // Tail of queue
    private static int tail;
    // Array for queue
    private static Object[] array;
 
    // Constructor of Queue class
    public Queue() {
        this.capacity = CAPACITY;
        array = new Object[capacity];
        front = tail = 0;
    }
 
    // Get size of queue
    public static int getSize() {
        if (isEmpty())
            return 0;
        else
            return (capacity + tail - front) % capacity;
    }
 
    // Whether is empty
    public static boolean isEmpty() {
        return (front == tail);
    }
 
    // put element into the end of queue
    public static void enqueue(Object element) throws ExceptionQueueFull {
        if (getSize() == capacity - 1)
            throw new ExceptionQueueFull("Queue is full");
        array[tail] = element;
        tail = (tail + 1) % capacity;
    }
 
    // get element from queue
    public static Object dequeue() throws ExceptionQueueEmpty {
        Object element;
        if (isEmpty())
            throw new ExceptionQueueEmpty("Queue is empty");
        element = array[front];
        front = (front + 1) % capacity;
        return element;
    }
 
    // Get the first element for queue
    public static Object frontElement() throws ExceptionQueueEmpty {
        if (isEmpty())
            throw new ExceptionQueueEmpty("Queue is empty");
        return array[front];
    }
 
    // Travel all elements of queue
    public static void getAllElements() {
        Object[] arrayList = new Object[getSize()];
 
        for (int i = front,j = 0; j < getSize(); i ++,j ++) {
                arrayList[j] = array[i];
        }
    }
}


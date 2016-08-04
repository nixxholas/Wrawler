/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

/**
 *
 * @author Nixholas
 */
public class ConcurrentInt {
    private static int Count = 0;
    
    public synchronized void incrementCount() {
        Count++;
    }

    public static int getCount() {
        return Count;
    }

    public static void setCount(int Count) {
        ConcurrentInt.Count = Count;
    }
}

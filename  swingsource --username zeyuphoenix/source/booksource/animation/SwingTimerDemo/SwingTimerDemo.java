package animation.SwingTimerDemo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
/*
 * SwingTimerDemo.java
 *
 * Created on May 2, 2007, 3:25 PM
 *
 * Copyright (c) 2007, Sun Microsystems, Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 * @author Chet
 */
public class SwingTimerDemo implements ActionListener {
    
    private static long prevTime = 0;
    private static long startTime = 0;
    private static final int DELAY = 100;
    private static final int DURATION = 5 * DELAY;
    private static final int PROCESSING_TIME = 30;
    private static final long INITIAL_PROCESSING_TIME = 2 * DELAY;
    private static Timer timer = null;
    private boolean firstTime = true;
    
    /** 
     * This method will be called during every tick of the Timers.
     * We insert an artificial delay each time, to simulate some processing.
     * The first time through, this delay is greater than the delay between
     * timing events, so that we can see how this hiccup is handled by
     * fixed-rate and fixed-delay timers.
     */
    public void actionPerformed(ActionEvent ae) {
        long nowTime = System.currentTimeMillis();
        long elapsedTime = nowTime - prevTime;
        long totalTime = nowTime - startTime;
        System.out.println("Elapsed time = " + elapsedTime);
        if (totalTime > DURATION) {
            timer.stop();
        }
        prevTime = nowTime;
        try {
            if (firstTime) {
                Thread.sleep(INITIAL_PROCESSING_TIME);
                firstTime = false;
            } else {
                Thread.sleep(PROCESSING_TIME);
            }
        } catch (Exception e) {}
    }

    public SwingTimerDemo() {
        firstTime = true;
    }
    
    public static void main(String[] args) {        
        // Run a default fixed-delay timer
        timer = new Timer(DELAY, new SwingTimerDemo());
        startTime = prevTime = System.currentTimeMillis();
        System.out.println("Fixed Delay Times");
        timer.start();

        // Sleep for long enough that the first timer ends
        try {
            Thread.sleep(DURATION*2);
        } catch (Exception e) {}
        
        // Run a timer with no coalescing to get fixed-rate behavior
        timer = new Timer(DELAY, new SwingTimerDemo());
        startTime = prevTime = System.currentTimeMillis();
        timer.setCoalesce(false);
        System.out.println("\nFixed Rate Times");
        timer.start();

    }
    
}

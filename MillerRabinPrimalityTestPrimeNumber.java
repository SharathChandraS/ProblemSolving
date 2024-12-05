package primenumber;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinPrimalityTestPrimeNumber {

	public static void main(String[] args) {
		
		runMillerRabinPrimilatyTest(9124);
//		runMillerRabinPrimilatyTest(1427);
//		runMillerRabinPrimilatyTest(3);
	}
	
	public static void runMillerRabinPrimilatyTest(long n) {
		
		/*
		 * Step 1: find 'd' n-1 = (2^s)*d d = (n-1)/(2^s) where s=0 to n divide n-1 by
		 * 2^s untill the n-1 is divided completely once when there is a decimal then
		 * choose 2^(s-1) and get 'd'
		 */
		long d = caculateD(n);
		
//		Step 2: select random numberd 'a' 1 < a < n-1
		Random random = new Random();
	
        long a = 0;
        if(n-2 < 2) {
        	a=1;
        } else {
        	 a = random.nextLong(2, n-2);
        }
//        System.out.println(d+" " +a);
		/*
		 * Step 3: 
		 * 	step 3.1 : x = (a^d) * mod n-1 check if we get '1' then it is
		 * composite, '-1' then it is probabily prime numberd. If we didn't got both
		 * then continue to step 3.2. 
		 * 	step 3.2 : x = (x^2) * mod n-1 check if we get '1'
		 * then it is composite, '-1' then it is probabily prime numberd. If we didn't
		 * got both then continue to step 3.2.
		 */	
//       System.out.println(checkPrimality(n, d, a)); ;
        checkPrimality(n, d, a);
	}
	
	public static long caculateD(long n) {
		long d = 0;
		long s =0;
		double m = 1;
		do {
			s++;
			m = Math.pow(2, s);
			d = (long) ((n-1) / m);
			if(d%2 != 0) {
				break;
			}
		} while((n-1) == (d*m));
		
		return d;
	}
	
//	public static void checkPrimality(long n, long d, long a) {
//		BigInteger base = BigInteger.valueOf(a);
//        BigInteger exponent = BigInteger.valueOf(d);
//        BigInteger modulus = BigInteger.valueOf(n);
//        
//        BigInteger result = base.modPow(exponent, modulus);
//        System.out.println("n: "+n+" ,d: "+d+" ,a: "+ a+ " ,result: "+result.floatValue());
//        
//        if(result.equals(BigInteger.valueOf(-1))) {
//        	System.out.println("Prime Number");
//        } else if (result.equals(BigInteger.ONE)) {
//        	System.out.println("Composite number");
//        } else if(result.intValue() > 1) {
//        	checkPrimality(n, 2, result.longValue());
//        }
//        
//	}
//	
//	for above checkPrimality function we will get stack overflow weather we will return or we will not return the value below is the complete explanation and solution
	
//	The **recursive method affects the stack** by continuously pushing new stack frames onto it with every recursive call. Here's a detailed explanation of how this works in your code and why it leads to a stack overflow:
//
//	---
//
//	### How the Stack Works in Recursion
//
//	1. **Stack Frame Creation**: 
//	   - Each time a method is called (including recursive calls), a **stack frame** is pushed onto the **call stack**.
//	   - A stack frame contains:
//	     - The method's local variables (e.g., `n`, `d`, `a`, `result`).
//	     - Return address (to continue execution after the method call finishes).
//	     - Other bookkeeping data.
//
//	2. **Recursive Call in Your Code**:
//	   - In your method, every time this condition is true:
//	     ```java
//	     if (result.intValue() > 1) {
//	         checkPrimality(n, 2, result.longValue());
//	     }
//	     ```
//	     - A new stack frame is created for the `checkPrimality` method.
//	     - The current frame is paused until the recursive call completes.
//
//	3. **Deep Recursion**:
//	   - If the condition keeps evaluating as `true`, the method keeps calling itself without returning.
//	   - Each recursive call adds another frame to the stack.
//
//	4. **Exceeding Stack Limit**:
//	   - The call stack has a limited size (determined by the JVM and the operating system).
//	   - If the recursion depth exceeds this limit, the program cannot allocate space for new stack frames.
//	   - At this point, a `StackOverflowError` is thrown.
//
//	---
//
//	### How Your Method Causes Stack Overflow
//
//	1. **Infinite Recursion**:
//	   - The recursive condition:
//	     ```java
//	     if (result.intValue() > 1) {
//	         checkPrimality(n, 2, result.longValue());
//	     }
//	     ```
//	     - This condition is problematic because it does not reduce the problem size effectively.
//	     - The new `result` value could lead to the same or similar values in subsequent calls, causing an infinite loop.
//
//	2. **Unbounded Calls**:
//	   - Without a proper termination condition, the recursion continues indefinitely for some values of `n` (e.g., `9124`).
//
//	3. **Stack Growth**:
//	   - Each recursive call consumes stack space.
//	   - The stack grows until the JVM stack limit is reached.
//
//	4. **Example Trace for `n = 9124`**:
//	   - First Call: `checkPrimality(9124, d, a)` → Calculates `result` → Calls `checkPrimality(9124, 2, result.longValue())`.
//	   - Second Call: Same logic repeats with new `result` → Calls `checkPrimality(9124, 2, result.longValue())`.
//	   - This process continues until the stack limit is reached, and a `StackOverflowError` is thrown.
//
//	---
//
//	### Visualizing Stack Frames
//
//	For the input `n = 9124`, assume the recursion does not terminate:
//
//	```
//	Main Thread: calls runMillerRabinPrimilatyTest(9124)
//	  → Frame 1: runMillerRabinPrimilatyTest(9124)
//	    → Frame 2: checkPrimality(9124, d, a)
//	      → Frame 3: checkPrimality(9124, 2, result.longValue())
//	        → Frame 4: checkPrimality(9124, 2, result.longValue())
//	          → Frame 5: checkPrimality(9124, 2, result.longValue())
//	          ...
//	          → Frame N: (Stack limit reached, StackOverflowError)
//	```
//
//	---
//
//	### Why Stack Space is Limited
//
//	1. **Performance**:
//	   - The stack is limited to avoid using excessive memory for potentially runaway recursive calls.
//	   - JVM uses a fixed stack size (default is often 1MB per thread, but this varies).
//
//	2. **Practical Constraints**:
//	   - Each recursive call consumes memory.
//	   - Allowing unlimited stack growth could lead to memory exhaustion.
//
//	---
//
//	### How to Fix the Stack Issue
//
//	1. **Avoid Infinite Recursion**:
//	   - Ensure the recursive call always reduces the problem size and eventually terminates.
//	   - Example: Replace recursion with iteration.
//
//	2. **Set Stack Size (If Needed)**:
//	   - You can increase the JVM stack size using the `-Xss` flag, but this is not a real solution as the recursion is fundamentally flawed.
//
//	3. **Terminate Properly**:
//	   - Add a condition to prevent recursion if `result` cycles or grows unbounded:
//	     ```java
//	     if (/* some condition to ensure termination */) {
//	         return;
//	     }
//	     ```
//
//	---
//
//	### Conclusion
//
//	Recursive calls push stack frames onto the call stack. Without proper termination, your method makes unbounded recursive calls, leading to the stack overflowing its limit. This is why you encounter the `StackOverflowError`.
	
// Solution
	public static void checkPrimality(long n, long d, long a) {
	    BigInteger base = BigInteger.valueOf(a);
	    BigInteger modulus = BigInteger.valueOf(n);

	    // Step 3.1: Compute a^d % n
	    BigInteger result = base.modPow(BigInteger.valueOf(d), modulus);

	    if (result.equals(BigInteger.ONE) || result.equals(modulus.subtract(BigInteger.ONE))) {
	        System.out.println(n+ " is Probably Prime Number");
	        return;
	    }

	    // Step 3.2: Square x repeatedly
	    while (d < n - 1) {
	        result = result.modPow(BigInteger.TWO, modulus);
	        d *= 2;

	        if (result.equals(modulus.subtract(BigInteger.ONE))) {
	            System.out.println(n+ " is Probably Prime Number");
	            return;
	        }

	        if (result.equals(BigInteger.ONE)) {
	            System.out.println(n+ " is Composite Number");
	            return;
	        }
	    }

	    System.out.println(n+ " is Composite Number");
	}

}

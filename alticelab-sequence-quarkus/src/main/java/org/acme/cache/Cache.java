package org.acme.cache;

/**
 *
 * @author Kildar
 */
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.math.BigInteger;

/**
 * POGO to serve as our cache, using a LinkedHash with the key being N and value being a BigInteger array of size 4
 */
public class Cache {

	private LinkedHashMap<Integer, BigInteger[]> cache;
	
	// Auto removes oldest entry when trying to add one past it's limit
	public Cache(int cacheSize) {
		this.cache = new LinkedHashMap<Integer, BigInteger[]>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, BigInteger[]> eldest) {
                return size() > cacheSize;
            }
        };

	}

	//We add the solution to what was asked and the last 3 values so we have everything we need to start calculating from there
	//Example if we lookup L(20) -> We store L(20), L(19), L(18), L(17)
	public void addToCache(int seqNumber, BigInteger solutions[]) {
		this.cache.put(seqNumber, solutions);
		
	}

	public BigInteger[] checkCache(int n) {
		//printCache();
		return this.cache.get(n);
	}
	
	public Set<Integer> getKeys()
	{
		return cache.keySet();
	}

	private void printCache(){
		//System.out.println("CURRENT CACHE");
		for (Integer key : cache.keySet()) {
			//System.out.println("N = " + key);
            for(int i = 0; i < cache.get(key).length; i++)
			 {
				System.out.println("\t" + (key + i - 3) + " = " + cache.get(key)[i]);
			 }
        }
	}
}


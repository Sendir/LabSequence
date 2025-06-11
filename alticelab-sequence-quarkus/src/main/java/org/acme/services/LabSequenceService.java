package org.acme.services;

import java.math.BigInteger;
import java.util.Set;

import org.acme.cache.Cache;
import org.acme.dto.ResponseDTO;
import org.acme.exceptions.NegativeNumberException;

import jakarta.enterprise.context.ApplicationScoped;

/**
 *
 * @author Kildar
 */
@ApplicationScoped
public class LabSequenceService {

    private final int cacheSize = 5;
    private Cache cache;

    public LabSequenceService() {
        cache = new Cache(cacheSize);
    }

    /**
     *
     * @param n index of the sequence's value to return
     * @return calculated value of sequence on index n
     */
    public ResponseDTO calculateLabSeq(int n) {
        //System.out.println("#####################################\n\n\n");
        long startTime = System.nanoTime();

        if (n < 0) {
            throw new NegativeNumberException("n must be a non-negative");
        }

        BigInteger[] localCachedResult = cache.checkCache(n);

        if (localCachedResult != null) {
            //System.out.println("Found directly on cache");
            long endTime = System.nanoTime();
            return new ResponseDTO(endTime - startTime, localCachedResult[3]);
        }

        int closestStart = checkClosestMatch(n);
        BigInteger result;

        if (closestStart != 0) {
            //System.out.println("Starting from: " + closestStart);
            result = calculateFromCache(n, closestStart);
            long endTime = System.nanoTime();

            return new ResponseDTO(endTime - startTime, result);
        } else {
            //System.out.println("Calculating from scratch");
            result = calculateFromScratch(n);
            long endTime = System.nanoTime();

            return new ResponseDTO(endTime - startTime, result);
        }
    }

    /**
     *
     * @param n n index of the sequence's value to return
     * @return closest matched value in {@link Cache}, 0 if none
     */
    private int checkClosestMatch(int n) {

        int closestMatch = n;
        int currentBestKey = 0;

        // Get the set of keys
        Set<Integer> keys = cache.getKeys();

        int comparedResult = 0;
        // Iterate through the keys and compare them to the desired number
        for (Integer key : keys) {
            // Minus 3 since we also have the previous 3 values of this current key
            comparedResult = n - (key - 3);

            //We can't do the reverse calculations so our result MUST be above 0 for it to be possible to start from there
            if (comparedResult >= 0 && closestMatch > comparedResult) {
                closestMatch = comparedResult;
                currentBestKey = key;
            }
        }

        return currentBestKey;
    }

    /**
     *
     * @param n n index of the sequence's value to return
     * @return calculates the sequence from the start
     */
    private BigInteger calculateFromScratch(int n) {
        BigInteger[] labSeq = new BigInteger[4];

        labSeq[0] = BigInteger.ZERO;
        labSeq[1] = BigInteger.ONE;
        labSeq[2] = BigInteger.ZERO;
        labSeq[3] = BigInteger.ONE;

        if (n < 4) {
            return labSeq[n];
        }

        BigInteger nextValue;

        for (int i = 4; i <= n; i++) {

            nextValue = labSeq[0].add(labSeq[1]);

            labSeq[0] = labSeq[1];
            labSeq[1] = labSeq[2];
            labSeq[2] = labSeq[3];
            labSeq[3] = nextValue;
        }

        cache.addToCache(n, labSeq);

        return labSeq[3];
    }

    /**
     *
     * @param n n index of the sequence's value to return
     * @return calculates the sequence starting from a value found in
     * {@link Cache}
     */
    private BigInteger calculateFromCache(int n, int key) {
        BigInteger[] labSeq = cache.checkCache(key).clone();

        // Checking to see if the result is already stored in the solutions attached to this key
        int checkSavedSolution = key - n;

        if (checkSavedSolution >= 0 && checkSavedSolution <= 3) {
            //System.out.println("Key: " + key + " - " + n + " = " + checkSavedSolution);
            //System.out.println(Arrays.toString(labSeq));
            return labSeq[3 - checkSavedSolution];
        }

        BigInteger nextValue;

        for (int i = key + 1; i <= n; i++) {

            nextValue = labSeq[0].add(labSeq[1]);

            labSeq[0] = labSeq[1];
            labSeq[1] = labSeq[2];
            labSeq[2] = labSeq[3];
            labSeq[3] = nextValue;
        }

        cache.addToCache(n, labSeq);

        return labSeq[3];
    }

    public void setCache(Cache mockCache) {
        this.cache = cache;
    }
}

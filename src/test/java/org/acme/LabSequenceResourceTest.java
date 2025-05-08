package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Set;

import org.acme.cache.Cache;
import org.acme.dto.ResponseDTO;
import org.acme.services.LabSequenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class LabSequenceResourceTest {

    @Inject
    LabSequenceService labSequenceService;

    @Test
    void testCorrectSequenceValueWithoutCache() {
        // Prepare
        int n = 19;

        // Run
        ResponseDTO response = labSequenceService.calculateLabSeq(n);

        // Assert
        assertEquals(BigInteger.valueOf(17), response.getResult());
    }

    @Test
    void testCorrectSequenceValueWithCache() {
        // Prepare
        int n = 1000000;

        // Run
        ResponseDTO firstResponse = labSequenceService.calculateLabSeq(n);

        ResponseDTO secondResponse = labSequenceService.calculateLabSeq(n);

        // Assert
        assertEquals(firstResponse.getResult(), secondResponse.getResult());
        assertTrue(secondResponse.getTime() < firstResponse.getTime(),
        "Expected second call to be faster due to cache: " + secondResponse.getTime() + " < " + firstResponse.getTime());
    }
}

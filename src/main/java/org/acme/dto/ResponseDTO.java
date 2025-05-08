package org.acme.dto;

/**
 *
 * @author Kildar
 */
import java.math.BigInteger;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Response DTO of LabSequence
 */
public class ResponseDTO {

    @Schema(
            description = "Time taken to compute the LabSequence value, in nanoseconds"
    )
    private long time;
    @Schema(
            description = "Result of the LabSequence"
    )
    private BigInteger result;

    public ResponseDTO() {
    }

    public ResponseDTO(long time, BigInteger result) {
        this.time = time;
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public BigInteger getResult() {
        return result;
    }

    public void setResult(BigInteger result) {
        this.result = result;
    }
}

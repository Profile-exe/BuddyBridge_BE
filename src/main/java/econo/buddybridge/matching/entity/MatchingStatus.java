package econo.buddybridge.matching.entity;

import lombok.Getter;


@Getter
public enum MatchingStatus {
    DONE("DONE"),
    FAILED("FAILED"),
    PENDING("PENDING");

    private final String matchingStatus;

    MatchingStatus(String matchingStatus) {
        this.matchingStatus = matchingStatus;
    }
}

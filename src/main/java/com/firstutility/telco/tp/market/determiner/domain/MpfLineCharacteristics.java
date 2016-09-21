package com.firstutility.telco.tp.market.determiner.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class MpfLineCharacteristics {

    private final String estimatedMinimumDownloadSpeed;
    private final String estimatedMaximumDownloadSpeed;

    public MpfLineCharacteristics(String estimatedMinimumDownloadSpeed, String estimatedMaximumDownloadSpeed) {
        this.estimatedMinimumDownloadSpeed = estimatedMinimumDownloadSpeed;
        this.estimatedMaximumDownloadSpeed = estimatedMaximumDownloadSpeed;
    }

    public String getEstimatedMinimumDownloadSpeed() {
        return estimatedMinimumDownloadSpeed;
    }

    public String getEstimatedMaximumDownloadSpeed() {
        return estimatedMaximumDownloadSpeed;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}

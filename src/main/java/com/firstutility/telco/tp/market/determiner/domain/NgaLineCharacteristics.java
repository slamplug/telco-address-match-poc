package com.firstutility.telco.tp.market.determiner.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class NgaLineCharacteristics {

    private final String estimatedMinimumDownloadSpeed;
    private final String estimatedMaximumDownloadSpeed;
    private final String estimatedMinimumUploadSpeed;
    private final String estimatedMaximumUploadSpeed;

    public NgaLineCharacteristics(final String estimatedMinimumDownloadSpeed, final String estimatedMaximumDownloadSpeed,
            final String estimatedMinimumUploadSpeed, final String estimatedMaximumUploadSpeed) {
        this.estimatedMinimumDownloadSpeed = estimatedMinimumDownloadSpeed;
        this.estimatedMaximumDownloadSpeed = estimatedMaximumDownloadSpeed;
        this.estimatedMinimumUploadSpeed = estimatedMinimumUploadSpeed;
        this.estimatedMaximumUploadSpeed = estimatedMaximumUploadSpeed;
    }

    public String getEstimatedMinimumDownloadSpeed() {
        return estimatedMinimumDownloadSpeed;
    }

    public String getEstimatedMaximumDownloadSpeed() {
        return estimatedMaximumDownloadSpeed;
    }

    public String getEstimatedMinimumUploadSpeed() {
        return estimatedMinimumUploadSpeed;
    }

    public String getEstimatedMaximumUploadSpeed() {
        return estimatedMaximumUploadSpeed;
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

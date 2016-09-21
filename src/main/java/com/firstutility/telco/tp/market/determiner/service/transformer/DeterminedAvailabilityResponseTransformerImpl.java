package com.firstutility.telco.tp.market.determiner.service.transformer;

import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.MpfLineCharacteristics;
import com.firstutility.telco.tp.market.determiner.domain.NgaLineCharacteristics;
import org.springframework.stereotype.Component;

@Component
public class DeterminedAvailabilityResponseTransformerImpl implements DeterminedAvailabilityResponseTransformer {

    private static final String COMMA = ",";

    @Override
    public String transform(final DeterminedAvailabilityResponseMsg determinedAvailabilityResponseMsg) {
        final StringBuilder stringBuilder = new StringBuilder();

        final MpfLineCharacteristics mpfLineCharacteristics = determinedAvailabilityResponseMsg.getMpfLineCharacteristics();

        final NgaLineCharacteristics ngaLineCharacteristics = determinedAvailabilityResponseMsg.getNgaLineCharacteristics();

        stringBuilder.append(determinedAvailabilityResponseMsg.getCustomerNumber()).append(COMMA)
                .append(nullToBlank(determinedAvailabilityResponseMsg.getAlk())).append(COMMA)
                .append(nullToBlank(determinedAvailabilityResponseMsg.getQualifier())).append(COMMA)
                .append(nullToBlank(determinedAvailabilityResponseMsg.getCurrentProvider())).append(COMMA)
                .append(isNull(mpfLineCharacteristics) ? "" : nullToBlank(mpfLineCharacteristics.getEstimatedMinimumDownloadSpeed())).append(COMMA)
                .append(isNull(mpfLineCharacteristics) ? "" : nullToBlank(mpfLineCharacteristics.getEstimatedMinimumDownloadSpeed())).append(COMMA)
                .append(isNull(ngaLineCharacteristics) ? "" : nullToBlank(ngaLineCharacteristics.getEstimatedMinimumDownloadSpeed())).append(COMMA)
                .append(isNull(ngaLineCharacteristics) ? "" : nullToBlank(ngaLineCharacteristics.getEstimatedMinimumDownloadSpeed())).append(COMMA)
                .append(isNull(ngaLineCharacteristics) ? "" : nullToBlank(ngaLineCharacteristics.getEstimatedMinimumUploadSpeed())).append(COMMA)
                .append(isNull(ngaLineCharacteristics) ? "" : nullToBlank(ngaLineCharacteristics.getEstimatedMaximumUploadSpeed())).append(COMMA)
                .append(booleanToYesNo(determinedAvailabilityResponseMsg.isTtCustomerOnFibre())).append(COMMA)
                .append(booleanToYesNo(determinedAvailabilityResponseMsg.isTtCustomerMigratedToBT()));

        return stringBuilder.toString();
    }

    private String nullToBlank(final String in) {
        return (in != null) ? in : "";
    }

    private boolean isNull(final Object in) {
        return (in == null);
    }

    private String booleanToYesNo(final boolean in) {
        return in ? "Yes" : "No";
    }
}

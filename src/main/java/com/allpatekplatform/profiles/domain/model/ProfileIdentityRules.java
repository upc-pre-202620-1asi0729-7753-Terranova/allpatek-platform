package com.allpatekplatform.profiles.domain.model;

import java.util.Optional;
import java.util.regex.Pattern;

public final class ProfileIdentityRules {

    private static final Pattern PERSON_NAME = Pattern.compile(
            "^\\p{L}+(?:[ '\\-]\\p{L}+)*$",
            Pattern.UNICODE_CHARACTER_CLASS);
    private static final Pattern PHONE = Pattern.compile("^\\d{9}$");
    private static final Pattern DNI = Pattern.compile("^\\d{8}$");
    private static final Pattern RUC = Pattern.compile("^\\d{11}$");

    private ProfileIdentityRules() {
    }

    public static Optional<String> issueFor(
            String fullName,
            String phone,
            String document,
            String address,
            String company,
            String ruc,
            String activity
    ) {
        if (blank(fullName) || !PERSON_NAME.matcher(fullName.trim()).matches()) {
            return Optional.of("fullName");
        }
        if (blank(phone) || !PHONE.matcher(phone.trim()).matches()) {
            return Optional.of("phone");
        }
        if (blank(document) || !DNI.matcher(document.trim()).matches()) {
            return Optional.of("document");
        }
        if (blank(address)) {
            return Optional.of("address");
        }
        boolean commercial = !blank(company) || !blank(ruc) || !blank(activity);
        if (!commercial) {
            return Optional.empty();
        }
        if (blank(company)) {
            return Optional.of("company");
        }
        if (blank(ruc) || !RUC.matcher(ruc.trim()).matches()) {
            return Optional.of("ruc");
        }
        if (blank(activity) || !PERSON_NAME.matcher(activity.trim()).matches()) {
            return Optional.of("activity");
        }
        return Optional.empty();
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}

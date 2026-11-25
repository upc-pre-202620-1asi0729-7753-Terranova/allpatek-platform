package com.allpatekplatform.profiles.domain.model.queries;

import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}

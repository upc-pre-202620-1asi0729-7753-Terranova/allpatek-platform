package com.allpatekplatform.profiles.application.commandservices;

import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.commands.CreateProfileCommand;
import com.allpatekplatform.profiles.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface ProfileCommandService {

    Result<Profile, ApplicationError> handle(CreateProfileCommand command);

    Result<Profile, ApplicationError> handle(UpdateProfileCommand command);
}

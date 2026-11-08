package com.allpatekplatform.shared.application.commandservices;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface ProfileCommandService {

    Result<Profile, ApplicationError> handle(UpdateProfileCommand command);
}

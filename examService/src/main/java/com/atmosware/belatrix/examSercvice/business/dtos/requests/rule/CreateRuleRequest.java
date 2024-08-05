package com.atmosware.belatrix.examSercvice.business.dtos.requests.rule;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRuleRequest(
        @Size(min = 5,max =150 )
        @NotNull
        String description
){
}

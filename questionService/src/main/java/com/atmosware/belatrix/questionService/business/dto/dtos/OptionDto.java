package com.atmosware.belatrix.questionService.business.dto.dtos;

public record OptionDto(
        Long id,
        String text,
        boolean correct
) {
}

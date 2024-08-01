package com.atmosware.belatrix.questionService.business.constants;

public class Messages {
    public static class OptionMessages{
        public static final String AT_LEAST_ONE_OPTION_HAS_TO_BE_TRUE="atLeastOneOptionHasToBeTrue";
        public static final String OPTIONS_AND_REQUEST_SIZE_SHOULD_MATCH="optionAndRequestSizeMatch";
        public static final String QUESTION_CAN_NOT_OBTAIN_OPTIONS_MORE_THAN_FIVE="questionCanNotObtainOptionsMoreThanFive";
        public static final String QUESTION_CAN_NOT_OBTAIN_OPTIONS_LESS_THAN_TWO="questionCanNotObtainOptionsLessThanTwo";
        public static final String OPTION_NOT_FOUND="optionNotFound";
        public static final String OPTION_AND_QUESTION_SHOULD_BELONG_TO_EACH_OTHER="optionAndQuestionShouldBelongToEachOther";
        public static final String ONLY_RIGHT_OPTION_CAN_NOT_BE_DELETED="onlyRightOptionCanNotBeDeleted";
    }
    public static class QuestionMessages{
        public static final String QUESTION_SHOULD_BE_EXISTS="questionShouldBeExists";
        public static final String QUESTION_SHOULD_BE_BELONGED_TO_ORGANIZATION="questionShouldBeBelongedToOrganization";
        public static final String QUESTION_IS_NOT_UPDATABLE="questionIsNotUpdatable";
    }
}

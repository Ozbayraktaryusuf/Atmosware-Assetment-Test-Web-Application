package com.atmosware.belatrix.examSercvice.business.constants;

public class Messages {
    public static class TestMessages {
        public static final String TEST_NOT_FOUND = "testNotFound";
        public static final String TEST_SHOULD_BE_BELONGED_TO_ORGANIZATION = "testShouldBeBelongedToOrganization";
        public static final String TEST_SHOULD_NOT_BE_STARTED="testShouldNotBeStarted";
        public static final String TEST_SHOULD_BE_STARTED_BUT_NOT_ENDED="testShouldBeStartedButNotEnded";
        public static final String TEST_MUST_NOT_OBTAIN_ANOTHER_ORGANIZATIONS_QUESTION="testMustNotObtainAnotherOrganizationsQuestion";
    }

    public static class TestQuestionMessages {
        public static final String TEST_QUESTION_NOT_FOUND = "testQuestionNotFound";
        public static final String TEST_QUESTION_CAN_NOT_BE_DUPLICATED = "questionCanNotBeDuplicated";
        public static final String TEST_CAN_NOT_OBTAIN_QUESTION_LESS_THAN_ONE="testCanNotObtainLessThanOne";

    }
    public static class RuleMessages{
        public static final String RULE_NOT_FOUND = "ruleNotFound";
    }
    public static class TestRuleMessages{
        public static final String TEST_RULE_NOT_FOUND = "testRuleNotFound";
        public static final String TEST_RULE_ALREADY_EXISTS = "testRuleAlreadyExists";
    }
}

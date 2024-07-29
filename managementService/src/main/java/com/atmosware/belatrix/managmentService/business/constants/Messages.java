package com.atmosware.belatrix.managmentService.business.constants;

public class Messages {
    public static class AuthenticationMessages {
        public static final String INVALID_USER_OR_PASSWORD = "Invalid username or password.";
        public static final String USER_SHOULD_HAVE_AN_ORGANIZATION="userShouldHaveAnOrganization";
    }

    public static class OrganizationMessages{
        public static final String ORGANIZATION_NAME_CAN_NOT_BE_DUPLICATED="organizationNameCanNotBeDuplicated";
        public static final String ORGANIZATION_SHOULD_BE_EXISTS="organizationNotFound";
    }

    public static class RoleMessages{
        public static final String ROLE_CAN_NOT_BE_DUPLICATED="roleNameCanNotBeDuplicated";
    }

    public static class UserMessages{
        public static final String USER_CAN_NOT_BE_DUPLICATED="userNameCanNotBeDuplicated";
        public static final String USER_SHOULD_BE_EXISTS="userNotFound";

        public static final String LOGIN_FAILED= "loginFailed";
    }
    public static class UserRoleMessages{
        public static final String USER_ROLE_SHOULD_BE_EXISTS="userRoleNotFound";
    }
}

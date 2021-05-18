package ua.comsys.kpi.snailboard.utils;

public final class StringUtils {

    private StringUtils() {}

    public static String prepareTeamInvitationalEmail(String inviteLink, String teamName) {
        return String.format("You were invited to %s team.%nFollow this link to join:%n%s", teamName, inviteLink);
    }
}

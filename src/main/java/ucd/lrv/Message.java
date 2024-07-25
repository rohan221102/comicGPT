package ucd.lrv;

public class Message {
    private final String role;
    private final String content;
    // private final String name; will be useful to maintain different characters

    Message(String role, String content) {
        if (role == null || content == null ) {
            throw new IllegalArgumentException("None of the parameters can be null");
        }
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    /*public String toString() {
        return "{\"role\": \"" + role + "\", \"content\": \"" + content + "\"}";
*/

    @Override
    public String toString() {
        return String.format("{\"role\": \"%s\", \"content\": \"%s\"}",
                escapeJson(role), escapeJson(content));
    }

    // Helper for json chars manipulation
    private String escapeJson(String input) {
        if (input == null) return null;
        return input.replace("\"", "\\\"");
    }

}

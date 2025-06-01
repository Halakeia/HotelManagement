package hotel.bao.security;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_CLIENT"),
    NAO_AUTENTICADO("ROLE_NAO_AUTENTICADO");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
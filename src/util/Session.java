package util;

import model.User;

public class Session {
    private static Session instance;
    private User currentUser;

    private Session() {

    }

    // Get the Singleton instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Get the current user
    public User getCurrentUser() {
        return currentUser;
    }

    // Clear session (Logout)
    public void clearSession() {
        currentUser = null;
    }
}

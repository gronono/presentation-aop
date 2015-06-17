package api;

import api.ext.User;

public class SecurityException extends RuntimeException {

	public SecurityException(User currentUser, User requiredUser) {

	}

}

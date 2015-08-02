package api.ext;


public class UserService implements IUserService {

	private User currentUser;

	public UserService(User currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}
}

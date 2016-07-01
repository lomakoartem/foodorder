package ua.rd.foodorder.infrastructure;

public class UserNameAndUpsaLinkTuple {

	private final String userName;
	
	private final String upsaLink;
	
	public UserNameAndUpsaLinkTuple(String userName, String upsaLink) {
		this.userName = userName;
		this.upsaLink = upsaLink;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getUpsaLink() {
		return upsaLink;
	}
	
}

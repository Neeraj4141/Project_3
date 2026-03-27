package in.co.rays.project_3.dto;

public class CartDTO extends BaseDTO {

	private long cartId;
	private String cartCode;
	private String userName;
	private long totalItems;
	private String status;
	
	

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public String getCartCode() {
		return cartCode;
	}

	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}

}

package com.challenge.inventory_service.constant;

public class ResponseCodeConstants {

	private ResponseCodeConstants() {
	}

	// Constant for response code
	public static final String RESPONSE_CODE_SUCCESS = "000";
	public static final String RESPONSE_CODE_GENERAL_ERROR = "999";
	public static final String RESPONSE_CODE_CLIENT_ERROR = "400";
	public static final String RESPONSE_CODE_DATA_NOT_FOUND = "404";

	// Constant for error desc
	public static final String RESPONSE_DESC_SUCCESS = "Success";
	public static final String RESPONSE_DESC_GENERAL_ERROR = "General Error";
	public static final String RESPONSE_DESC_ORDER_STOCK_NOT_AVAILABLE = "Stock Not Available";
	public static final String RESPONSE_DESC_ORDER_ITEM_NOT_FOUND = "Item Not Found";
	public static final String RESPONSE_DESC_BOOKING_NOT_FOUND = "Please Do Book First";

}

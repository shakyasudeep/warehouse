/**
 * 
 */
package com.example.demo.exceptions;

/**
 * @author sudeepshakya
 *
 */
public class DuplicateDealException extends Exception {
	private static final long serialVersionUID = -7445120812909300758L;

	public DuplicateDealException(String message) {
		super(message);
	}
}

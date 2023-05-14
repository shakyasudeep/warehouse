/**
 * 
 */
package com.example.demo.exceptions;

/**
 * @author sudeepshakya
 *
 */

public class DealValidationException extends Exception {
	private static final long serialVersionUID = -8813862414645331505L;

	public DealValidationException(String message) {
		super(message);
	}
}
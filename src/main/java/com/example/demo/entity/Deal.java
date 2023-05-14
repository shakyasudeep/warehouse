/**
 * 
 */
package com.example.demo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sudeepshakya
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "deals")
public class Deal implements Serializable {

	private static final long serialVersionUID = 6584356239045607469L;

	public Deal(String dealId, String fromCurreny, String toCurrency, Date timestamp, Double amount) {
		this.dealId = dealId;
		this.fromCurrency = fromCurreny;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = new BigDecimal(amount);
	}

	public Deal(String dealId, String fromCurreny, String toCurrency, Date timestamp, BigDecimal amount) {
		this.dealId = dealId;
		this.fromCurrency = fromCurreny;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = amount;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "deal_id", unique = true)
	private String dealId;

	@Column(name = "from_currency")
	private String fromCurrency;

	@Column(name = "to_currency")
	private String toCurrency;

	//@JsonSerialize(using = LocalDateTimeSerializer.class)
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'hh:mm:ss")
	@Column(name = "deal_timestamp")
	private Date timestamp;

	@Column(name = "amount")
	private BigDecimal amount;

}

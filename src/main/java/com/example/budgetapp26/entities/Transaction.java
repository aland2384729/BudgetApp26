package com.example.budgetapp26.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

import com.example.budgetapp26.categorization.TransactionCategory;

@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUid = 5L;

	@Id
	@GeneratedValue
	private Integer id;

	private String description;

	private Double theValue;

	@Temporal(TemporalType.DATE)
	private Date date;

	private TransactionCategory category;

	@ManyToOne
	private Account account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTheValue() {
		return theValue;
	}

	public void setTheValue(Double theValue) {
		this.theValue = theValue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}

	public String toString() {
		return "[" + id + "," + date + "," + description + "," + theValue + "," + account + ","
				+ category.toString() + "]";
	}

}
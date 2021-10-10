package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
	
	
	public CurvePoint() {}
	
	public CurvePoint(int curveId, double term, double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
		
	}
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
    @Positive
	@NotNull(message = "CurveId is mandatory")
	private Integer curveId;
	private Timestamp asOfDate;
    @NotNull(message = "Term is mandatory")
	private Double term;
    @NotNull(message = "Value is mandatory")
	private Double value;
	private Timestamp creationDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCurveId() {
		return curveId;
	}
	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}
	public Timestamp getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Double getTerm() {
		return term;
	}
	public void setTerm(Double term) {
		this.term = term;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

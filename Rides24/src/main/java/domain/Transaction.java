package domain;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Transaction {
	
	//ATRIBUTES//
	@Id
	private int TransID = (int) System.currentTimeMillis();
	private float saldo;
	private Date fecha;
	
	//Constructoras//
	
	public Transaction(float s) {
		saldo=s;
		fecha= new Date();
	}
	
	public float getSaldo() {
		return saldo;
	}
	
}

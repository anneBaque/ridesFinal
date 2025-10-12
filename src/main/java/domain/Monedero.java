package domain;

import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class Monedero {

	//ATRIBUTOS//
	@Id
	private int MonederoID = (int) System.currentTimeMillis();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Transaction> lista;
	
	//BUILDER//
	
	public Monedero() {
		lista = new ArrayList<>();
	}
	
	public boolean addCash(float s) {
		boolean bol = false;
		if(s>0) {
			lista.add(new Transaction(s));
			bol=true;
		}
		return bol;
	}
	
	public float calcularSaldo() {
		float sum = 0;
		for(Transaction trans:lista) {
			sum=sum+trans.getSaldo();
		}
		return sum;
	}
	
	public boolean pagarReserva(float precio) {
		boolean r=false;
		if(calcularSaldo()>=precio) {
			lista.add(new Transaction(-precio));
			r=true;
		}
		return r;
	}
	
	public boolean withdraw(float cuanto) {
		boolean r=false;
		if(calcularSaldo()>=cuanto) {
			lista.add(new Transaction(-cuanto));
			r=true;
		}
		return r;
	}
	
	public ArrayList<Transaction> getLista() {
		return lista;
	}
	
	
}

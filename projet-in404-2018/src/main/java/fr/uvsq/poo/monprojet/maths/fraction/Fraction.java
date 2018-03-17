package fr.uvsq.poo.monprojet.maths.fraction;

public class Fraction {
	private int numerateur;
	private int denominateur;
	public final static Fraction ZERO = new Fraction(0,1);
	public final static Fraction UN = new Fraction(1,1);
	
	public Fraction() {
		numerateur = 0;
		denominateur = 1;
	}
	public Fraction(int numerateur, int denominateur) {
		if(denominateur != 0) {
			this.numerateur = numerateur;
			this.denominateur = denominateur;
		}
		else {
			this.setFraction(0,1);
		}
		
	}
	
	public Fraction(Fraction f) {
		this.numerateur = f.numerateur;
		this.denominateur = f.denominateur;
	}
	
	public String toString() {
		return numerateur + "/" + denominateur;
	}
	
	public boolean setFraction(int numerateur, int denominateur) {
		if(denominateur != 0) {
			this.numerateur = numerateur;
			this.denominateur = denominateur;
			return true;
		}
		else return false;
	}
	
	public int getNumerateur() {
		return numerateur;
	}
	
	public int getDenominateur() {
		return denominateur;
	}
	
	public void setNumerateur(int numerateur) {
		this.numerateur = numerateur;
	}
	
	public void setDenominateur(int denominateur) {
		this.denominateur = denominateur;
	}
	
	public double getDouble() {
		double d = ((double)numerateur)/((double)denominateur);
		return d;
	}
	
	public Fraction addition(Fraction f2) {
		Fraction f3 = new Fraction();
		if(this.denominateur != f2.denominateur) {
			f3.setFraction(this.numerateur*f2.denominateur + f2.numerateur*this.denominateur,this.denominateur*f2.denominateur);
		}
		else f3.setFraction(this.numerateur + f2.numerateur, this.denominateur);
		return f3;
	}
	
	private int pgcd(int i, int j) {
		int resultat;
		while((resultat = i%j ) != 0) {
			i = j;
			j = resultat;
		}
		return j;
	}
	
	public void setIreductible() {
		int reduction = 1;
		reduction = pgcd(this.numerateur,this.denominateur);
		this.numerateur /= reduction;
		this.denominateur /= reduction;
	}
	
	public boolean equals(Fraction f) {
		Fraction f2 = new Fraction(this);
		f2.setIreductible();
		f.setIreductible();
		if(f.numerateur == f2.numerateur && f.denominateur == f2.denominateur) return true;
		else return false;
	}
}

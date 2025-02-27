package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Outages implements Comparable<Outages>  {
	
	private int id;
	private Nerc nerc;
	private LocalDateTime outageStart;  //importante usare LocalDateTime per tenere info sia sul giorno che sull'ora
	private LocalDateTime outageEnd;
	private int affectedPeople;
	private long outageDuration;
	private int year;

	public Outages(int id, Nerc nerc, LocalDateTime outageStart, LocalDateTime outageEnd, int affectedPeople) {
		this.id = id;
		this.nerc = nerc;
		this.outageStart = outageStart;
		this.outageEnd = outageEnd;
		this.affectedPeople = affectedPeople;
		
		// Le ore vengono "troncate". 
		// Alternativamente, posso salvarmi la differenza in minuti e controllare la durata con 60*Y, dove Y sono il numero di ore massime inserite dall'utente. 
		// Entrambe le soluzioni sono ok.
		LocalDateTime tempDateTime = LocalDateTime.from(outageStart);
		this.outageDuration = tempDateTime.until(outageEnd, ChronoUnit.HOURS);
		this.year = outageStart.getYear();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getOutageStart() {
		return outageStart;
	}

	public void setOutageStart(LocalDateTime outageStart) {
		this.outageStart = outageStart;
	}

	public LocalDateTime getOutageEnd() {
		return outageEnd;
	}

	public void setOutageEnd(LocalDateTime outageEnd) {
		this.outageEnd = outageEnd;
	}

	public long getOutageDuration() {
		return outageDuration;
	}

	public void setOutageDuration(long outageDuration) {
		this.outageDuration = outageDuration;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getAffectedPeople() {
		return affectedPeople;
	}

	public void setAffectedPeople(int affectedPeople) {
		this.affectedPeople = affectedPeople;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Outages other = (Outages) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Outages o) {
		return this.getOutageStart().compareTo(o.getOutageStart());
	}

	@Override
	public String toString() {
		return "Outages [id=" + id + ", nerc=" + nerc + ", outageStart=" + outageStart + ", outageEnd=" + outageEnd
				+ ", affectedPeople=" + affectedPeople + ", outageDuration=" + outageDuration + ", year=" + year + "]";
	}
	

	
	
	
	
}
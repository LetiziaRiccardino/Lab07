package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO dao;
	List<Outages> parziale;
	List<Outages> best;
	Integer maxOre=null;
	Integer maxAnni=null;
	List<Outages> tutti;
	
	public Model() {
		dao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return dao.getNercList();
	}
	
	public List<Outages> getOutagesList(Nerc nerc){
		return dao.getOutagesList(nerc);
	}
	
	
	public List<Outages> trovaSequenza(Nerc nerc, Integer maxOre, Integer maxAnni){
		
		best= new ArrayList<Outages>();
		parziale= new ArrayList<Outages>();
		
		tutti= new ArrayList<>();
		tutti= this.getOutagesList(nerc);	
		Collections.sort(tutti);//i dati sono ordinati per data
		
		this.maxOre=maxOre;
		this.maxAnni=maxAnni;
		
		cerca(parziale);//, livello
		
		return best; 
	}
	
	
	public void cerca(List<Outages> parziale){//, Integer livello
				
		
		//caso terminale
		Integer personeCoinvolte= calcolaPersoneCoinvolte(parziale);
			if(best==null || personeCoinvolte<calcolaPersoneCoinvolte(best)) {
			best=new ArrayList<>(parziale);
		}
		
		//caso intermedio
		for(Outages o: tutti) {			
				if(aggiuntaValida(o, parziale)) {
					parziale.add(o);
					cerca(parziale);
					parziale.remove(parziale.size()-1);
				}
		}
	}

	private boolean aggiuntaValida(Outages o, List<Outages> parziale2) {
		
//		LocalDateTime began=null;
//		LocalDateTime finished=null;
		Integer conta=0;
		int somma= 0;
		for(Outages ot: parziale2) {
			
//			if(conta==0) {
//				began=ot.getOutageStart();
//				finished=ot.getOutageEnd();
//				conta ++;
//			}
			somma+= ot.getOutageDuration(); //la soluzione del prof è uguale
			if(somma> maxOre)
				return false;
//			else {
//				if(ot.getOutageStart().isBefore(began))
//					began=ot.getOutageStart();
//				if(ot.getOutageEnd().isAfter(finished))
//					finished=ot.getOutageEnd();
//				if(finished.getYear()-began.getYear()>maxAnni)
//					return false;
//			}
			
			if(parziale2.size()>=2) { // sfrutto il fatto che gli eventi sono ordinati per data: prendo il primo e l'ultimo
				int y1=parziale.get(0).getYear();
				int y2=parziale.get(parziale.size()-1).getYear();
				if((y2-y1)>maxAnni)
					return false;
			}
		}
		return true;
	}

	

	private Integer calcolaPersoneCoinvolte(List<Outages> parziale2) {
		Integer pCoinvolte=0;
		for(Outages o: parziale2)
			pCoinvolte+=o.getAffectedPeople();
		return pCoinvolte;
	}
	
	

}

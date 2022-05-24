package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO dao;
	private List<Outages> parziale;
	private List<Outages> best;
	private Integer maxOre=null;
	private Integer maxAnni=null;
	private List<Outages> tutti;
	
	public Model() {
		dao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return dao.getNercList();
	}
	
	public List<Outages> getOutagesList(Nerc nerc){
		return dao.getOutagesList(nerc);
	}
	
	
	public List<Outages> getWorstCase(Nerc nerc, Integer maxOre, Integer maxAnni){
		
		best= new ArrayList<Outages>();
		
		parziale= new ArrayList<Outages>();
		
		tutti= this.getOutagesList(nerc);	
		Collections.sort(tutti);//i dati sono ordinati per data
		
		this.maxOre=maxOre;
		this.maxAnni=maxAnni;
		
		cerca(parziale);
		
		return best; 
	}
	
	
	public void cerca(List<Outages> parziale){
				
		//caso terminale
		Integer personeCoinvolte= calcolaPersoneCoinvolte(parziale);
			if(personeCoinvolte<calcolaPersoneCoinvolte(best)) {
				best= new ArrayList<Outages>(parziale);
		}
		
		//caso intermedio
		for(Outages o: tutti) {	
			parziale.add(o);
				if(aggiuntaValida(parziale)) {
					cerca(parziale);
					}
				parziale.remove(parziale.size()-1);
		}
	}

	private boolean aggiuntaValida(List<Outages> parziale2) {
		
		//controllo sul numero di ore
		int somma= 0;
		for(Outages ot: parziale2) {
			 somma+= ot.getOutageDuration();
			}
		if(somma> maxOre)
			return false;
		
		//controllo numero anni
		if(parziale2.size()>=2) { // sfrutto il fatto che gli eventi sono ordinati per data: prendo il primo e l'ultimo
				int y1=parziale.get(0).getYear();
				int y2=parziale.get(parziale.size()-1).getYear();
				if((y2-y1)>maxAnni)
					return false;
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

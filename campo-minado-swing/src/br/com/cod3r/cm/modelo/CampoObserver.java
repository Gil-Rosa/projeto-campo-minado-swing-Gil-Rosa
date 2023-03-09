package br.com.cod3r.cm.modelo;
@FunctionalInterface
public interface CampoObserver {

  public void eventoOcorreu(Campo campo, CampoEvento evento); 
	  
  
}

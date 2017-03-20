package br.univel.cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import br.univel.comum.CONSTANTES;
import br.univel.comum.Plact;

public class Zoom extends Thread implements Runnable, Plact{

	private Integer vlr = null;
	
	public Zoom() throws Exception{
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1818);
		Plact servico = (Plact) registry.lookup(Plact.NOME);
		Integer retorno = servico.calcule(CONSTANTES.RA_DEZENA);
		System.out.println(retorno);
	}
	
	public static void main(String[] args) throws Exception{
		new Zoom();
	}
	
	@Override
	public Integer calcule(Integer rec) throws RemoteException {
		if (this.vlr == null){
			this.vlr = rec;
		}else{
			this.vlr += rec;
		}
		System.out.println(vlr - 11);
		if (vlr >= 0 && vlr < 30) {
			vlr -= 1;
			return vlr;	
		} else if (vlr >= 30 && vlr < 70){
			vlr -= 2;
			return vlr;
		} else{
			vlr -= 3;
			return vlr;
		}
	}
	
	public void run(){
		Zoom servico;
		try{
			servico = (Zoom) UnicastRemoteObject.exportObject(Zoom.this, 0);
			Registry registry = LocateRegistry.createRegistry(1818);
			registry.rebind(Plact.NOME, servico);
		} catch(Exception e){}
	}
	
}

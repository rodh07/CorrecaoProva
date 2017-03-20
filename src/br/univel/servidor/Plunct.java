package br.univel.servidor;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.univel.comum.CONSTANTES;
import br.univel.comum.Plact;

public class Plunct extends Thread implements Runnable, Plact{

	private static final int PORTA_TCPIP = 1818;
	private Integer vlr = null;
	
	public void conect() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", PORTA_TCPIP);
		Plact servico = (Plact) registry.lookup(Plact.NOME);
		Integer retorno = servico.calcule(CONSTANTES.RA_DEZENA);
		System.out.println(retorno);
	}
	
	public void run(){
		Plact servico;
		try{
			servico = (Plact) UnicastRemoteObject.exportObject(Plunct.this, 0);
			Registry registry = LocateRegistry.createRegistry(PORTA_TCPIP);
			registry.rebind(Plact.NOME, servico);
		} catch (Exception e){}
	}
	
	@Override
	public Integer calcule(Integer rec) throws RemoteException {
		if (this.vlr == null){
			this.vlr = rec;
		}else{
			this.vlr += rec;
		}
		System.out.println(vlr + 11);
		if (vlr >= 0 && vlr < 30) {
			vlr += 1;
			return vlr;	
		} else if (vlr >= 30 && vlr < 70){
			vlr += 2;
			return vlr;
		} else{
			vlr += 3;
			return vlr;
		}
	}
	
	public static void main(String[] args) {
		new Plunct().start();
	}

}

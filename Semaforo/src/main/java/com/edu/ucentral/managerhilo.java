package com.edu.ucentral;

public class managerhilo implements Runnable{

	private Colores colores;
	public managerhilo() {
		
	}
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				colores.obtener(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}

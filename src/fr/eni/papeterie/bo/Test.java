package fr.eni.papeterie.bo;

public class Test {
	
	public String attribut;
	
	public static void main(String[] args) {
		int j = 1;
		Test test = new Test();
		test.attribut = "maChaine";
		test.maMethode(test);
		System.out.println("Dans main: attribut="+ test.attribut);
	}

	public  void maMethode(Test obj){
		obj.attribut = "maMethode";
		System.out.println("Dans maMethode: attribut="+ obj.attribut);
		
	}

//	public  void maMethode(int i){
//		i = 2;
//		System.out.println("Dans maMethode: i="+ i);
//	}

}

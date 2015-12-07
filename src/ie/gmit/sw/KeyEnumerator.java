package ie.gmit.sw;

public class KeyEnumerator {
	
	private char[] getNextKey(char[] key){
		for (int i = key.length - 1; i >=0; i--){
			if (key[i] =='Z'){
				if (i == 0) return null;
				key[i] = 'A';
			}else{
				key[i]++;
				break;
			}
		}
		return key;
	}
	
	
	public char[] crackCypher(String cypherText, int maxKeyLength){
		char[] key = null;
		
		int counter = 0;
		for (int j = 3; j <= maxKeyLength; j++){
			key = new char[j];
			for (int k = 0; k < key.length; k++) key[k] = 'A';
			
			do{
				counter++;
				//System.out.println(new String(key));
				
			}while ((key = getNextKey(key)) != null);
		}
		System.out.println("Enumerated " + counter + " keys.");
		
		return key;
	}
	
	public static void main(String[] args) {
		new KeyEnumerator().crackCypher("TVHUGTUDHKOURUGTS", 6);;
	}
}

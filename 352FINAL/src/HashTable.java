import java.math.BigInteger;


  
public class HashTable implements DataStructure {
	 
	private static int size;
	public HashEntry[] table;
	public enum State{EMPTY, OCCUPIED, AVAILABLE};
	
	
	public HashTable(int tableSize){
		if(!checkPrime(tableSize)){
			size = nextPrime(tableSize);
		}
		table = new HashEntry[size];
		for(int i = 0; i<size; i++){
			table[i] = new HashEntry();
			table[i].state = State.EMPTY;
		}
	}
	
	public boolean checkPrime(int n){
		BigInteger b = new BigInteger(String.valueOf(n));
		
		return b.isProbablePrime(1);
	}
	
	public int nextPrime(int n){
		BigInteger b = new BigInteger(String.valueOf(n));
		return Integer.parseInt(b.nextProbablePrime().toString());
	}

	@Override
	public void put(int key, int value) {
		size++;
		int hash = hashFunction(key);
		
		HashEntry entry = new HashEntry(key, value);
		
		while(table[hash].key != null && table[hash].getKey() != key){
			hash = (hash + 1) % size;
		}
		table[hash] = entry;
		if(hash == (size - 1)){
			entry.next = null;
			entry.prev = table[hash - 1];
		}
		else if(hash == 0){
			entry.next = table[hash + 1];
			entry.prev = null;
		}
		else{
			entry.next = table[hash + 1];
			entry.prev = table[hash - 1];
		}
		entry.state = State.OCCUPIED;
	}

	@Override
	public void remove(int key) {
		int hash = (key % size);
		
		while(table[hash].key != null && table[hash].getKey() != key){
			hash = (hash - 1) % size;
		}
		table[hash] = null;
		table[hash].state = State.AVAILABLE;
		
	}
		

	@Override
	public int get(int key) {
		int hash = (key%size);
		
		while(table[hash].key != null && table[hash].getKey() != key){
			hash = (hash + 1) % size;
		}
		if(table[hash] == null){
			return -1;
		}
		else{
			return table[hash].getValue();
		}
		
	}
	
	private int hashFunction(Integer i){
		int hash = i.hashCode();
		hash %= table.length;
		if(hash < 0){
			hash += table.length;
		}
		return hash;
	}

}

class HashEntry {

	protected HashTable.State state;
	protected Integer key;
	protected Integer value; 
	protected HashEntry next;
	protected HashEntry prev;
	
	
	HashEntry(){
		this.key = null;
		this.value = null;
	}
	HashEntry(int key, int value){
		this.key = key;
		this.value = value; 
		this.next = null;
		this.prev = null; 
	}
	
	public int getKey(){
		return key;
	}
	
	public int getValue(){
		return value;
	}
}
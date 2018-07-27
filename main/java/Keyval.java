import java.util.Comparator;

public class Keyval{
	
	private String key;
	private String val;
	private String word;
	
	//constructor for keyval
	public Keyval(String key, String val) {
	
		this.key = key;
		this.val = val;
		this.word = key + val;
		
	}
	
	
	//getters
	public String getKey() {
		return key;
	}
	
	public String getVal() {
		return val;
	}

	public String getWord() {
		return word;
	}
	
	public String printMapping(){
		return (getKey() + ":" + getVal());
	}
	
	public void updateWord(){
		this.word = this.key + this.val;	
	}
	public void setKey(String key) {
		this.key = key;
		updateWord();
	}
	
	public void setValue(String val){
		this.val = val;
		updateWord();
	}
	
	public static Comparator<Keyval> WordComparator = new Comparator<Keyval>() {
		
		public int compare(Keyval keyval1, Keyval keyval2) { 
			String word1 = keyval1.getWord();
			String word2 = keyval2.getWord();		
			return word1.compareTo(word2);
		}
	
	};
	
}

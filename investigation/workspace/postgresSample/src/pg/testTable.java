package pg;

public class testTable {
	private int id;
	private String name;

	public testTable(int int1, String string) {
		id = int1;
		name = string;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

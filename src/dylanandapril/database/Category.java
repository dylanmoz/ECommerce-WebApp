package dylanandapril.database;

/**
 * Class representing the Category table in the database. 
 * @author dylan
 */
public class Category {
	
	private long id;
	private String name;
	private String description;
	
	public Category() {
		
	}
	
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Category(long id, String name, String description) {
		this(name, description);
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public Category setId(long id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Category setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Category setDescription(String description) {
		this.description = description;
		return this;
	}
	
}
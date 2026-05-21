package org.springboot.springboot1.entity;

public class Book {
	private Integer bookId;
	private String title;
	private String image;
	private String author;
	private Double price;
	private Integer categoryId;
	private Category category;
	

	public Book() {
		super();
	}
	public Book(Integer bookId, String title, String image, String author, Double price) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.image = image;
		this.author = author;
		this.price = price;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	

}

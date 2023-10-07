package book;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.*;
import book.file.BookFileReader;


/**
 * Represents a catalog of books.
 * @author lbrandon 20220418
 *
 */
public class BookCatalog {

	/**
	 * Internal map for storing books where the key is the title and the value is the Book object.
	 */
	private Map<String, Book> bookMap;
	
	/**
	 * Creates a book catalog and initializes the internal map for storing books.
	 */
	public BookCatalog() {
		this.bookMap = new TreeMap<String, Book>();
	}

	/**
	 * Gets internal book map.
	 * @return book map
	 */
	public Map<String, Book> getBookMap() {
		return this.bookMap;
	}
	
	/**
	 * Adds the given book to the internal map.
	 * Uses book title as key and Book itself as value.  If title is null, doesn't add Book.
	 * @param book to add
	 */
	public void addBook(Book book) {
		// TODO Implement method
		String title = book.getTitle();
		bookMap.put(title,book);
	}
	
	/**
	 * Gets the book associated with the given title.
	 * Returns null if the book doesn't exist in the catalog.
	 * You do not need to worry about case. "Test Title" is not the same as "test title"
	 * @param title of book
	 * @return book
	 */
	public Book getBookByTitle(String title) {
		// TODO Implement method
		title =title.trim();
		Book b = null;
		b = this.bookMap.get(title);
		return b;
	}
	
	/**
	 * Gets the book associated with the given author.
	 * Returns null if the book doesn't exist in the catalog.
	 * You do not need to worry about case. "Test Author" is not the same as "test author"
	 * @param author of book
	 * @return book
	 */
	public Book getBookByAuthor(String author) {
		// TODO Implement method
		author = author.trim();
		Book b = null;
		Iterator<String> it = this.bookMap.keySet().iterator();
		while(it.hasNext()){
			b = getBookByTitle(it.next());
			if(b.getAuthor().equals(author)){
				break;
			}
		}
		return b;
	}
	
	///// DO NOT CHANGE CODE IN MAIN METHOD! /////
	public static void main(String[] args) throws FileNotFoundException {
    	
		//create instance of book catalog
		BookCatalog bookCatalog = new BookCatalog();
		
		//load and parse book files
		List<String> catInTheHat = BookFileReader.parseFile("the_cat_in_the_hat_snippet.txt");
		List<String> warAndPeace = BookFileReader.parseFile("war_and_peace.txt");
		List<String> siddhartha = BookFileReader.parseFile("siddhartha.txt");
		
		//create instances of book with lists above
		Book catInTheHatBook = new Book(catInTheHat);
		Book warAndPeaceBook = new Book(warAndPeace);
		Book siddharthaBook = new Book(siddhartha);
		
		//get lines from books
		System.out.println("\nGET FIRST 3 LINES");
		System.out.println(catInTheHatBook.getLines().subList(0, 3));
		System.out.println(warAndPeaceBook.getLines().subList(0, 3));
		System.out.println(siddharthaBook.getLines().subList(0, 3));

		//get titles of books
		System.out.println("\nGET TITLES");
		String catInTheHatBookTitle = catInTheHatBook.getTitle();
		System.out.println(catInTheHatBookTitle);
		String warAndPeaceBookTitle = warAndPeaceBook.getTitle();
		System.out.println(warAndPeaceBookTitle);
		String siddharthaBookTitle = siddharthaBook.getTitle();
		System.out.println(siddharthaBookTitle);
		
		//get authors of books
		System.out.println("\nGET AUTHORS");
		String catInTheHatBookAuthor = catInTheHatBook.getAuthor();
		System.out.println(catInTheHatBookAuthor);
		String warAndPeaceBookAuthor = warAndPeaceBook.getAuthor();
		System.out.println(warAndPeaceBookAuthor);
		String siddharthaBookAuthor = siddharthaBook.getAuthor();
		System.out.println(siddharthaBookAuthor);
		
		//get total word counts from books
		System.out.println("\nGET TOTAL WORD COUNTS");
		System.out.println(catInTheHatBook.getTotalWordCount());
		System.out.println(warAndPeaceBook.getTotalWordCount());
		System.out.println(siddharthaBook.getTotalWordCount());
		
		//get total word counts from books
		System.out.println("\nGET UNIQUE WORD COUNTS");
		System.out.println(catInTheHatBook.getUniqueWordCount());
		System.out.println(warAndPeaceBook.getUniqueWordCount());
		System.out.println(siddharthaBook.getUniqueWordCount());
		
		//get specific word counts from books
		System.out.println("\nGET SPECIFIC WORD COUNTS");
		System.out.println(catInTheHatBook.getSpecificWordCount("sit"));
		System.out.println(warAndPeaceBook.getSpecificWordCount("love"));
		System.out.println(siddharthaBook.getSpecificWordCount("hate"));
		
		//add books to catalog
		bookCatalog.addBook(catInTheHatBook);
		bookCatalog.addBook(warAndPeaceBook);
		bookCatalog.addBook(siddharthaBook); 

		//get books by title
		System.out.println("\nGET BOOKS BY TITLE");
		System.out.println(bookCatalog.getBookByTitle("The Cat in the Hat").getTitle());
		System.out.println(bookCatalog.getBookByTitle("War and Peace").getTitle());
		System.out.println(bookCatalog.getBookByTitle("Siddhartha").getTitle());
		
		//get books by author
		System.out.println("\nGET BOOKS BY AUTHOR");
		System.out.println(bookCatalog.getBookByAuthor("Dr. Seuss").getAuthor());
		System.out.println(bookCatalog.getBookByAuthor("Leo Tolstoy").getAuthor());
		System.out.println(bookCatalog.getBookByAuthor("Hermann Hesse").getAuthor());
		
	}
}

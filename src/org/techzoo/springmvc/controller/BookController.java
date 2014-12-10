package org.techzoo.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.techzoo.springmvc.dao.BookDao;
import org.techzoo.springmvc.vo.Book;

@Controller
@RequestMapping("api")
public class BookController {

	BookDao bookBao;
	
	@Autowired
	public BookController(BookDao bookBao) {
		this.bookBao = bookBao;
	}
	
	@RequestMapping(value = "/index")
	public String index()
	{
		return "index";
	}
	
	@RequestMapping (value = "book/save", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveBook(@RequestBody Book book)
	{
		return bookBao.addBook(book);
	}
	
	@ResponseBody
	@RequestMapping (value = "book/loadBooks")
	public Map<String, List<Book>> loadAllBooks()
	{
		Map<String, List<Book>> books = new HashMap<String, List<Book>>();
		books.put("books", bookBao.listBooks());
		return books;
	}
	
	@RequestMapping (value = "book/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteBooks(@RequestBody Book book)
	{
		return bookBao.removeBook(book);
	}
	
	@RequestMapping (value = "book/updateBook", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateBooks(@RequestBody Book book)
	{
		bookBao.updateBook(book);
		return true;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ObjectError> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException error ) {
	   return error.getBindingResult().getAllErrors();
	}
}

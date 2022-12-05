package com.apu.multiple.database.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.apu.multiple.database.api.book.repository.BookRepository;
import com.apu.multiple.database.api.model.book.Book;
import com.apu.multiple.database.api.model.user.User;
import com.apu.multiple.database.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootMultipleDsApplication {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void addData2DB() {
		userRepository.saveAll(
				Stream.of(
						new User(1, "Test", "Lastname"),
						new User(2, "Test2", "lm2")).collect(Collectors.toList()));
		bookRepository.saveAll(
				Stream.of(new Book(1, "Java"), new Book(2, "Math")).collect(Collectors.toList()));
	}

	@GetMapping("/getUsers")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user){
		return userRepository.save(user);
	}

	@GetMapping("/getBooks")
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	@PostMapping("/addBook")
	public Book addBook(@RequestBody Book book){
		return bookRepository.save(book);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleDsApplication.class, args);
	}
}
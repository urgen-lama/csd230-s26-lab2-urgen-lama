package csd230.s26.lab1;

import com.github.javafaker.Faker;
import csd230.s26.lab1.entities.*;
import csd230.s26.lab1.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootApplication
public class Lab1Application implements CommandLineRunner {

	private final BookRepository bookRepository;
	private final MagazineRepository magazineRepository;
	private final DiscMagRepository discMagRepository;
	private final TicketRepository ticketRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final GuitarRepository guitarRepository;
	private final PianoRepository pianoRepository;

	public Lab1Application(BookRepository bookRepository,
	                       MagazineRepository magazineRepository,
	                       DiscMagRepository discMagRepository,
	                       TicketRepository ticketRepository,
	                       ProductRepository productRepository,
	                       CartRepository cartRepository,
	                       GuitarRepository guitarRepository,
	                       PianoRepository pianoRepository) {
		this.bookRepository = bookRepository;
		this.magazineRepository = magazineRepository;
		this.discMagRepository = discMagRepository;
		this.ticketRepository = ticketRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.guitarRepository = guitarRepository;
		this.pianoRepository = pianoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Faker faker = new Faker();

		System.out.println("Generating Books...");
		for (int i = 0; i < 3; i++) {
			BookEntity book = new BookEntity(
					faker.book().author(),
					faker.book().title(),
					Double.parseDouble(faker.commerce().price(10.0, 50.0)),
					faker.number().numberBetween(1, 100)
			);
			bookRepository.save(book);
		}

		System.out.println("Generating Magazines...");
		for (int i = 0; i < 3; i++) {
			MagazineEntity mag = new MagazineEntity(
					faker.number().numberBetween(10, 100),
					LocalDateTime.now().minusDays(i),
					faker.book().genre() + " Magazine",
					Double.parseDouble(faker.commerce().price(5.0, 20.0)),
					faker.number().numberBetween(10, 500)
			);
			magazineRepository.save(mag);
			System.out.println("Saved Magazine: " + mag.getTitle());
		}

		System.out.println("Generating Tickets...");
		for (int i = 0; i < 3; i++) {
			String eventName = faker.commerce().department() + " " + faker.company().suffix();
			TicketEntity ticket = new TicketEntity(
					eventName + " Ticket",
					Double.parseDouble(faker.commerce().price(5.0, 100.0))
			);
			ticketRepository.save(ticket);
			System.out.println("Saved Ticket: " + ticket.getDescription());
		}

		System.out.println("Generating DiscMags...");
		for (int i = 0; i < 3; i++) {
			DiscMagEntity discMag = new DiscMagEntity(
					faker.bool().bool(),
					faker.number().numberBetween(10, 100),
					LocalDateTime.now().minusDays(i),
					faker.book().title() + " (with Disc)",
					Double.parseDouble(faker.commerce().price(10.0, 30.0)),
					faker.number().numberBetween(5, 50)
			);
			discMagRepository.save(discMag);
			System.out.println("Saved DiscMag: " + discMag.getTitle());
		}

		System.out.println("Generating Guitars...");
		for (int i = 0; i < 3; i++) {
			GuitarEntity guitar = new GuitarEntity(
					faker.company().name(),
					"New",
					faker.options().option(6, 7, 12),
					faker.bool().bool(),
					Double.parseDouble(faker.commerce().price(299.0, 2499.0))
			);
			guitarRepository.save(guitar);
			System.out.println("Saved Guitar: " + guitar.getBrand());
		}

		System.out.println("Generating Pianos...");
		for (int i = 0; i < 3; i++) {
			PianoEntity piano = new PianoEntity(
					faker.company().name(),
					"Used",
					faker.options().option(61, 76, 88),
					faker.bool().bool(),
					Double.parseDouble(faker.commerce().price(499.0, 9999.0))
			);
			pianoRepository.save(piano);
			System.out.println("Saved Piano: " + piano.getBrand());
		}

		CartEntity cart = new CartEntity();
		cartRepository.save(cart);

		BookEntity someBook = bookRepository.findAll().get(0);
		cart.addProduct(someBook);

		GuitarEntity randomGuitar = guitarRepository.findAll().get(0);
		cart.addProduct(randomGuitar);

		cartRepository.save(cart);

		System.out.println("\n--- Cart Verification ---");
		cartRepository.findAll().forEach(c -> {
			System.out.println("Cart ID: " + c.getId());
			c.getProducts().forEach(p -> System.out.println(" - Contains: " + p.toString()));
		});

		System.out.println("\n--- Query Testing ---");

// 1. Test findByAuthor (Search for one of your faker-generated authors)
		String authorToFind = bookRepository.findAll().get(0).getAuthor();
		System.out.println("Searching for books by: " + authorToFind);
		bookRepository.findByAuthor(authorToFind).forEach(System.out::println);

// 2. Test findByTitleContaining
		System.out.println("\nSearching for titles containing 'Magazine':");
		bookRepository.findByTitleContaining("Magazine").forEach(System.out::println);

// 3. Test findByPriceLessThan
		System.out.println("\nSearching for cheap items (under $20.00):");
		productRepository.findByPriceLessThan(20.0).forEach(p ->
				System.out.println(p.getProductId() + ": " + p.getClass().getSimpleName()));

// 4. Test Custom Range Query
		System.out.println("\nTesting Custom Price Range Query ($15 - $45):");
		productRepository.findProductsInPriceRange(15.0, 45.0).forEach(System.out::println);

		System.out.println("\nDatabase initialization complete.");

		System.out.println("\n--- Listing All Products from ProductRepository ---");
		productRepository.findAll().forEach(product -> {
			System.out.println(product.toString());
		});
	}
}
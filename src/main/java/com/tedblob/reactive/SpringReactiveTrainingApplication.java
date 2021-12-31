package com.tedblob.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class SpringReactiveTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveTrainingApplication.class, args);
		String result = getMono().block(Duration.ofMillis(1000));
		System.out.println(result);
		getMono().doOnNext(r -> {
					if (result != null) {
						String value = result.toUpperCase();
						System.out.println(value);
					}

				})
				.doOnNext(r -> {
					if (result != null) {
						String value = result.toLowerCase();
						System.out.println(value);
					}
				})
				.subscribe(System.out::println);

/* prints
Example of Mono */
	}

	private static Mono<String> getMono() {

		return Mono.just("Example");
	}

}

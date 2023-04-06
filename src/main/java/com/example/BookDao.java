package com.example;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookDao implements PanacheRepositoryBase<Book,String> {
}

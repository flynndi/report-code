package com.example;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/hello")
public class ExampleResource {

    @Inject
    BookDao bookDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("book/all")
    @Produces(MediaType.APPLICATION_JSON)
    @WithSession
    public Uni<List<Book>> all() {
        return bookDao.listAll();
    }
}

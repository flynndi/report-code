package com.example;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.core.runtime.context.VertxContextSafetyToggle;
import io.smallrye.common.vertx.VertxContext;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleBean.class);

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Inject
    Vertx vertx;

    @Inject
    BookDao bookDao;

    void onStart(@Observes StartupEvent ev) {

        // initBook4 can run on quarkus2
        // None of the methods can be run in quarkus3
        // If there is something wrong with my writing, please let me know

        this.initBook1();
//        this.initBook2();
//        this.initBook3();
//        this.initBook4();

    }


    public Uni<Void> initBook1() {
        Context context = VertxContext.getOrCreateDuplicatedContext(vertx);
        VertxContextSafetyToggle.setContextSafe(context, Boolean.TRUE);
        context.runOnContext(event -> {
            sessionFactory.openSession().chain(session -> {
                Book indefinitely = bookDao.findById("3").await().indefinitely();
                if (Objects.nonNull(indefinitely)) {
                    Book book = new Book();
                    book.setContent("3");
                    book.setId("3");
                    book.setName("3");
                    bookDao.persist(book).await().indefinitely();
                }
                return Uni.createFrom().voidItem();
            })
                    .eventually(() -> sessionFactory.close()).await().indefinitely();

        });
        return Uni.createFrom().voidItem();
    }


    public void initBook2() {
        Context context = VertxContext.getOrCreateDuplicatedContext(vertx);
        VertxContextSafetyToggle.setContextSafe(context, Boolean.TRUE);
        context.runOnContext(event -> {
            Book indefinitely = bookDao.findById("3").await().indefinitely();
            if (Objects.nonNull(indefinitely)) {
                Book book = new Book();
                book.setContent("3");
                book.setId("3");
                book.setName("3");
                bookDao.persist(book).await().indefinitely();
            }
        });
    }

    public void initBook3() {
        Context context = VertxContext.getOrCreateDuplicatedContext(vertx);
        VertxContextSafetyToggle.setContextSafe(context, Boolean.TRUE);
        context.executeBlocking(promise -> {
            Book indefinitely = bookDao.findById("3").await().indefinitely();
            if (Objects.nonNull(indefinitely)) {
                Book book = new Book();
                book.setContent("3");
                book.setId("3");
                book.setName("3");
                bookDao.persist(book).await().indefinitely();
            }
            promise.complete();
        }, res -> {
            if (res.failed()) {
                System.out.println("res.cause() = " + res.cause());
            }
        });
    }

    public void initBook4() {
        Book indefinitely = bookDao.findById("3").await().indefinitely();
        if (Objects.nonNull(indefinitely)) {
            Book book = new Book();
            book.setContent("3");
            book.setId("3");
            book.setName("3");
            bookDao.persist(book).await().indefinitely();
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }
}

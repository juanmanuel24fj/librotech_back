package org.example.repository;

import org.example.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

     Libro findByTitulo(String titulo);
     List<Libro> findByAutor(String autor);

}

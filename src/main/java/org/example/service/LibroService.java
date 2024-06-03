package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Libro;
import org.example.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
public class LibroService {
    @Autowired
    private LibroRepository repositorioLibros;
    private final Path rootLocation = Paths.get("mediafiles");

    public List<Libro> obtenerTodosLosLibros() {
        return repositorioLibros.findAll();
    }

    public Libro obtenerLibroPorId(Long id) {
        return repositorioLibros.findById(id).orElse(null);
    }

    public Libro obtenerLibroPorTitulo(String titulo) {
        return repositorioLibros.findByTitulo(titulo);
    }

    public List<Libro> obtenerLibrosPorAutor(String autor) {
        return repositorioLibros.findByAutor(autor);
    }

    public Libro crearLibro(Libro libro) {
        return repositorioLibros.save(libro);
    }

    public String guardarImagen(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String filename = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    public Resource obtenerImagenLibro(String nombreImagen) {
        try {
            Path archivo = rootLocation.resolve(nombreImagen).normalize().toAbsolutePath();
            Resource resource = new UrlResource(archivo.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se pudo leer la imagen del libro.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al cargar la imagen del libro.", e);
        }
    }
}

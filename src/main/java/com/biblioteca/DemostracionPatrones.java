package com.biblioteca;

import com.biblioteca.model.builders.LibroBuilder;
import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.abstractfactory.impl.LibroDigitalFactory;
import com.biblioteca.patterns.abstractfactory.impl.LibroFisicoFactory;
import com.biblioteca.patterns.adapter.LegacyLibro;
import com.biblioteca.patterns.adapter.impl.LegacyLibroAdapter;
import com.biblioteca.patterns.decorator.impl.PrestamoDecorator;
import com.biblioteca.patterns.factory.impl.FiccionFactory;
import com.biblioteca.patterns.factory.impl.NoFiccionFactory;
import com.biblioteca.patterns.observer.impl.EstadisticasObserver;
import com.biblioteca.patterns.observer.impl.PrestamoObserver;
import com.biblioteca.patterns.strategy.impl.SearchByAuthorStrategy;
import com.biblioteca.patterns.strategy.impl.SearchByTitleStrategy;
import com.biblioteca.service.BibliotecaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase de demostración que muestra el uso de todos los patrones implementados
 * Se ejecuta automáticamente al iniciar la aplicación
 */
@Component
public class DemostracionPatrones implements CommandLineRunner {
    
    private final BibliotecaService bibliotecaService;
    private final FiccionFactory ficcionFactory;
    private final NoFiccionFactory noFiccionFactory;
    private final LibroFisicoFactory libroFisicoFactory;
    private final LibroDigitalFactory libroDigitalFactory;
    private final SearchByTitleStrategy searchByTitleStrategy;
    private final SearchByAuthorStrategy searchByAuthorStrategy;
    private final PrestamoObserver prestamoObserver;
    private final EstadisticasObserver estadisticasObserver;
    
    public DemostracionPatrones(BibliotecaService bibliotecaService,
                               FiccionFactory ficcionFactory,
                               NoFiccionFactory noFiccionFactory,
                               LibroFisicoFactory libroFisicoFactory,
                               LibroDigitalFactory libroDigitalFactory,
                               SearchByTitleStrategy searchByTitleStrategy,
                               SearchByAuthorStrategy searchByAuthorStrategy,
                               PrestamoObserver prestamoObserver,
                               EstadisticasObserver estadisticasObserver) {
        this.bibliotecaService = bibliotecaService;
        this.ficcionFactory = ficcionFactory;
        this.noFiccionFactory = noFiccionFactory;
        this.libroFisicoFactory = libroFisicoFactory;
        this.libroDigitalFactory = libroDigitalFactory;
        this.searchByTitleStrategy = searchByTitleStrategy;
        this.searchByAuthorStrategy = searchByAuthorStrategy;
        this.prestamoObserver = prestamoObserver;
        this.estadisticasObserver = estadisticasObserver;
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🎯 DEMOSTRACIÓN DE PATRONES DE DISEÑO EN SISTEMA DE BIBLIOTECA");
        System.out.println("=".repeat(80));
        
        // Configurar observadores
        configurarObservadores();
        
        // Demostrar cada patrón
        demostrarPatronBuilder();
        demostrarPatronFactory();
        demostrarPatronAbstractFactory();
        demostrarPatronAdapter();
        demostrarPatronStrategy();
        demostrarPatronDecorator();
        
        // Mostrar estadísticas finales
        mostrarEstadisticasFinales();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ DEMOSTRACIÓN COMPLETADA - TODOS LOS PATRONES IMPLEMENTADOS");
        System.out.println("=".repeat(80) + "\n");
    }
    
    private void configurarObservadores() {
        System.out.println("\n🔍 CONFIGURANDO PATRÓN OBSERVER");
        System.out.println("-".repeat(50));
        
        bibliotecaService.agregarObservador(prestamoObserver);
        bibliotecaService.agregarObservador(estadisticasObserver);
        
        System.out.println("✓ Observadores configurados: " + bibliotecaService.getObservadores().size());
    }
    
    private void demostrarPatronBuilder() {
        System.out.println("\n🏗️ DEMOSTRANDO PATRÓN BUILDER");
        System.out.println("-".repeat(50));
        
        try {
            // Crear libro usando Builder pattern
            Libro libro1 = LibroBuilder.nuevo()
                    .conTitulo("Cien años de soledad")
                    .conAutor("Gabriel García Márquez")
                    .ficcion()
                    .fisico()
                    .construir();
            
            bibliotecaService.agregarLibro(libro1);
            System.out.println("✓ Libro creado con Builder: " + libro1.getInformacion());
            
            // Otro ejemplo con method chaining
            Libro libro2 = LibroBuilder.nuevo()
                    .conTitulo("El arte de la guerra")
                    .conAutor("Sun Tzu")
                    .noFiccion()
                    .digital()
                    .build();
            
            bibliotecaService.agregarLibro(libro2);
            System.out.println("✓ Libro creado con Builder: " + libro2.getInformacion());
            
        } catch (Exception e) {
            System.err.println("❌ Error en Builder: " + e.getMessage());
        }
    }
    
    private void demostrarPatronFactory() {
        System.out.println("\n🏭 DEMOSTRANDO PATRÓN FACTORY METHOD");
        System.out.println("-".repeat(50));
        
        try {
            // Factory para libros de ficción
            Libro libroFiccion = ficcionFactory.crearLibro("1984", "George Orwell");
            bibliotecaService.agregarLibro(libroFiccion);
            System.out.println("✓ Factory Ficción: " + libroFiccion.getInformacion());
            
            // Factory para libros de no ficción
            Libro libroNoFiccion = noFiccionFactory.crearLibro("Sapiens", "Yuval Noah Harari");
            bibliotecaService.agregarLibro(libroNoFiccion);
            System.out.println("✓ Factory No Ficción: " + libroNoFiccion.getInformacion());
            
        } catch (Exception e) {
            System.err.println("❌ Error en Factory: " + e.getMessage());
        }
    }
    
    private void demostrarPatronAbstractFactory() {
        System.out.println("\n🏭🏭 DEMOSTRANDO PATRÓN ABSTRACT FACTORY");
        System.out.println("-".repeat(50));
        
        try {
            // Factory para libros físicos
            Libro ficcionFisica = libroFisicoFactory.crearLibroFiccion("El Quijote", "Miguel de Cervantes");
            bibliotecaService.agregarLibro(ficcionFisica);
            System.out.println("✓ Abstract Factory Físico: " + ficcionFisica.getInformacion());
            
            // Factory para libros digitales
            Libro noFiccionDigital = libroDigitalFactory.crearLibroNoFiccion("Clean Code", "Robert Martin");
            bibliotecaService.agregarLibro(noFiccionDigital);
            System.out.println("✓ Abstract Factory Digital: " + noFiccionDigital.getInformacion());
            
        } catch (Exception e) {
            System.err.println("❌ Error en Abstract Factory: " + e.getMessage());
        }
    }
    
    private void demostrarPatronAdapter() {
        System.out.println("\n🔌 DEMOSTRANDO PATRÓN ADAPTER");
        System.out.println("-".repeat(50));
        
        try {
            // Crear libro legacy (sistema antiguo)
            LegacyLibro legacyLibro = new LegacyLibro(
                "El Principito", 
                "Antoine de Saint-Exupéry", 
                "ficcion", 
                "fisico"
            );
            
            // Adaptar para usar con nuestro sistema
            LegacyLibroAdapter adapter = LegacyLibroAdapter.adaptarConId(legacyLibro, 999L);
            
            System.out.println("✓ Sistema Legacy adaptado: " + adapter.getInformacion());
            System.out.println("✓ Formato original legacy: " + legacyLibro.obtenerDatos());
            
        } catch (Exception e) {
            System.err.println("❌ Error en Adapter: " + e.getMessage());
        }
    }
    
    private void demostrarPatronStrategy() {
        System.out.println("\n🎯 DEMOSTRANDO PATRÓN STRATEGY");
        System.out.println("-".repeat(50));
        
        try {
            // Búsqueda por título
            List<Libro> porTitulo = bibliotecaService.buscarLibros(searchByTitleStrategy, "arte");
            System.out.println("✓ Strategy - Búsqueda por título 'arte': " + porTitulo.size() + " resultados");
            
            // Búsqueda por autor
            List<Libro> porAutor = bibliotecaService.buscarLibros(searchByAuthorStrategy, "García");
            System.out.println("✓ Strategy - Búsqueda por autor 'García': " + porAutor.size() + " resultados");
            
            porTitulo.forEach(libro -> System.out.println("  📖 " + libro.getTitulo()));
            
        } catch (Exception e) {
            System.err.println("❌ Error en Strategy: " + e.getMessage());
        }
    }
    
    private void demostrarPatronDecorator() {
        System.out.println("\n🎨 DEMOSTRANDO PATRÓN DECORATOR");
        System.out.println("-".repeat(50));
        
        try {
            // Obtener un libro para decorar
            List<Libro> libros = bibliotecaService.listarLibrosDisponibles();
            if (!libros.isEmpty()) {
                Libro libro = libros.get(0);
                
                // Decorar con funcionalidad de préstamo
                PrestamoDecorator prestamoDecorator = PrestamoDecorator.decorar(libro);
                
                System.out.println("✓ Libro original: " + libro.getInformacion());
                
                // Realizar préstamo usando decorator
                boolean prestamoExitoso = prestamoDecorator.prestar("Juan Pérez", 14);
                System.out.println("✓ Préstamo realizado: " + prestamoExitoso);
                System.out.println("✓ Libro decorado: " + prestamoDecorator.getInformacion());
                
                // Verificar si está atrasado
                System.out.println("✓ ¿Está atrasado?: " + prestamoDecorator.estaAtrasado());
                
                // Devolver libro
                boolean devolucionExitosa = prestamoDecorator.devolver();
                System.out.println("✓ Devolución realizada: " + devolucionExitosa);
                
            } else {
                System.out.println("⚠️ No hay libros disponibles para demonstrar Decorator");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error en Decorator: " + e.getMessage());
        }
    }
    
    private void mostrarEstadisticasFinales() {
        System.out.println("\n📊 ESTADÍSTICAS FINALES");
        System.out.println("-".repeat(50));
        
        BibliotecaService.EstadisticasBiblioteca estadisticas = bibliotecaService.obtenerEstadisticas();
        System.out.println("📚 " + estadisticas);
        
        estadisticasObserver.mostrarResumenEstadisticas();
    }
}
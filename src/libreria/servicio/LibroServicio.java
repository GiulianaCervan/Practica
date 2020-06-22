/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicio;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

public class LibroServicio {

    public void guardar(Long isbn, String titulo, Integer anio, String idAutor, String idEditorial, Integer ejemplares) {

        EntityManager em = Persistence.createEntityManagerFactory("LIbreriaPU").createEntityManager();
        em.getTransaction().begin();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setPrestados(0);
        libro.setAnio(anio);

        Autor autor = em.find(Autor.class, idAutor);
        libro.setAutor(autor);
        
        Editorial editorial = em.find(Editorial.class, idEditorial);
        libro.setEditorial(editorial);
        
        em.persist(libro);
        em.getTransaction().commit();
    }
    
    
    public void actualizarEjemplares(Long isbn, Integer ejemplares) {

        EntityManager em = Persistence.createEntityManagerFactory("LIbreriaPU").createEntityManager();
        em.getTransaction().begin();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setEjemplares(ejemplares);
        
        em.persist(libro);
        em.getTransaction().commit();
    }
    
    public static List<Libro> buscarLibrosPorIsbn(Long i){
        EntityManager em = Persistence.createEntityManagerFactory("LIbreriaPU").createEntityManager();
        List<Libro> li = em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :i").setParameter("isbn", i).getResultList();
        
        return li;
    }
    public List<Libro> buscarLibrosPorAutor(String autor){
        EntityManager em = Persistence.createEntityManagerFactory("LIbreriaPU").createEntityManager();  
        
        List<Libro> libros = em.createQuery("SELECT c FROM Libro c WHERE c.autor.nombre LIKE :nombre ORDER BY c.titulo")
                .setParameter("nombre", "%" + autor + "%")
                .getResultList();
        
        return libros;
    }

}

package experimento03;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {		
		EntityManager entityManager = getEntityManager();
		
		List<Tecnico> tecnicos = new ArrayList<Tecnico>();
		List<Incidente> incidentes = new ArrayList<Incidente>();
		
		Cliente cliente = new Cliente("YPF", 30374231589L);
		Tecnico t01 = new Tecnico("Pepito", "Gonzales", 46732842, NotifEnum.WHATSAPP);
		Tecnico t02 = new Tecnico("Juan", "Hermanos", 32848284, NotifEnum.WHATSAPP);
		Tecnico t03 = new Tecnico("Segundo", "Rodriguez", 23423234, NotifEnum.EMAIL);
		Tecnico t04 = new Tecnico("Francisco", "Sonci", 53674543, NotifEnum.WHATSAPP);
		
		t01.addEspecialidad(EspecialidadEnum.SOFTWARE);
		t01.addEspecialidad(EspecialidadEnum.HARDWARE);
		t02.addEspecialidad(EspecialidadEnum.SOFTWARE);
		t02.addEspecialidad(EspecialidadEnum.HARDWARE);
		t03.addEspecialidad(EspecialidadEnum.SOFTWARE);
		t04.addEspecialidad(EspecialidadEnum.SOFTWARE);
		
		tecnicos.add(t01);
		tecnicos.add(t02);
		tecnicos.add(t03);
		tecnicos.add(t04);
		
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
			entityManager.persist(cliente);
			for (Tecnico t : tecnicos) {
				entityManager.persist(t);		
			}
		tx.commit();
		
		Incidente i01 = new Incidente("Pantalla azul", "Crasheo y tiro pantalla azul", EspecialidadEnum.SOFTWARE); //soft
		Incidente i02 = new Incidente("Pantalla azul", "Crasheo y tiro pantalla azul", EspecialidadEnum.SOFTWARE); //soft
		Incidente i03 = new Incidente("Impresora", "Manda a imprimir y arranca pero no manda papel", EspecialidadEnum.HARDWARE); //hardware
		Incidente i04 = new Incidente("Problema PC", "Se apaga sola la compu", EspecialidadEnum.HARDWARE); //hardware
		Incidente i05 = new Incidente("Problema Audio", "No reproduce audio en linux", EspecialidadEnum.SOFTWARE); //soft
		Incidente i06 = new Incidente("Pantalla azul", "Crasheo y tiro pantalla azul", EspecialidadEnum.SOFTWARE); //soft
		Incidente i07 = new Incidente("Pantalla azul", "Crasheo y tiro pantalla azul", EspecialidadEnum.SOFTWARE); //soft

		incidentes.add(i01);
		incidentes.add(i02);
		incidentes.add(i03);
		incidentes.add(i04);
		incidentes.add(i05);
		incidentes.add(i06);
		incidentes.add(i07);
		
		i01.setTecnico(t01);
		i02.setTecnico(t02);
		i03.setTecnico(t03);
		
		t02.finalizarIncidente();
		i04.setTecnico(t02);
		i05.setTecnico(t04);

		t04.finalizarIncidente();
		i06.setTecnico(t04);
		
		t02.finalizarIncidente();
		i07.setTecnico(t02);
		t02.finalizarIncidente();
		

		i02.setFechaAsignado(LocalDate.now().minusDays(10));
		i02.setFechaResolucion(LocalDate.now().minusDays(5));
		i04.setFechaAsignado(LocalDate.now().minusDays(3));
		i04.setFechaResolucion(LocalDate.now().minusDays(2));
		
		System.out.println("El tecnico con mas incidentes resueltos en los ultimos 7 dias es el Tecnico " + getTecnicoMaxIncidentesResueltos(tecnicos, 7, null).getId());
		System.out.println("El tecnico con mas incidentes resueltos en los ultimos 7 dias en la especialidad SOFTWARE es el Tecnico " + getTecnicoMaxIncidentesResueltos(tecnicos, 7, EspecialidadEnum.SOFTWARE).getId());
		System.out.println("El tecnico con mas incidentes resueltos en los ultimos 7 dias en la especialidad HARDWARE es el Tecnico " + getTecnicoMaxIncidentesResueltos(tecnicos, 7, EspecialidadEnum.HARDWARE).getId());
		
		System.out.println("El tecnico mas rapido fue el Tecnico " + getTecnicoMasRapido(tecnicos).getId());
	

		EntityTransaction tx2 = entityManager.getTransaction();
		tx2.begin();
			entityManager.persist(cliente);
			for (Tecnico t : tecnicos) {
				entityManager.persist(t);		
			}
			for (Incidente i : incidentes) {
				entityManager.persist(i);				
			}
		tx2.commit();
	}
	
	public static Tecnico getTecnicoMaxIncidentesResueltos(List<Tecnico> tecnicos, Integer nDias, EspecialidadEnum especialidad) {
		Integer maxResueltos = 0;
		Tecnico tecnicoMaxResueltos = null;
		
		for (Tecnico t : tecnicos) {
			Integer cantidadResueltos = t.getCantidadResueltos(nDias, especialidad);
			if (cantidadResueltos > maxResueltos) {
				maxResueltos = cantidadResueltos;
				tecnicoMaxResueltos = t;
			}
		}
		
		return tecnicoMaxResueltos;
	}
	
	public static Tecnico getTecnicoMasRapido(List<Tecnico> tecnicos) {
		Double min = 99999999.0;
		Tecnico mejorTecnico = null;
		for (Tecnico t : tecnicos) {
			Double promedioTiempoResolucion = t.promedioTiempoResolucion();
			System.out.println("Tecnico " + t.getId() + " tardo " + promedioTiempoResolucion + " dias");
			if (promedioTiempoResolucion < min) {
				min = promedioTiempoResolucion;
				mejorTecnico = t;
			}
		}
		return mejorTecnico;
	}
	
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_PU");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

}

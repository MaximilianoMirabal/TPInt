package experimento03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="tecnico")
public class Tecnico {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDENTITY")
	private Integer id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="apellido")
	private String apellido;
	@Column(name="dni")
	private Integer dni;
	@Column(name="notifPreferida")
	private NotifEnum notifPreferida;
	@Column(name="incidenteAsignado")
	private Integer incidenteAsignado = null;
	@OneToMany(cascade = CascadeType.ALL)
	//(mappedBy="tecnico_id")
	private List<Incidente> incidentes = new ArrayList<Incidente>();
	@Column(name="incidentesRealizados")
	private Integer incidentesRealizados = 0;
	@Column(name="titulo")
	private List<EspecialidadEnum> especialidades = new ArrayList<EspecialidadEnum>();
	
	public Tecnico(String nombre, String apellido, Integer dni, NotifEnum notifPreferida) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.notifPreferida = notifPreferida;
	}

	public Integer getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public NotifEnum getNotifPreferida() {
		return notifPreferida;
	}

	public void setNotifPreferida(NotifEnum notifPreferida) {
		this.notifPreferida = notifPreferida;
	}
	
	public void setIncidenteAsignado(Incidente incidente) {
		this.incidentes.add(incidente);
		this.incidenteAsignado = this.incidentes.size()-1;
		
	}
	
	public Integer getIncidenteAsignado() {
		return this.incidenteAsignado;
	}

	public void finalizarIncidente() {
		if (this.incidenteAsignado != null) {
			this.incidentes.get(this.incidenteAsignado).finalizar();
			this.incidentesRealizados++;
			this.incidenteAsignado = null;
		}
	}
	
	public List<Incidente> getIncidentes() {
		return this.incidentes;
	}
	
	public void addEspecialidad(EspecialidadEnum especialidad) {
		especialidades.add(especialidad);
	}
	
	public Boolean seEspecializaEn(EspecialidadEnum especialidad) {
		for(EspecialidadEnum e : especialidades) {
			if (e == especialidad) {
				return true;
			}
		}
		return false;
	}
	
	public Double promedioTiempoResolucion() {
		if (incidentesRealizados == 0) {
			return 999999999.0;
		}
		Long tiempoTotal = 0L;
		for(Incidente i : incidentes) {
			if (i.getEstado() == EstadoEnum.FINALIZADO) {				
				tiempoTotal += ChronoUnit.DAYS.between(i.getFechaAsignado(), i.getFechaResolucion());
			}
		}
		return tiempoTotal.doubleValue() / incidentesRealizados;		
	}

	public Integer getCantidadResueltos(Integer nDias, EspecialidadEnum especialidad) {
		Integer cantIncidentesResueltos = 0;
		for (Incidente i : incidentes) {
			if (i.getEstado() == EstadoEnum.FINALIZADO && !LocalDate.now().minusDays(nDias).isAfter(i.getFechaResolucion()) && (especialidad == null || especialidad == i.getEspecialidad())) {
				cantIncidentesResueltos++;
			}
		}
		return cantIncidentesResueltos;
	}
}

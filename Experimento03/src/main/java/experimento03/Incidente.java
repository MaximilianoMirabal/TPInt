package experimento03;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="incidente")
public class Incidente {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDENTITY")
	private Integer id;
	@Column(name="titulo")
	private String titulo;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="fechaIngreso")
	private LocalDate fechaIngreso;
	@Column(name="fechaAsignado")
	private LocalDate fechaAsignado = null;
	@Column(name="fechaResolucion")
	private LocalDate fechaResolucion = null;
	@ManyToOne
	@JoinColumn(name="tecnico_id", referencedColumnName="id")
	private Tecnico tecnico;
	@Column(name="estado")
	private EstadoEnum estado;
	@Column(name="especialidad")
	private EspecialidadEnum especialidad;
	
	public Incidente(String titulo, String descripcion, EspecialidadEnum especialidad) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaIngreso = LocalDate.now();
		this.estado = EstadoEnum.INCOMPLETO;
		this.especialidad = especialidad;
	}

	public Integer getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaResolucion() {
		return fechaResolucion;
	}
	
	public LocalDate getFechaAsignado() {
		return fechaAsignado;
	}

	public void setFechaResolucion(LocalDate fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	
	public void setFechaAsignado(LocalDate fechaAsignado) {
		this.fechaAsignado = fechaAsignado;
	}

	public EstadoEnum getEstado() {
		return estado;
	}
	
	public EspecialidadEnum getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(EspecialidadEnum especialidad) {
		this.especialidad = especialidad;
	}

	public void finalizar() {
		this.estado = EstadoEnum.FINALIZADO;
		this.fechaResolucion = LocalDate.now();
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		if (tecnico.getIncidenteAsignado() == null && tecnico.seEspecializaEn(especialidad)) {
			this.tecnico = tecnico;
			this.fechaAsignado = LocalDate.now();
			this.estado = EstadoEnum.EN_PROCESO;
			this.tecnico.setIncidenteAsignado(this);
		}
	}
}

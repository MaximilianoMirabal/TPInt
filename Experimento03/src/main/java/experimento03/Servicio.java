package experimento03;

import java.util.Date;

public class Servicio {
	
	private Integer id;
	private String nombre;
	private Date horasSoporte;
	
	public Servicio(Integer id, String nombre, Date horasSoporte) {
		this.id = id;
		this.nombre = nombre;
		this.horasSoporte = horasSoporte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHorasSoporte() {
		return horasSoporte;
	}

	public void setHorasSoporte(Date horasSoporte) {
		this.horasSoporte = horasSoporte;
	}

}

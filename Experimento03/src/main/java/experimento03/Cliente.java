package experimento03;

import jakarta.persistence.*;


@Entity
@Table(name="cliente")
public class Cliente {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDENTITY")
	private Integer id;
	@Column(name="razonSocial")
	private String razonSocial;
	@Column(name="cuit")
	private Long cuit;
	
	public Cliente(String razonSocial, Long cuit) {
		//this.id = id;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getCuit() {
		return cuit;
	}

	public void setCuit(Long cuit) {
		this.cuit = cuit;
	}
	
	

}

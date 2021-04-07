package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.educandoweb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremental (funciona pra MySQL, H2)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;  //A partir da versão 8 do Java surgiu a classe Instant melhor que o Date
	private Integer orderStatus;
	
//	@JsonIgnore  // Colocamos essa anotação pra não ficar em looping infinito, trazendo a lista de Users por Order
	@ManyToOne //Instruir ao JPA que essa é chave estrangeira
	@JoinColumn(name = "client_id") //nome da chave estrangeira no banco de dados
	private User client;
	
	@OneToMany(mappedBy = "id.order", fetch = FetchType.EAGER) // IMPORTANTE: A lista abaixo OrderItem que possui o "id" e no "id" eu tenho o "order", presente no "OrderItemPK", 
	                                  //             Aqui estou buscando os OrderItems conforme o id
	private Set<OrderItem> items = new HashSet<>();
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)  // Cascade serve exclusivamente para PROPAGAR tudo o que ocorre com essa entidade na entidade filha
	                                                          // Ao efetuar o SAVE aqui ocorrerá a atualização da entidade Payment
	private Payment payment;
	
	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = moment;
		setOrderStatus(orderStatus);;
		this.client = client;
	}

	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public Double getTotal() {
		Double total = 0.0;
		
		for (OrderItem x : items) {
			total += x.getSubTotal();
		}
		
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

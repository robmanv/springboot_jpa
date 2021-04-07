package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremental (funciona pra MySQL, H2)
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;

	// @Transient // Vai impedir que o JPA tente interpretar, quebra-galho até a
	// próxima aula

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>(); // Usei o Set ao invés do List pra garantir que não exista
														// categoria repetida pro mesmo produto.
														// Set é interface e o HashSet é a classe

	@OneToMany(mappedBy = "id.product", fetch = FetchType.EAGER) // IMPORTANTE: A lista abaixo OrderItem que possui o "id" e no "id" eu tenho o "order", presente no "OrderItemPK", 
    //                                                                          Aqui estou buscando os OrderItems conforme o id
	private Set<OrderItem> items = new HashSet<>();

	public Product() {

	}

	public Product(Long id, String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	// Vou varrer a lista OrderItem > OrderItemPK > Order, gerando outra lista de Orders (Acessar tabela intermediária pra obter as Orders)  
	@JsonIgnore
	public Set<Order> getOrders() {
		Set<Order> set = new HashSet<>();
		
		for (OrderItem x : items) {
			set.add(x.getOrder());
		}
		
		return set;
	}
	
	// Somente um exemplo, de qualquer get que coloco, ele retorna no JSON do Postman
	public Set<Category> getCategories2() {
		Set<Category> set = new HashSet<>();
		
		for (Category x : categories) {
			set.add(x);
		}
		
		return set;
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

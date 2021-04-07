package com.educandoweb.course.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremental (funciona pra MySQL, H2)
	private Long id;
	private String name;
	
	//@Transient // Vai impedir que o JPA tente interpretar, quebra-galho até a próxima aula
	//@JsonIgnore
	@JsonIgnore
	@ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)  // informo aqui o nome da collection da outra entidade, já que foi lá onde defini a tabela intermediaria, poderia ter feito aqui tb
	                                                               // inclui o fetchType EAGER pq vou obrigar a carregar essas tabelas intermediárias, normalmente elas não carregam 
	private Set<Product> products = new HashSet<>(); // Inicializando pra não ficar com nulo
	
	public Category() {
		
	}
	
	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public Set<Product> getProducts() {
		return products;
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
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
